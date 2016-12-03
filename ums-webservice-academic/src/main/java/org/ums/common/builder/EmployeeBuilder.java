package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.persistent.model.PersistentDepartment;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class EmployeeBuilder implements Builder<Employee, MutableEmployee> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Employee pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("employeeName", pReadOnly.getEmployeeName());
    pBuilder.add("designation", pReadOnly.getDesignation());
    pBuilder.add("employmentType", pReadOnly.getEmploymentType());
    pBuilder.add("deptOfficeId", pReadOnly.getDepartment().getId());
    pBuilder.add("fatherName", pReadOnly.getFatherName());
    pBuilder.add("motherName", pReadOnly.getMotherName());
    pBuilder.add("birthDate", pReadOnly.getBirthDate());
    pBuilder.add("gender", pReadOnly.getGender());
    pBuilder.add("bloodGroup", pReadOnly.getBloodGroup());
    pBuilder.add("presentAddress", pReadOnly.getPresentAddress());
    pBuilder.add("permanentAddress", pReadOnly.getPermanentAddress());
    pBuilder.add("mobileNumber", pReadOnly.getMobileNumber());
    pBuilder.add("phoneNumber", pReadOnly.getPhoneNumber());
    pBuilder.add("emailAddress",
        pReadOnly.getEmailAddress() == null ? "-" : pReadOnly.getEmailAddress());
    pBuilder.add("joiningDate", pReadOnly.getJoiningDate());
    pBuilder.add("jobPermanentDate",
        pReadOnly.getJobPermanentDate() == null ? "-" : pReadOnly.getJobPermanentDate());
    pBuilder.add("status", pReadOnly.getStatus());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("semesterWithdraw")
        .path(pReadOnly.getId().toString()).build().toString());
  }

  @Override
  public void build(MutableEmployee pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.getString("id"));
    pMutable.setEmployeeName(pJsonObject.getString("employeeName"));
    pMutable.setDesignation(Integer.parseInt(pJsonObject.getString("designation")));
    pMutable.setEmploymentType(pJsonObject.getString("employmentType"));
    PersistentDepartment dept = new PersistentDepartment();
    dept.setId(pJsonObject.getString("deptOfficeId"));
    pMutable.setDepartment(dept);
    pMutable.setFatherName(pJsonObject.getString("fatherName"));
    pMutable.setMotherName(pJsonObject.getString("motherName"));
    pMutable.setBirthDate(pJsonObject.getString("birthDate"));
    pMutable.setGender(pJsonObject.getString("gender").toCharArray()[0]);
    pMutable.setBloodGroup(pJsonObject.getString("bloodGroup"));
    pMutable.setPresentAddress(pJsonObject.getString("presentAddress"));
    pMutable.setPermanentAddress(pJsonObject.getString("permanentAddress"));
    pMutable.setMobileNumber(pJsonObject.getString("mobileNumber"));
    pMutable.setPhoneNumber(pJsonObject.getString("phoneNumber"));
    pMutable.setEmailAddress(pJsonObject.getString("emailAddress"));
    pMutable.setJoiningDate(pJsonObject.getString("joiningDate"));
    pMutable.setJobParmanentDate(pJsonObject.getString("jobPermanentDate"));
    pMutable.setStatus(Integer.parseInt(pJsonObject.getString("status")));
  }
}
