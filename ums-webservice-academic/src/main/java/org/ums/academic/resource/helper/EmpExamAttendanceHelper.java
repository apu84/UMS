package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.EmpExamAttendanceBuilder;
import org.ums.domain.model.immutable.EmpExamAttendance;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.manager.*;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
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
