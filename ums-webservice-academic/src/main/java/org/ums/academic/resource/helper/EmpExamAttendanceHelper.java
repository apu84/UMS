package org.ums.academic.resource.helper;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.EmpExamAttendanceBuilder;
import org.ums.builder.EmpExamInvigilatorDateBuilder;
import org.ums.builder.EmpExamReserveDateBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.EmpExamAttendance;
import org.ums.domain.model.immutable.EmpExamInvigilatorDate;
import org.ums.domain.model.immutable.EmpExamReserveDate;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.domain.model.mutable.MutableEmpExamInvigilatorDate;
import org.ums.domain.model.mutable.MutableEmpExamReserveDate;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.enums.accounts.definitions.MonthType;
import org.ums.generator.IdGenerator;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentEmpExamAttendance;
import org.ums.persistent.model.PersistentEmpExamInvigilatorDate;
import org.ums.persistent.model.PersistentEmpExamReserveDate;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
@Component
public class EmpExamAttendanceHelper extends ResourceHelper<EmpExamAttendance, MutableEmpExamAttendance, Long> {
  @Autowired
  StudentManager mStudentManager;
  @Autowired
  SemesterManager mSemesterManager;
  @Autowired
  EmpExamAttendanceBuilder mBuilder;
  @Autowired
  EmpExamAttendanceManager mManager;
  @Autowired
  PersonalInformationManager mPersonalInformationManager;
  @Autowired
  ClassRoomManager mClassRoomManager;
  @Autowired
  EmployeeManager mEmployeeManager;
  @Autowired
  IdGenerator mIdGenerator;
  @Autowired
  EmpExamInvigilatorDateManager mEmpExamInvigilatorDateManager;
  @Autowired
  EmpExamReserveDateManager mEmpExamReserveDateManager;
  @Autowired
  DepartmentManager mDepartmentManager;

  @Override
  @Transactional
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    Long id = mIdGenerator.getNumericId();
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentEmpExamAttendance application = new PersistentEmpExamAttendance();
    application.setId(id);
    application.setSemesterId(11012017);
    getBuilder().build(application, jsonObject, localCache);

    String invigilatorDate[] = application.getInvigilatorDate().split(",");
    String reverseDate[] = application.getReserveDate().split(",");

    List<MutableEmpExamInvigilatorDate> invigilatorDateList = getMutableEmpExamInvigilatorDate(id, invigilatorDate);
    List<MutableEmpExamReserveDate> reserveDateList = getMutableEmpExamReserveDate(id, reverseDate);
    addInfo(application, invigilatorDateList, reserveDateList);

    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private List<MutableEmpExamReserveDate> getMutableEmpExamReserveDate(Long id, String[] reverseDate) {
    List<MutableEmpExamReserveDate> reserveDateList = new ArrayList<>();
    for(int j = 0; j < reverseDate.length; j++) {
      PersistentEmpExamReserveDate empExamReserveDate = new PersistentEmpExamReserveDate();
      empExamReserveDate.setAttendantId(id);
      empExamReserveDate.setExamDate(reverseDate[j]);
      reserveDateList.add(empExamReserveDate);
    }
    return reserveDateList;
  }

  private List<MutableEmpExamInvigilatorDate> getMutableEmpExamInvigilatorDate(Long id, String[] invigilatorDate) {
    List<MutableEmpExamInvigilatorDate> invigilatorDateList = new ArrayList<>();
    for(int i = 0; i < invigilatorDate.length; i++) {
      PersistentEmpExamInvigilatorDate empExamInvigilatorDate = new PersistentEmpExamInvigilatorDate();
      empExamInvigilatorDate.setAttendantId(id);
      empExamInvigilatorDate.setExamDate(invigilatorDate[i]);
      invigilatorDateList.add(empExamInvigilatorDate);
    }
    return invigilatorDateList;
  }

