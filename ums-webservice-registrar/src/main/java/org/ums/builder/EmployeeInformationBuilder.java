package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.*;
import org.ums.domain.model.mutable.registrar.*;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class EmployeeInformationBuilder implements Builder<ServiceInformation, MutableServiceInformation> {

  @Override
  public void build(JsonObjectBuilder pBuilder, ServiceInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("employeeDesignation", pReadOnly.getDesignation());
    pBuilder.add("employeeEmploymentType", pReadOnly.getEmploymentType());
    pBuilder.add("employeeDepartment", pReadOnly.getDeptOffice());
    pBuilder.add("employeeJoiningDate", pReadOnly.getJoiningDate());
    pBuilder.add("employeeContractualDate", pReadOnly.getJobContractualDate());
    pBuilder.add("employeeProbationDate", pReadOnly.getJobProbationDate());
    pBuilder.add("employeeJobPermanentDate", pReadOnly.getJobPermanentDate());
    pBuilder.add("employeeExtensionNumber", pReadOnly.getExtNo());
    pBuilder.add("employeeShortName", pReadOnly.getShortName());
    pBuilder.add("employeeRoomNumber", pReadOnly.getRoomNo());
    pBuilder.add("employeeResignDate", pReadOnly.getJobTerminationDate());
  }

  @Override
  public void build(MutableServiceInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getInt("employeeId"));
    pMutable.setDesignation(pJsonObject.getInt("employeeDesignation"));
    pMutable.setEmploymentType(pJsonObject.getInt("employeeEmploymentType"));
    pMutable.setDeptOffice(pJsonObject.getInt("employeeDepartment"));
    pMutable.setJoiningDate(pJsonObject.getString("employeeJoiningDate"));
    pMutable.setJobContractualDate(pJsonObject.getString("employeeContractualDate"));
    pMutable.setJobProbationDate(pJsonObject.getString("employeeProbationDate"));
    pMutable.setJobPermanentDate(pJsonObject.getString("employeeJobPermanentDate"));
    pMutable.setExtNo(pJsonObject.getInt("employeeExtensionNumber"));
    pMutable.setShortName(pJsonObject.getString("employeeShortName"));
    pMutable.setRoomNo(pJsonObject.getString("employeeRoomNumber"));
    pMutable.setJobTerminationDate(pJsonObject.getString("employeeResignDate"));
  }
}
