package org.ums.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.enums.common.EmploymentPeriod;
import org.ums.formatter.DateFormat;
import org.ums.manager.DepartmentManager;
import org.ums.manager.DesignationManager;
import org.ums.manager.EmploymentTypeManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceInformationBuilder implements Builder<ServiceInformation, MutableServiceInformation> {

  @Autowired
  @Qualifier("genericDateFormat")
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
    JsonObjectBuilder departmentBuilder = Json.createObjectBuilder();
    departmentBuilder.add("id", pReadOnly.getDepartmentId()).add("name",
        mDepartmentManager.get(pReadOnly.getDepartmentId()).getLongName());
    pBuilder.add("department", departmentBuilder);
    JsonObjectBuilder designationBuilder = Json.createObjectBuilder();
    designationBuilder.add("id", pReadOnly.getDesignationId()).add("name",
        mDesignationManager.get(pReadOnly.getDesignationId()).getDesignationName());
    pBuilder.add("designation", designationBuilder);
    JsonObjectBuilder employmentBuilder = Json.createObjectBuilder();
    employmentBuilder.add("id", pReadOnly.getEmploymentId()).add("name",
        mEmploymentTypeManager.get(pReadOnly.getEmploymentId()).getType());
    pBuilder.add("employmentType", employmentBuilder);
    pBuilder.add("joiningDate", mDateFormat.format(pReadOnly.getJoiningDate()));
    pBuilder.add("resignDate", pReadOnly.getResignDate() == null ? "" : mDateFormat.format(pReadOnly.getResignDate()));
    List<ServiceInformationDetail> serviceInformationDetails = new ArrayList<>();
    JsonArrayBuilder children = Json.createArrayBuilder();
    serviceInformationDetails = mServiceInformationDetailManager.getServiceDetail(pReadOnly.getId());
    for(ServiceInformationDetail serviceInformationDetail : serviceInformationDetails) {
      JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
      mServiceInformationDetailBuilder.build(jsonObjectBuilder, serviceInformationDetail, pUriInfo, pLocalCache);
      children.add(jsonObjectBuilder);
    }
    pBuilder.add("intervalDetails", children);
  }

  @Override
  public void build(MutableServiceInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(!pJsonObject.getString("id").equals("") ? Long.parseLong(pJsonObject.getString("id")) : null);
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
