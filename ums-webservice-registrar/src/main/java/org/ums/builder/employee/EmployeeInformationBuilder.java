package org.ums.builder.employee;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.Employee.EmployeeInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableEmployeeInformation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class EmployeeInformationBuilder implements Builder<EmployeeInformation, MutableEmployeeInformation> {
  @Override
  public void build(JsonObjectBuilder pBuilder, EmployeeInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("employeeDesignation", pReadOnly.getDesignation());
    pBuilder.add("employeeEmploymentType", pReadOnly.getEmploymentType());
    pBuilder.add("employeeDepartment", pReadOnly.getDeptOffice());
    pBuilder.add("employeeJoiningDate", pReadOnly.getJobPermanentDate());
    pBuilder.add("employeeJobPermanentDate", pReadOnly.getJobPermanentDate());
    pBuilder.add("employeeExtensionNumber", pReadOnly.getExtNo());
    pBuilder.add("employeeShortName", pReadOnly.getShortName());
    pBuilder.add("employeeRoomNumber", pReadOnly.getRoomNo());
    pBuilder.add("employeeResignDate", pReadOnly.getJobTerminationDate());
  }

  @Override
  public void build(MutableEmployeeInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getInt("employeeId"));
    pMutable.setDesignation(pJsonObject.getInt("employeeDesignation"));
    pMutable.setEmploymentType(pJsonObject.getString("employeeEmploymentType"));
    pMutable.setDeptOffice(pJsonObject.getString("employeeDepartment"));
    pMutable.setJoiningDate(pJsonObject.getString("employeeJoiningDate"));
    pMutable.setJobPermanentDate(pJsonObject.getString("employeeJobPermanentDate"));
    pMutable.setExtNo(pJsonObject.getInt("employeeExtensionNumber"));
    pMutable.setShortName(pJsonObject.getString("employeeShortName"));
    pMutable.setRoomNo(pJsonObject.getString("employeeRoomNumber"));
    pMutable.setJobTerminationDate(pJsonObject.getString("employeeResignDate"));
  }
}
