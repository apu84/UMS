package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.AbsLateComingInfoBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AbsLateComingInfo;
import org.ums.domain.model.mutable.MutableAbsLateComingInfo;
import org.ums.ems.profilemanagement.personal.PersonalInformationManager;
import org.ums.enums.ProgramType;
import org.ums.enums.common.EmployeeType;
import org.ums.manager.*;
import org.ums.persistent.dao.PersistentAbsLateComingInfo;
import org.ums.report.generator.examAttendance.ExamAttendantType;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
@Component
public class AbsLateComingInfoResourceHelper extends ResourceHelper<AbsLateComingInfo, MutableAbsLateComingInfo, Long> {
  @Autowired
  AbsLateComingInfoManager mAbsLateComingInfoManager;
  @Autowired
  StudentManager mStudentManager;
  @Autowired
  SemesterManager mSemesterManager;
  @Autowired
  AbsLateComingInfoBuilder mBuilder;
  @Autowired
  AbsLateComingInfoManager mManager;
  @Autowired
  PersonalInformationManager mPersonalInformationManager;
  @Autowired
  ClassRoomManager mClassRoomManager;
  @Autowired
  EmployeeManager mEmployeeManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentAbsLateComingInfo application = new PersistentAbsLateComingInfo();
    application.setSemesterId(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
    getBuilder().build(application, jsonObject, localCache);
    try {
      mManager.create(application);
    } catch(Exception e) {
      e.printStackTrace();
    }
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response deleteRecords(JsonObject pJsonObject, UriInfo pUriInfo) {
    List<MutableAbsLateComingInfo> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentAbsLateComingInfo application = new PersistentAbsLateComingInfo();
      getBuilder().build(application, jsonObject, localCache);
      applications.add(application);
    }
    mManager.delete(applications);

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getAbsLateComeInfoList(final Integer pSemesterId, final Integer pExamType, final Request pRequest,
      final UriInfo pUriInfo) {
    List<AbsLateComingInfo> absLateComingInfoList = mManager.getInfoBySemesterExamType(pSemesterId, pExamType);
    List<MutableAbsLateComingInfo> list = new ArrayList<>();
    for(AbsLateComingInfo app : absLateComingInfoList) {
      MutableAbsLateComingInfo mutableAbsLateComingInfo = new PersistentAbsLateComingInfo();
      mutableAbsLateComingInfo.setEmployeeId(app.getEmployeeId());
      mutableAbsLateComingInfo.setEmployeeName(mPersonalInformationManager.get(app.getEmployeeId()).getFullName());
      mutableAbsLateComingInfo.setSemesterId(app.getSemesterId());
      mutableAbsLateComingInfo.setExamType(app.getExamType());
      mutableAbsLateComingInfo.setPresentType(app.getPresentType());
      mutableAbsLateComingInfo.setPresentInfo(ExamAttendantType.get(app.getPresentType()).getLabel());
      mutableAbsLateComingInfo.setRemarks(app.getRemarks());
      mutableAbsLateComingInfo.setInvigilatorRoomId(app.getInvigilatorRoomId());
      mutableAbsLateComingInfo.setInvigilatorRoomName(mClassRoomManager.get(app.getInvigilatorRoomId()).getRoomNo());
      mutableAbsLateComingInfo.setExamDate(app.getExamDate());
      mutableAbsLateComingInfo.setArrivalTime(app.getArrivalTime());
      mutableAbsLateComingInfo.setDeptId(mEmployeeManager.get(app.getEmployeeId()).getDepartment().getId());
      mutableAbsLateComingInfo.setDeptName(mEmployeeManager.get(app.getEmployeeId()).getDepartment().getShortName());
      mutableAbsLateComingInfo.setEmployeeType(EmployeeType.get(
          mEmployeeManager.get(app.getEmployeeId()).getEmployeeType()).getLabel());
      list.add(mutableAbsLateComingInfo);
    }
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(MutableAbsLateComingInfo app : list) {
      children.add(toJson(app, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected ContentManager<AbsLateComingInfo, MutableAbsLateComingInfo, Long> getContentManager() {
    return getContentManager();
  }

  @Override
  protected Builder<AbsLateComingInfo, MutableAbsLateComingInfo> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AbsLateComingInfo pReadonly) {
    return null;
  }
}
