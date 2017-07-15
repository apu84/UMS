package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.*;
import org.ums.domain.model.mutable.registrar.*;
import org.ums.enums.common.EmploymentPeriod;
import org.ums.formatter.DateFormat;
import org.ums.manager.DepartmentManager;
import org.ums.manager.DesignationManager;
import org.ums.manager.EmploymentTypeManager;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.ldap.PagedResultsControl;
import javax.ws.rs.core.UriInfo;

@Component
public class ServiceInformationBuilder implements Builder<ServiceInformation, MutableServiceInformation> {

  @Autowired
  private UserManager mUserManager;

  @Autowired
  private DateFormat mDateFormat;

  @Autowired
  private DepartmentManager mDepartmentManager;

  @Autowired
  DesignationManager mDesignationManager;

  @Autowired
  private EmploymentTypeManager mEmploymentTypeManager;

  private EmploymentPeriod mEmploymentPeriod;

  @Override
  public void build(JsonObjectBuilder pBuilder, ServiceInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("departmentId", pReadOnly.getDepartmentId());
    pBuilder.add("designationId", pReadOnly.getDesignationId());
    pBuilder.add("employmentId", pReadOnly.getEmploymentId());
    pBuilder.add("joiningDate", mDateFormat.format(pReadOnly.getJoiningDate()));
    pBuilder.add("resignDate", pReadOnly.getResignDate() == null ? "" : mDateFormat.format(pReadOnly.getResignDate()));
    pBuilder.add("roomNo", pReadOnly.getRoomNo() == null ? "" : pReadOnly.getRoomNo());
    pBuilder.add("extNo", pReadOnly.getExtNo() == null ? "" : pReadOnly.getExtNo());
    pBuilder.add("academicInitial", pReadOnly.getAcademicInitial() == null ? "" : pReadOnly.getAcademicInitial());
    pBuilder.add("status", pReadOnly.getCurrentStatus());
  }

  @Override
  public void build(MutableServiceInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    pMutable.setDepartment(mDepartmentManager.get(pJsonObject.getJsonObject("department").getString("id")));
    pMutable.setDesignation(mDesignationManager.get(pJsonObject.getJsonObject("designation").getInt("id")));
    pMutable.setEmployment(mEmploymentTypeManager.get(pJsonObject.getJsonObject("employmentType").getInt("id")));
    pMutable.setJoiningDate(mDateFormat.parse(pJsonObject.getString("joiningDate")));
    pMutable
        .setResignDate(pJsonObject.containsKey("resignDate") ? pJsonObject.getString("resignDate").equals("") ? null
            : mDateFormat.parse(pJsonObject.getString("resignDate")) : null);
    pMutable.setRoomNo(pJsonObject.containsKey("roomNo") ? pJsonObject.getString("roomNo") : "");
    pMutable.setExtNo(pJsonObject.containsKey("extNo") ? pJsonObject.getString("extNo") : "");
    pMutable.setAcademicInitial(pJsonObject.containsKey("academicInitial") ? pJsonObject.getString("academicInitial")
        : "");
    pMutable.setCurrentStatus(0);
  }
}