  @Transactional
  public Response UpdateRecords(JsonObject pJsonObject, UriInfo pUriInfo) {
    URI contextURI = null;
    Long id = mIdGenerator.getNumericId();
    Response.ResponseBuilder builder = Response.created(contextURI);
    JsonArray entries = pJsonObject.getJsonArray("entries");

    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    JsonObject jsonObjectForAdd = entries.getJsonObject(0);
    PersistentEmpExamAttendance application = new PersistentEmpExamAttendance();
    getBuilder().build(application, jsonObject, localCache);
    PersistentEmpExamInvigilatorDate empExamInvigilatorDate = new PersistentEmpExamInvigilatorDate();
    empExamInvigilatorDate.setAttendantId(application.getId());
    PersistentEmpExamReserveDate empExamReserveDate = new PersistentEmpExamReserveDate();
    empExamReserveDate.setAttendantId(application.getId());
    try {

        mEmpExamInvigilatorDateManager.delete(empExamInvigilatorDate);
        mEmpExamReserveDateManager.delete(empExamReserveDate);
         mManager.delete(application);

      PersistentEmpExamAttendance app = new PersistentEmpExamAttendance();
      getBuilder().build(app, jsonObjectForAdd, localCache);
      app.setId(id);
      app.setSemesterId(11012017);

      String invigilatorDate[] = app.getInvigilatorDate().split(",");
      String reverseDate[] = app.getReserveDate().split(",");

      List<MutableEmpExamInvigilatorDate> invigilatorDateList = getMutableEmpExamInvigilatorDate(id, invigilatorDate);
      List<MutableEmpExamReserveDate> reserveDateList = getMutableEmpExamReserveDate(id, reverseDate);
      addInfo(app, invigilatorDateList, reserveDateList);

      builder.status(Response.Status.ACCEPTED);
    } catch(Exception e) {
      e.printStackTrace();
      builder.status(Response.Status.NOT_FOUND);
    }
    return builder.build();
  }

