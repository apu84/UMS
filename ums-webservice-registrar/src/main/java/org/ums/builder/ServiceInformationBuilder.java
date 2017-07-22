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
import org.ums.manager.registrar.ServiceInformationDetailManager;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.ldap.PagedResultsControl;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceInformationBuilder implements Builder<ServiceInformation, MutableServiceInformation> {

  @Autowired
  private DateFormat mDateFormat;

  @Autowired
  private DepartmentManager mDepartmentManager;

  @Autowired
  DesignationManager mDesignationManager;

  @Autowired
  private EmploymentTypeManager mEmploymentTypeManager;

  @Autowired
  ServiceInformationDetailManager mServiceInformationDetailManager;

  @Autowired
  ServiceInformationDetailBuilder mServiceInformationDetailBuilder;

  private EmploymentPeriod mEmploymentPeriod;

  @Override
  public void build(JsonObjectBuilder pBuilder, ServiceInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("departmentId", pReadOnly.getDepartmentId());
    pBuilder.add("designationId", pReadOnly.getDesignationId());
    pBuilder.add("employmentId", pReadOnly.getEmploymentId());
    pBuilder.add("joiningDate", mDateFormat.format(pReadOnly.getJoiningDate()));
    pBuilder.add("resignDate", pReadOnly.getResignDate() == null ? "" : mDateFormat.format(pReadOnly.getResignDate()));
    List<ServiceInformationDetail> serviceInformationDetails = new ArrayList<>();
    JsonArrayBuilder children = Json.createArrayBuilder();
    serviceInformationDetails = mServiceInformationDetailManager.getServiceInformationDetail(pReadOnly.getId());
    for(ServiceInformationDetail serviceInformationDetail : serviceInformationDetails) {
      JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
      mServiceInformationDetailBuilder.build(jsonObjectBuilder, serviceInformationDetail, pUriInfo, pLocalCache);
      children.add(jsonObjectBuilder);
    }
    pBuilder.add("intervalDetails", children);
  }

  @Override
  public void build(MutableServiceInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("dbAction")) {
      if(pJsonObject.getString("dbAction").equals("Update")) {
        pMutable.setId(Long.valueOf(pJsonObject.getString("id")));
      }
      else if(pJsonObject.getString("dbAction").equals("Create")) {
      }
    }
    else if(pJsonObject.getString("dbAction").equals("Delete")) {
      pMutable.setId(Long.valueOf(pJsonObject.getString("id")));
    }
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setDepartment(mDepartmentManager.get(pJsonObject.getJsonObject("department").getString("id")));
    pMutable.setDesignation(mDesignationManager.get(pJsonObject.getJsonObject("designation").getInt("id")));
    pMutable.setEmployment(mEmploymentTypeManager.get(pJsonObject.getJsonObject("employmentType").getInt("id")));
    pMutable.setJoiningDate(mDateFormat.parse(pJsonObject.getString("joiningDate")));
    pMutable
        .setResignDate(pJsonObject.containsKey("resignDate") ? pJsonObject.getString("resignDate").equals("") ? null
            : mDateFormat.parse(pJsonObject.getString("resignDate")) : null);
  }
}
