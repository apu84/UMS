package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by Monjur-E-Morshed on 08-May-17.
 */
@Component
public class LmsApplicationBuilder implements Builder<LmsApplication, MutableLmsApplication> {
  @Override
  public void build(JsonObjectBuilder pBuilder, LmsApplication pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("employeeId", pReadOnly.getEmployee().getId());
    pBuilder.add("employeeName", pReadOnly.getEmployee().getEmployeeName());
    Format formatter = new SimpleDateFormat("dd/MM/YYYY");
    pBuilder.add("fromDate", formatter.format(pReadOnly.getFromDate()));
    pBuilder.add("toDate", formatter.format(pReadOnly.getToDate()));
    pBuilder.add("reason", pReadOnly.getReason());
  }

  @Override
  public void build(MutableLmsApplication pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(pJsonObject.getInt("id"));
    if(pJsonObject.containsKey("employeeId"))
      pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    if(pJsonObject.containsKey("typeId"))
      pMutable.setLeaveTypeId(pJsonObject.getInt("typeId"));
    if(pJsonObject.containsKey("appliedOn"))
      pMutable.setAppliedOn(UmsUtils.convertToDate(pJsonObject.getString("appliedOn"), "DD/MM/YYYY"));
    if(pJsonObject.containsKey("fromDate"))
      pMutable.setFromDate(UmsUtils.convertToDate(pJsonObject.getString("fromdate"), "DD/MM/YYYY"));
    if(pJsonObject.containsKey("todate"))
      pMutable.setToDate(UmsUtils.convertToDate(pJsonObject.getString("toDate"), "DD/MM/YYYY"));
    if(pJsonObject.containsKey("reason"))
      pMutable.setReason(pJsonObject.getString("reason"));
  }
}
