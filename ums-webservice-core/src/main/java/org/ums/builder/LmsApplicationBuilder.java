package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.enums.common.LeaveApplicationStatus;
import org.ums.formatter.DateFormat;
import org.ums.manager.UserManager;

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

  @Autowired
  private UserManager mUserManager;

  @Autowired
  DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, LmsApplication pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("employeeId", pReadOnly.getEmployee().getId());
    pBuilder.add("employeeName", pReadOnly.getEmployee().getEmployeeName());
    pBuilder.add("leaveType", pReadOnly.getLeaveTypeId());
    pBuilder.add("leaveTypeName", pReadOnly.getLmsType().getName());
    Format formatter = new SimpleDateFormat("dd/MM/YYYY");
    pBuilder.add("appliedOn", formatter.format(pReadOnly.getAppliedOn()));
    pBuilder.add("fromDate", formatter.format(pReadOnly.getFromDate()));
    pBuilder.add("toDate", formatter.format(pReadOnly.getToDate()));
    pBuilder.add("reason", pReadOnly.getReason());
    pBuilder.add("status", pReadOnly.getApplicationStatus().getId());
    pBuilder.add("statusName", pReadOnly.getApplicationStatus().getLabel());
  }

  @Override
  public void build(MutableLmsApplication pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id")) {
      pMutable.setId(Long.valueOf(pJsonObject.getInt("id")));
    }
    if(pJsonObject.containsKey("employeeId")) {
      pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    }
    else {
      User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
      pMutable.setEmployeeId(user.getEmployeeId());
    }
    if(pJsonObject.containsKey("typeId"))
      pMutable.setLeaveTypeId(pJsonObject.getInt("typeId"));
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    try {

      if(pJsonObject.containsKey("fromDate"))
        pMutable.setFromDate(formatter.parse(pJsonObject.getString("fromDate")));
      if(pJsonObject.containsKey("toDate"))
        pMutable.setToDate(formatter.parse(pJsonObject.getString("toDate")));
    } catch(Exception e) {

    }

    if(pJsonObject.containsKey("reason"))
      pMutable.setReason(pJsonObject.getString("reason"));
    if(pJsonObject.containsKey("appStatus"))
      pMutable.setLeaveApplicationStatus(LeaveApplicationStatus.get(pJsonObject.getInt("appStatus")));
  }
}
