package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.EmpExamAttendanceBuilder;
import org.ums.builder.EmpExamInvigilatorDateBuilder;
import org.ums.builder.EmpExamReserveDateBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.EmpExamAttendance;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.domain.model.mutable.MutableEmpExamInvigilatorDate;
import org.ums.domain.model.mutable.MutableEmpExamReserveDate;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.generator.IdGenerator;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentEmpExamAttendance;
import org.ums.persistent.model.PersistentEmpExamInvigilatorDate;
import org.ums.persistent.model.PersistentEmpExamReserveDate;
import org.ums.resource.ResourceHelper;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
  EmpExamInvigilatorDateBuilder mEmpExamInvigilatorDateBuilder;
  @Autowired
  EmpExamReserveDateBuilder mEmpExamReserveDateBuilder;

  @Override
  @Transactional
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    Long id = mIdGenerator.getNumericId();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentEmpExamAttendance application = new PersistentEmpExamAttendance();
    application.setId(id);
    application.setSemesterId(11012017);
    getBuilder().build(application, jsonObject, localCache);
    String invigilatorDate[] = application.getInvigilatorDate().split(",");
    String reverseDate[] = application.getReserveDate().split(",");

    List<MutableEmpExamInvigilatorDate> invigilatorDateList = new ArrayList<>();
    for(int i = 0; i < invigilatorDate.length; i++) {
      PersistentEmpExamInvigilatorDate empExamInvigilatorDate = new PersistentEmpExamInvigilatorDate();
      empExamInvigilatorDate.setAttendantId(id);
      empExamInvigilatorDate.setExamDate(invigilatorDate[i]);
      invigilatorDateList.add(empExamInvigilatorDate);
    }
    List<MutableEmpExamReserveDate> reserveDateList = new ArrayList<>();
    for(int j = 0; j < reverseDate.length; j++) {
      PersistentEmpExamReserveDate empExamReserveDate = new PersistentEmpExamReserveDate();
      empExamReserveDate.setAttendantId(id);
      empExamReserveDate.setExamDate(reverseDate[j]);
      reserveDateList.add(empExamReserveDate);
    }

    try {
      mManager.create(application);
      mEmpExamInvigilatorDateManager.create(invigilatorDateList);
      mEmpExamReserveDateManager.create(reserveDateList);
    } catch(Exception e) {
      e.printStackTrace();
    }

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
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
