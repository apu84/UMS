package org.ums.ems.createnew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.enums.common.EmployeeType;
import org.ums.enums.common.EmploymentType;
import org.ums.enums.common.Salutation;
import org.ums.formatter.DateFormat;
import org.ums.manager.DepartmentManager;
import org.ums.manager.DesignationManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class EmployeeCreateRequestBuilder implements Builder<EmployeeCreateRequest, MutableEmployeeCreateRequest> {

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Autowired
  private DepartmentManager mDepartmentManager;

  @Autowired
  private DesignationManager mDesignationManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, EmployeeCreateRequest pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("email", pReadOnly.getEmail() == null ? "" : pReadOnly.getEmail());
    pBuilder.add("salutation", Json.createObjectBuilder().add("id", pReadOnly.getSalutation()).add("name",
            Salutation.get(pReadOnly.getSalutation()).getLabel()));
    pBuilder.add("employeeName", pReadOnly.getEmployeeName());
    pBuilder.add(
        "department",
        Json.createObjectBuilder().add("id", pReadOnly.getDepartmentId())
            .add("shortName", mDepartmentManager.get(pReadOnly.getDepartmentId()).getShortName())
            .add("longName", mDepartmentManager.get(pReadOnly.getDepartmentId()).getLongName())
            .add("type", mDepartmentManager.get(pReadOnly.getDepartmentId()).getType()));
    pBuilder.add(
        "employeeType",
        Json.createObjectBuilder().add("id", pReadOnly.getEmployeeType())
            .add("name", EmployeeType.get(pReadOnly.getEmployeeType()).getLabel()));
    pBuilder.add(
        "designation",
        Json.createObjectBuilder().add("id", pReadOnly.getDesignation())
            .add("name", mDesignationManager.get(pReadOnly.getDesignation()).getDesignationName())
            .add("employeeType", mDesignationManager.get(pReadOnly.getDesignation()).getEmployeeType()));
    pBuilder.add(
        "employmentType",
        Json.createObjectBuilder().add("id", pReadOnly.getEmploymentType())
            .add("name", EmploymentType.get(pReadOnly.getEmploymentType()).getLabel()));
    pBuilder.add("joiningDate", mDateFormat.format(pReadOnly.getJoiningDate()));
    pBuilder.add("createAccount", pReadOnly.getCreateAccount() == 1 ? "Yes" : "No");
    pBuilder.add("requestedBy", pReadOnly.getRequestedBy() == null ? "" : pReadOnly.getRequestedBy());
    pBuilder.add("requestedOn",
        pReadOnly.getRequestedOn() == null ? "" : mDateFormat.format(pReadOnly.getRequestedOn()));
    pBuilder.add("actionTakenBy", pReadOnly.getActionTakenBy() == null ? "" : pReadOnly.getActionTakenBy());
    pBuilder.add("actionTakenOn",
        pReadOnly.getActionTakenOn() == null ? "" : mDateFormat.format(pReadOnly.getActionTakenOn()));
    pBuilder.add("actionStatus", pReadOnly.getActionStatus());
  }

  @Override
  public void build(MutableEmployeeCreateRequest pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.getString("id"));
    pMutable.setEmail(pJsonObject.getString("email"));
    pMutable.setSalutation(pJsonObject.getJsonObject("salutation").getInt("id"));
    pMutable.setEmployeeName(pJsonObject.getString("employeeName"));
    pMutable.setDepartmentId(pJsonObject.getJsonObject("department").getString("id"));
    pMutable.setEmployeeType(pJsonObject.getJsonObject("employeeType").getInt("id"));
    pMutable.setDesignation(pJsonObject.getJsonObject("designation").getInt("id"));
    pMutable.setEmploymentType(pJsonObject.getJsonObject("employmentType").getInt("id"));
    pMutable.setJoiningDate(mDateFormat.parse(pJsonObject.getString("joiningDate")));
    pMutable.setCreateAccount(pJsonObject.getBoolean("createAccount") ? 1 : 2);
    pMutable.setActionStatus(pJsonObject.containsKey("actionStatus") ? pJsonObject.getInt("actionStatus") : 0);
  }
}
