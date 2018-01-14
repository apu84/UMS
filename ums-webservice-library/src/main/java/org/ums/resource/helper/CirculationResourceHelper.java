package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.CirculationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.formatter.DateFormat;
import org.ums.manager.ContentManager;
import org.ums.manager.library.CirculationManager;
import org.ums.manager.library.ItemManager;
import org.ums.manager.library.RecordManager;
import org.ums.persistent.model.library.PersistentCirculation;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CirculationResourceHelper extends ResourceHelper<Circulation, MutableCirculation, Long> {

  @Autowired
  CirculationManager mManager;

  @Autowired
  CirculationBuilder mBuilder;

  @Autowired
  ItemManager mItemManager;

  @Autowired
  RecordManager mRecordManager;

  @Autowired
  private DateFormat mDateFormat;

  public JsonObject getCirculation(final String pPatronId, final UriInfo pUriInfo) {
    List<Circulation> circulations = new ArrayList<>();
    try {
      circulations = mManager.getCirculation(pPatronId);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(circulations, pUriInfo);
  }

  public JsonObject getCirculationCheckedInItems(final String pPatronId, final UriInfo pUriInfo) {
    List<Circulation> circulations = new ArrayList<>();
    try {
      circulations = mManager.getCirculationCheckedInItems(pPatronId);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(circulations, pUriInfo);
  }

  public JsonObject getSingleCirculation(final String pAccessionNumber, final UriInfo pUriInfo) {
    Circulation circulation = new PersistentCirculation();
    circulation = mManager.getSingleCirculation(pAccessionNumber);
    JsonObjectBuilder object = Json.createObjectBuilder();
    LocalCache localCache = new LocalCache();
    object.add("entries", toJson(circulation, pUriInfo, localCache));
    return object.build();
  }

  public JsonObject getAllCirculation(final String pPatronId, final UriInfo pUriInfo) {
    List<Circulation> circulations = new ArrayList<>();
    try {
      circulations = mManager.getAllCirculation(pPatronId);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(circulations, pUriInfo);
  }

  @Transactional
  public Response saveCheckout(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableCirculation mutableCirculation = new PersistentCirculation();
    LocalCache localCache = new LocalCache();
    mBuilder.build(mutableCirculation, pJsonObject.getJsonObject("entries"), localCache);
    mManager.saveCheckout(mutableCirculation);
    MutableItem mutableItem = (MutableItem) mItemManager.getByAccessionNumber(mutableCirculation.getAccessionNumber());
    mutableItem.setCirculationStatus(1);
    mItemManager.update(mutableItem);
    MutableRecord mutableRecord = (MutableRecord) mRecordManager.get(mutableCirculation.getMfn());
    mutableRecord.setTotalAvailable(mutableRecord.getTotalAvailable() - 1);
    mutableRecord.setTotalCheckedOut(mutableRecord.getTotalCheckedOut() + 1);
    mRecordManager.update(mutableRecord);
    localCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Transactional
  public Response updateCirculationForCheckIn(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableCirculation mutableCirculation = new PersistentCirculation();
    LocalCache localCache = new LocalCache();
    mBuilder.checkInBuilder(mutableCirculation, pJsonObject.getJsonObject("entries"), localCache);
    mManager.updateCirculation(mutableCirculation);
    MutableItem mutableItem = (MutableItem) mItemManager.getByAccessionNumber(mutableCirculation.getAccessionNumber());
    mutableItem.setCirculationStatus(0);
    mItemManager.update(mutableItem);
    MutableRecord mutableRecord = (MutableRecord) mRecordManager.get(mutableItem.getMfn());
    mutableRecord.setTotalAvailable(mutableRecord.getTotalAvailable() + 1);
    mutableRecord.setTotalCheckedOut(mutableRecord.getTotalCheckedOut() - 1);
    mRecordManager.update(mutableRecord);

    Date dueDate = mDateFormat.parse(pJsonObject.getJsonObject("entries").getString("dueDate"));
    Date returnDate = mutableCirculation.getReturnDate();

    int compare = dueDate.compareTo(returnDate);

    System.out.println("Compare: " + compare);

    if(pJsonObject.getJsonObject("entries").containsKey("fineStatus")) {
      if(pJsonObject.getJsonObject("entries").getBoolean("fineStatus")) {
        System.out.println("True");
      }
    }
    localCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Transactional
  public Response updateCirculation(JsonObject pJsonObject, UriInfo pUriInfo) {
    List<MutableCirculation> mutableCirculation = new ArrayList<>();
    LocalCache localCache = new LocalCache();
    for(int i = 0; i < pJsonObject.getJsonArray("entries").size(); i++) {
      MutableCirculation mutableCirculation1 = new PersistentCirculation();
      mBuilder.checkInUpdateBuilder(mutableCirculation1, pJsonObject.getJsonArray("entries").getJsonObject(i),
          localCache);
      mManager.updateCirculation(mutableCirculation1);
      MutableItem mutableItem =
          (MutableItem) mItemManager.getByAccessionNumber(mutableCirculation1.getAccessionNumber());
      mutableItem.setCirculationStatus(0);
      mItemManager.update(mutableItem);
      MutableRecord mutableRecord = (MutableRecord) mRecordManager.get(mutableItem.getMfn());
      mutableRecord.setTotalAvailable(mutableRecord.getTotalAvailable() + 1);
      mutableRecord.setTotalCheckedOut(mutableRecord.getTotalCheckedOut() - 1);
      mRecordManager.update(mutableRecord);
    }
    localCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject toJson(List<Circulation> pCirculation, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Circulation circulation : pCirculation) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, circulation, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<Circulation, MutableCirculation, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Circulation, MutableCirculation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Circulation pReadonly) {
    return pReadonly.getLastModified();
  }
}
