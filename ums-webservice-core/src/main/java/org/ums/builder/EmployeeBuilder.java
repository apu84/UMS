package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.enums.common.DesignationType;
import org.ums.formatter.DateFormat;
import org.ums.manager.DesignationManager;
import org.ums.persistent.model.PersistentDepartment;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class EmployeeBuilder implements Builder<Employee, MutableEmployee> {
  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Autowired
  private DesignationManager mDesignationManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, Employee pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("text", pReadOnly.getId());
    pBuilder.add("employeeName", pReadOnly.getPersonalInformation().getFullName());
    pBuilder.add("name", pReadOnly.getPersonalInformation().getFullName());
    pBuilder.add("designation", pReadOnly.getDesignationId());
    pBuilder.add("designationName", mDesignationManager.get(pReadOnly.getDesignationId()).getDesignationName());
    pBuilder.add("employmentType", pReadOnly.getEmploymentType());
    pBuilder.add("deptOfficeId", pReadOnly.getDepartment().getId());
    pBuilder.add("deptOfficeName", pReadOnly.getDepartment().getType() == 1 ? pReadOnly.getDepartment().getShortName()
        : pReadOnly.getDepartment().getLongName());
    pBuilder.add("joiningDate",
        pReadOnly.getJoiningDate() == null ? null : mDateFormat.format(pReadOnly.getJoiningDate()));
    pBuilder.add("status", pReadOnly.getStatus());
    pBuilder.add("statusName", pReadOnly.getStatus() == 1 ? "Active" : pReadOnly.getStatus() == 2 ? "On Leave"
        : pReadOnly.getStatus() == 3 ? "Inactive" : "Unknown");
    pBuilder.add("shortName", pReadOnly.getShortName() == null ? "" : pReadOnly.getShortName());
    pBuilder.add("employeeType", pReadOnly.getEmployeeType());
    pBuilder.add("self",
        pUriInfo.getBaseUriBuilder().path("academic").path("semesterWithdraw").path(pReadOnly.getId().toString())
            .build().toString());
  }

  @Override
  public void build(MutableEmployee pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.getString("id"));
    try {
      pMutable.setDesignationId(pJsonObject.getInt("designation"));
    } catch(Exception e) {
      pMutable.setDesignationId(pJsonObject.getJsonObject("designation").getInt("id"));
    }
    try {
      pMutable.setEmploymentType(pJsonObject.getString("employmentType"));
    } catch(Exception e) {
      pMutable.setEmploymentType(String.valueOf(pJsonObject.getJsonObject("employmentType").getInt("id")));
    }
    PersistentDepartment dept = new PersistentDepartment();
    try {
      dept.setId(pJsonObject.getString("deptOfficeId"));
    } catch(Exception e) {
      dept.setId(pJsonObject.getJsonObject("department").getString("id"));
    }
    pMutable.setDepartment(dept);
    pMutable.setJoiningDate(mDateFormat.parse(pJsonObject.getString("joiningDate")));
    // pMutable.setStatus(1);
    pMutable.setShortName(pJsonObject.containsKey("shortName") ? pJsonObject.getString("shortName").isEmpty() ? ""
        : pJsonObject.getString("shortName") : "");
    try {
      pMutable.setEmployeeType(pJsonObject.getInt("employeeType"));
    } catch(Exception e) {
      pMutable.setEmployeeType(pJsonObject.getJsonObject("employeeType").getInt("id"));
    }
    pMutable.setStatus(pJsonObject.getInt("status"));
  }

  public void customBuilderForEmployee(JsonObjectBuilder pBuilder, Employee pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {

    DesignationType designationType = null;
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getPersonalInformation().getFullName());
    pBuilder.add("designation", DesignationType.get(pReadOnly.getDesignationId()).getLabel());
    // if(pReadOnly.getEmploymentType().equals(EmploymentType.PERMANENT.getLabel()))
    // pBuilder.add("employmentType", "Permanent");
    // else if(pReadOnly.getEmploymentType().equals(EmploymentType.CONTRACTUAL.getLabel()))
    // pBuilder.add("employmentType", "Contractual");
    // else if(pReadOnly.getEmploymentType().equals(EmploymentType.PROVISIONAL.getLabel()))
    // pBuilder.add("employmentType", "Provisional");
    // else if(pReadOnly.getEmploymentType().equals(EmploymentType.PARTTIME.getLabel()))
    // pBuilder.add("employmentType", "Part Time");
    pBuilder.add("department", pReadOnly.getDepartment().getLongName());
  }
}
