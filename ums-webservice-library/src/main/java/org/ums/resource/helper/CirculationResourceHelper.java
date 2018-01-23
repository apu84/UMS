package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.CirculationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.fee.FeeCategory;
import org.ums.fee.dues.MutableStudentDues;
import org.ums.fee.dues.PersistentStudentDues;
import org.ums.fee.dues.StudentDues;
import org.ums.fee.dues.StudentDuesManager;
import org.ums.formatter.DateFormat;
import org.ums.manager.ContentManager;
import org.ums.manager.library.CirculationManager;
import org.ums.manager.library.FineManager;
import org.ums.manager.library.ItemManager;
import org.ums.manager.library.RecordManager;
import org.ums.persistent.model.library.PersistentCirculation;
import org.ums.persistent.model.library.PersistentFine;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

  @Autowired
  private FineManager mFineManager;

  @Autowired
  private UserManager mUserManager;

  @Autowired
  private StudentDuesManager mStudentDuesManager;

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
    MutableCirculation mutableCirculation1 = new PersistentCirculation();
    mutableCirculation1 =
        (MutableCirculation) mManager.getSingleCirculation(pJsonObject.getJsonObject("entries").getString("itemCode"));
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
    mutableCirculation1.setReturnDate(mutableCirculation.getReturnDate());
    /* fineCalculator(mutableCirculation1); */
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

  private void fineCalculator(MutableCirculation pMutableCirculation) {
    /*
     * String userId = SecurityUtils.getSubject().getPrincipal().toString(); User user =
     * mUserManager.get(userId); long dueDate = pMutableCirculation.getDueDate().getTime(); long
     * returnDate = pMutableCirculation.getReturnDate().getTime(); long diff1 =
     * Math.abs(TimeUnit.DAYS.convert((dueDate - returnDate), TimeUnit.MILLISECONDS));
     * System.out.println("diff1: " + diff1); if(dueDate < returnDate) { MutableFine mutableFine =
     * new PersistentFine(); mutableFine.setPatronId(pMutableCirculation.getPatronId());
     * mutableFine.setCirculationId(pMutableCirculation.getId()); mutableFine.setDescription("");
     * mutableFine.setAmount(5 * diff1); mutableFine.setFineAppliedBy(user.getEmployeeId());
     * if(pMutableCirculation.getFineStatus() == 2 || pMutableCirculation.getFineStatus() == 1) {
     * mutableFine.setFineForgivenBy(user.getEmployeeId()); } else {
     * mutableFine.setFineForgivenBy(null); } mutableFine.setFineCategory(1);
     * mutableFine.setFinePaymentDate(null);
     * mutableFine.setFineAppliedDate(pMutableCirculation.getDueDate());
     * mFineManager.saveFine(mutableFine);
     * 
     * MutableStudentDues mutableStudentDues = new PersistentStudentDues();
     * mutableStudentDues.setFeeCategoryId("99");
     * mutableStudentDues.setDescription("No Description");
     * mutableStudentDues.setStudentId(pMutableCirculation.getPatronId());
     * mutableStudentDues.setAmount(BigDecimal.valueOf(5 * diff1));
     * mutableStudentDues.setAddedOn(new Date());
     * mutableStudentDues.setUserId(user.getEmployeeId()); mutableStudentDues.setPayBefore(new
     * Date()); mutableStudentDues.setStatus(StudentDues.Status.APPLIED);
     * 
     * mStudentDuesManager.create(mutableStudentDues); }
     */
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