  private void addInfo(PersistentEmpExamAttendance application,
      List<MutableEmpExamInvigilatorDate> invigilatorDateList, List<MutableEmpExamReserveDate> reserveDateList) {
    try {
      mManager.create(application);
      mEmpExamInvigilatorDateManager.create(invigilatorDateList);
      mEmpExamReserveDateManager.create(reserveDateList);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  @Transactional
  public Response deleteRecords(JsonObject pJsonObject, UriInfo pUriInfo) {
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    JsonArray entries = pJsonObject.getJsonArray("entries");

    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentEmpExamAttendance application = new PersistentEmpExamAttendance();
    getBuilder().build(application, jsonObject, localCache);
    PersistentEmpExamInvigilatorDate empExamInvigilatorDate = new PersistentEmpExamInvigilatorDate();
    empExamInvigilatorDate.setAttendantId(application.getId());
    PersistentEmpExamReserveDate empExamReserveDate = new PersistentEmpExamReserveDate();
    empExamReserveDate.setAttendantId(application.getId());
    try {
      mEmpExamInvigilatorDateManager.delete(empExamInvigilatorDate);
      mEmpExamReserveDateManager.delete(empExamReserveDate);
      mManager.delete(application);
      builder.status(Response.Status.ACCEPTED);
    } catch(Exception e) {
      e.printStackTrace();
      builder.status(Response.Status.NOT_FOUND);
    }

    return builder.build();
  }

  public String parseExamDate(String pExamDate) {
    if(pExamDate.length() < 0 || pExamDate.equals("") || pExamDate.equals(null)) {
      return " ";
    }
    else {
      String dates[] = pExamDate.split(",");
      String newDate = "";
      Map<String, String> dayMonthMap = new HashMap<>();
      for(int i = 0; i < dates.length; i++) {

        String dayMonth[] = dates[i].split("-");
        if(dayMonthMap.containsKey(dayMonth[1])) {
          String oldValue = dayMonthMap.get(dayMonth[1]);
          dayMonthMap.put(dayMonth[1], oldValue = oldValue + ", " + dayMonth[0]);
        }
        else {
          dayMonthMap.put(dayMonth[1], dayMonth[0]);
        }
      }
      int counter = 0;
      for(Map.Entry<String, String> entry : dayMonthMap.entrySet()) {
        if(counter > 0) {
          newDate =
              newDate + ", and " + MonthType.get(Integer.parseInt(entry.getKey())) + ": " + entry.getValue() + " ";
        }
        else {
          newDate = newDate + MonthType.get(Integer.parseInt(entry.getKey())) + ": " + entry.getValue();
        }
        counter = counter + 1;
      }
      return newDate;
    }
  }

  public JsonObject getEmpExamAttendanceInfo(final Integer pSemesterId, final Integer pExamType,
      final Request pRequest, final UriInfo pUriInfo) {
    List<MutableEmpExamAttendance> list = getMutableEmpExamAttendances(pSemesterId, pExamType);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(MutableEmpExamAttendance app : list) {
      children.add(toJson(app, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public List<MutableEmpExamAttendance> getMutableEmpExamAttendances(Integer pSemesterId, Integer pExamType) {
    List<EmpExamAttendance> empExamAttendances=mManager.getInfoBySemesterAndExamType(pSemesterId,pExamType);
    List<EmpExamInvigilatorDate> empExamInvigilatorDate=mEmpExamInvigilatorDateManager.getBySemesterAndExamType(pSemesterId,pExamType);
    List<EmpExamReserveDate> empExamReserveDate=mEmpExamReserveDateManager.getBySemesterAndExamType(pSemesterId, pExamType);
    Map<Long,String> empExamInvigilatorDateMap=empExamInvigilatorDate.stream().collect(
                    Collectors.toMap(a->a.getAttendantId(), a->a.getExamDate()==null? "":a.getExamDate(),(oldValue, newValue) -> oldValue=oldValue+","+newValue
                    ));
    Map<Long,String> empExamReserveDateMap=empExamReserveDate.stream().collect(Collectors.toMap(
            e->e.getAttendantId(),e->e.getExamDate()==null? "":e.getExamDate(),(oldValue, newValue) -> oldValue=oldValue+","+newValue
    ));
    List<MutableEmpExamAttendance> list = new ArrayList<>();
    for(EmpExamAttendance app: empExamAttendances){
      PersistentEmpExamAttendance innerList= new PersistentEmpExamAttendance();
      innerList.setId(app.getId());
      innerList.setSemesterId(app.getSemesterId());
      innerList.setExamType(app.getExamType());
      innerList.setRoomInCharge(app.getRoomInCharge());
      innerList.setInvigilatorRoomId(app.getInvigilatorRoomId());
      innerList.setInvigilatorRoomName(mClassRoomManager.get(app.getInvigilatorRoomId()).getRoomNo());
      innerList.setEmployeeId(app.getEmployeeId());
      innerList.setEmployees(mEmployeeManager.get(innerList.getEmployeeId()));
      innerList.setEmployeeType(mEmployeeManager.get(app.getEmployeeId()).getEmployeeType());
      innerList.setDepartmentId(mEmployeeManager.get(app.getEmployeeId()).getDepartment().getId());
      innerList.setDepartment(mDepartmentManager.get(innerList.getDepartmentId()));
      innerList.setDesignationId(app.getEmployees().getDesignationId());
      innerList.setInvigilatorDate(parseExamDate(empExamInvigilatorDateMap.get(app.getId())));
      innerList.setReserveDate(parseExamDate(empExamReserveDateMap.get(app.getId())));
      innerList.setInvigilatorDateForUpdate(empExamInvigilatorDateMap.get(app.getId()));
      innerList.setReserveDateForUpdate(empExamReserveDateMap.get(app.getId()));
      list.add(innerList);
    }
    return list;
  }

  @Override
  protected ContentManager<EmpExamAttendance, MutableEmpExamAttendance, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<EmpExamAttendance, MutableEmpExamAttendance> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(EmpExamAttendance pReadonly) {
    return null;
  }
}
