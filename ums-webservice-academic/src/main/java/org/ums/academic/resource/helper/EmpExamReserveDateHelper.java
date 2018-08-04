package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.EmpExamReserveDateBuilder;
import org.ums.domain.model.immutable.EmpExamReserveDate;
import org.ums.domain.model.mutable.MutableEmpExamReserveDate;
import org.ums.manager.ContentManager;
import org.ums.manager.EmpExamReserveDateManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
@Component
public class EmpExamReserveDateHelper extends ResourceHelper<EmpExamReserveDate, MutableEmpExamReserveDate, Long> {
  @Autowired
  EmpExamReserveDateBuilder mBuilder;
  @Autowired
  EmpExamReserveDateManager mManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<EmpExamReserveDate, MutableEmpExamReserveDate, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<EmpExamReserveDate, MutableEmpExamReserveDate> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(EmpExamReserveDate pReadonly) {
    return null;
  }
}
