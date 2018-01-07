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
import org.ums.manager.ContentManager;
import org.ums.manager.library.CirculationManager;
import org.ums.manager.library.ItemManager;
import org.ums.persistent.model.library.PersistentCirculation;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class CirculationResourceHelper extends ResourceHelper<Circulation, MutableCirculation, Long> {

  @Autowired
  CirculationManager mManager;

  @Autowired
  CirculationBuilder mBuilder;

  @Autowired
  ItemManager mItemManager;

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
    mItemManager.updateItemCirculationStatus(mutableCirculation.getAccessionNumber(), 1);
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
    mItemManager.updateItemCirculationStatus(mutableCirculation.getAccessionNumber(), 0);
    localCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Transactional
  public Response updateCirculation(JsonObject pJsonObject, UriInfo pUriInfo) {
    List<MutableCirculation> mutableCirculation = new ArrayList<>();
    LocalCache localCache = new LocalCache();
    for(int i = 0; i < pJsonObject.size(); i++) {
      MutableCirculation mutableCirculation1 = new PersistentCirculation();
      mBuilder.build(mutableCirculation1, pJsonObject.getJsonArray("entries").getJsonObject(i), localCache);
      mutableCirculation.add(mutableCirculation1);
    }
    mManager.batchUpdateCirculation(mutableCirculation);
    for(MutableCirculation mutableCirculation1 : mutableCirculation) {
      mItemManager.updateItemCirculationStatus(mutableCirculation1.getAccessionNumber(), 0);
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
