package org.ums.resource.leavemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.EmployeeBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.formatter.DateFormat;
import org.ums.manager.EmployeeManager;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
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
  private EmployeeBuilder mEmployeeBuilder;
  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  @Qualifier("genericDateFormat")
  DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, LmsApplication pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("secondId", pReadOnly.getId());
    pBuilder.add("employeeId", pReadOnly.getEmployee().getId());
    pBuilder.add("employeeName", pReadOnly.getEmployee().getPersonalInformation().getFullName());
    JsonObjectBuilder employee = Json.createObjectBuilder();
    mEmployeeBuilder.build(employee, pReadOnly.getEmployee(), pUriInfo, pLocalCache);
    pBuilder.add("employee", employee);
    pBuilder.add("leaveType", pReadOnly.getLeaveTypeId());
    pBuilder.add("leaveTypeName", pReadOnly.getLmsType().getName());
    Format formatter = new SimpleDateFormat("dd/MM/YYYY");
    pBuilder.add("appliedOn", formatter.format(pReadOnly.getAppliedOn()));
    pBuilder.add("fromDate", formatter.format(pReadOnly.getFromDate()));
    pBuilder.add("toDate", formatter.format(pReadOnly.getToDate()));
    long diff = pReadOnly.getToDate().getTime() - pReadOnly.getFromDate().getTime();
    int duration = (int) (diff / (24 * 60 * 60 * 1000) + 1);
    pBuilder.add("duration", duration);
    pBuilder.add("reason", pReadOnly.getReason());
    pBuilder.add("status", pReadOnly.getApplicationStatus().getId());
    pBuilder.add("statusName", pReadOnly.getApplicationStatus().getLabel());
    pBuilder.add("submittedBy", pReadOnly.getSubmittedBy());
    JsonObjectBuilder submittedByEmp = Json.createObjectBuilder();
    mEmployeeBuilder.build(submittedByEmp, mEmployeeManager.get(pReadOnly.getSubmittedBy()), pUriInfo, pLocalCache);
    pBuilder.add("submittedByEmp", submittedByEmp);
  }

  @Override
  public void build(MutableLmsApplication pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id")) {
      pMutable.setId(Long.valueOf(pJsonObject.getInt("id")));
    }
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));

    pMutable.setLeaveTypeId(pJsonObject.getInt("typeId"));
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    try {

      pMutable.setFromDate(formatter.parse(pJsonObject.getString("fromDate")));

      pMutable.setToDate(formatter.parse(pJsonObject.getString("toDate")));
    } catch(Exception e) {

    }

    if(pJsonObject.containsKey("reason"))
      pMutable.setReason(pJsonObject.getString("reason"));
    if(pJsonObject.containsKey("appStatus"))
      pMutable.setLeaveApplicationStatus(LeaveApplicationApprovalStatus.get(pJsonObject.getInt("appStatus")));
  }
}
