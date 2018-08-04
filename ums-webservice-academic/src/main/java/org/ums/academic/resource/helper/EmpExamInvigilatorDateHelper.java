package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.EmpExamAttendanceBuilder;
import org.ums.builder.EmpExamInvigilatorDateBuilder;
import org.ums.domain.model.immutable.EmpExamAttendance;
import org.ums.domain.model.immutable.EmpExamInvigilatorDate;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.domain.model.mutable.MutableEmpExamInvigilatorDate;
import org.ums.manager.ContentManager;
import org.ums.manager.EmpExamAttendanceManager;
import org.ums.manager.EmpExamInvigilatorDateManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
@Component
public class EmpExamInvigilatorDateHelper extends
    ResourceHelper<EmpExamInvigilatorDate, MutableEmpExamInvigilatorDate, Long> {
  @Autowired
  EmpExamInvigilatorDateBuilder mBuilder;
  @Autowired
  EmpExamInvigilatorDateManager mManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<EmpExamInvigilatorDate, MutableEmpExamInvigilatorDate, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<EmpExamInvigilatorDate, MutableEmpExamInvigilatorDate> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(EmpExamInvigilatorDate pReadonly) {
    return null;
  }
}
