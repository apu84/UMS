package org.ums.ems.createnew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.formatter.DateFormat;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class EmployeeCreateRequestBuilder implements Builder<EmployeeCreateRequest, MutableEmployeeCreateRequest> {

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, EmployeeCreateRequest pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("email", pReadOnly.getEmail());
    pBuilder.add("salutation", pReadOnly.getSalutation());
    pBuilder.add("employeeName", pReadOnly.getEmployeeName());
    pBuilder.add("departmentId", pReadOnly.getDepartmentId());
    pBuilder.add("employeeType", pReadOnly.getEmployeeType());
    pBuilder.add("designation", pReadOnly.getDesignation());
    pBuilder.add("employmentType", pReadOnly.getEmploymentType());
    pBuilder.add("joiningDate", mDateFormat.format(pReadOnly.getJoiningDate()));
    pBuilder.add("createAccount", pReadOnly.getCreateAccount());
    pBuilder.add("requestedBy", pReadOnly.getRequestedBy());
    pBuilder.add("requestedOn", mDateFormat.format(pReadOnly.getRequestedOn()));
    pBuilder.add("actionTakenBy", pReadOnly.getActionTakenBy());
    pBuilder.add("actionTakenOn", mDateFormat.format(pReadOnly.getActionTakenOn()));
    pBuilder.add("actionStatus", pReadOnly.getActionStatus());
  }

  @Override
  public void build(MutableEmployeeCreateRequest pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.getString("id"));
    pMutable.setEmail(pJsonObject.getString("email"));
    pMutable.setSalutation(pJsonObject.getInt("salutation"));
    pMutable.setEmployeeName(pJsonObject.getString("employeeName"));
    pMutable.setDepartmentId(pJsonObject.getString("department"));
    pMutable.setEmployeeType(pJsonObject.getInt("employeeType"));
    pMutable.setDesignation(pJsonObject.getInt("designation"));
    pMutable.setEmploymentType(pJsonObject.getInt("employmentType"));
    pMutable.setJoiningDate(mDateFormat.parse(pJsonObject.getString("joiningDate")));
    pMutable.setCreateAccount(pJsonObject.getInt("createAccount"));
    pMutable.setRequestedBy(pJsonObject.getString("requestedBy"));
    pMutable.setRequestedOn(mDateFormat.parse(pJsonObject.getString("requestedOn")));
    pMutable.setActionTakenBy(pJsonObject.getString("actionTakenBy"));
    pMutable.setActionTakenOn(mDateFormat.parse(pJsonObject.getString("actionTakenOn")));
    pMutable.setActionStatus(pJsonObject.getInt("actionStatus"));
  }
}
