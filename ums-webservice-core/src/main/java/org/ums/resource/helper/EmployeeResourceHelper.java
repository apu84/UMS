package org.ums.resource.helper;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.employee.personal.MutablePersonalInformation;
import org.ums.employee.personal.PersistentPersonalInformation;
import org.ums.employee.personal.PersonalInformation;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.formatter.DateFormat;
import org.ums.resource.EmployeeResource;
import org.ums.builder.Builder;
import org.ums.builder.EmployeeBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Employee;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.PersistentUser;
import org.ums.usermanagement.user.User;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.manager.EmployeeManager;
import org.ums.usermanagement.user.UserManager;
import org.ums.persistent.model.PersistentEmployee;
import org.ums.resource.ResourceHelper;
import org.ums.solr.repository.EmployeeRepository;
import org.ums.solr.repository.document.EmployeeDocument;

@Component
public class EmployeeResourceHelper extends ResourceHelper<Employee, MutableEmployee, String> {

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  private EmployeeBuilder mBuilder;

  @Autowired
  private UserManager mUserManager;

  @Autowired
  EmployeeRepository mEmployeeRepository;

  @Autowired
  PersonalInformationManager mPersonalInformationManager;

  @Autowired
  DateFormat mDateFormat;

  @Override
  @Transactional
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableEmployee mutableEmployee = new PersistentEmployee();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableEmployee, pJsonObject.getJsonObject("entries"), localCache);
    mutableEmployee.create();

    MutablePersonalInformation mutablePersonalInformation = new PersistentPersonalInformation();
    preparePersonalInformation(mutablePersonalInformation, pJsonObject.getJsonObject("entries"));
    mPersonalInformationManager.create(mutablePersonalInformation);

    if(pJsonObject.getJsonObject("entries").containsKey("IUMSAccount")
        && pJsonObject.getJsonObject("entries").getBoolean("IUMSAccount")) {
      MutableUser mutableUser = new PersistentUser();
      prepareUserInformation(mutableUser, pJsonObject.getJsonObject("entries"));
      mUserManager.create(mutableUser);
    }

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(EmployeeResource.class).path(EmployeeResource.class, "get")
            .build(mutableEmployee.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getByEmployeeId(final UriInfo pUriInfo) {
    // String employeeId = SecurityUtils.getSubject().getPrincipal().toString();
    Employee employee = getSignedEmployeeInfo();

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    children.add(toJson(employee, pUriInfo, localCache));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getActiveTeachersByDept(final UriInfo pUriInfo) {
    Employee employee = getSignedEmployeeInfo();
    List<Employee> employees = getContentManager().getActiveTeachersOfDept(employee.getDepartment().getId());

    return convertToJson(employees, pUriInfo);
  }

  public JsonObject getEmployees(final String pDepartmentId, final UriInfo pUriInfo) {
    List<Employee> employees = new ArrayList<>();
    employees = getContentManager().getEmployees(pDepartmentId);

    return convertToJson(employees, pUriInfo);
  }

  public JsonObject getCurrentMaxEmployeeId(final String pDepartmentId, final int pEmployeeType) {
    String currentMax = "";
    String newId = "";
    try {
      currentMax = mEmployeeManager.getLastEmployeeId(pDepartmentId, pEmployeeType);
      newId = "0" + String.valueOf(Integer.parseInt(currentMax) + 1);
    } catch(Exception e) {
      newId = pDepartmentId + pEmployeeType + "001";
    }

    JsonObjectBuilder object = Json.createObjectBuilder();
    object.add("entries", newId);
    return object.build();
  }

  public boolean validateShortName(final String pShortName) {
    return mEmployeeManager.validateShortName(pShortName);
  }

  public JsonObject getByDesignation(final String designationId, final Request pRequest, final UriInfo pUriInfo) {
    List<Employee> employees = getContentManager().getByDesignation(designationId);
    return convertToJson(employees, pUriInfo);
  }

  private Employee getSignedEmployeeInfo() {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    String employeeId = user.getEmployeeId();
    Employee employee = getContentManager().get(employeeId);
    return employee;
  }

  private JsonObject convertToJson(List<Employee> employees, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(Employee employee : employees) {
      children.add(toJson(employee, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject searchUserByName(String pQuery, int page, final UriInfo pUriInfo) {
    List<EmployeeDocument> userDocuments = mEmployeeRepository.findByCustomQuery(pQuery, new PageRequest(page, 10));
    List<Employee> users = new ArrayList<>();
    for(EmployeeDocument document : userDocuments) {
      users.add(mEmployeeManager.get(document.getId()));
    }
    return convertToJson(users, pUriInfo);
  }

  public JsonObject searchUserByDepartment(String pQuery, int page, final UriInfo pUriInfo) {
    List<EmployeeDocument> userDocuments = mEmployeeRepository.findByDepartment(pQuery, new PageRequest(page, 10));
    List<Employee> users = new ArrayList<>();
    for(EmployeeDocument document : userDocuments) {
      users.add(mEmployeeManager.get(document.getId()));
    }
    return convertToJson(users, pUriInfo);
  }

  public JsonObject getEmployees(final String pPublicationStatus, final Request pRequest, final UriInfo pUriInfo) {
    Department department = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getDepartment();
    String departmentId = department.getId().toString();
    List<Employee> employees = mEmployeeManager.getEmployees(departmentId, pPublicationStatus);
    return JsonCreator(employees, pUriInfo);
  }

  private JsonObject JsonCreator(List<Employee> pEmployees, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(Employee employee : pEmployees) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      mBuilder.customBuilderForEmployee(jsonObject, employee, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  private void preparePersonalInformation(MutablePersonalInformation pMutablePersonalInformation, JsonObject pJsonObject) {
    pMutablePersonalInformation.setId(pJsonObject.getString("id"));
    pMutablePersonalInformation.setFirstName(pJsonObject.getString("firstName"));
    pMutablePersonalInformation.setLastName(pJsonObject.getString("lastName"));
    pMutablePersonalInformation.setFatherName(" ");
    pMutablePersonalInformation.setMotherName(" ");
    pMutablePersonalInformation.setGender(" ");
    pMutablePersonalInformation.setDateOfBirth(mDateFormat.parse("1/1/1960"));
    pMutablePersonalInformation.setNationalityId(0);
    pMutablePersonalInformation.setReligionId(0);
    pMutablePersonalInformation.setMaritalStatusId(0);
    pMutablePersonalInformation.setSpouseName(" ");
    pMutablePersonalInformation.setNidNo(" ");
    pMutablePersonalInformation.setBloodGroupId(0);
    pMutablePersonalInformation.setSpouseNidNo(" ");
    pMutablePersonalInformation.setWebsite(" ");
    pMutablePersonalInformation.setOrganizationalEmail(" ");
    pMutablePersonalInformation.setPersonalEmail(pJsonObject.getString("email"));
    pMutablePersonalInformation.setMobileNumber(" ");
    pMutablePersonalInformation.setPhoneNumber(" ");
    pMutablePersonalInformation.setPresentAddressLine1(" ");
    pMutablePersonalInformation.setPresentAddressLine2(" ");
    pMutablePersonalInformation.setPresentAddressCountryId(18);
    pMutablePersonalInformation.setPresentAddressDivision(null);
    pMutablePersonalInformation.setPresentAddressDistrict(null);
    pMutablePersonalInformation.setPresentAddressThana(null);
    pMutablePersonalInformation.setPresentAddressPostCode(" ");

    pMutablePersonalInformation.setPermanentAddressLine1(" ");
    pMutablePersonalInformation.setPermanentAddressLine2(" ");
    pMutablePersonalInformation.setPermanentAddressCountryId(18);
    pMutablePersonalInformation.setPermanentAddressDivision(null);
    pMutablePersonalInformation.setPermanentAddressDistrict(null);
    pMutablePersonalInformation.setPermanentAddressThana(null);
    pMutablePersonalInformation.setPermanentAddressPostCode(" ");

    pMutablePersonalInformation.setEmergencyContactName(" ");
    pMutablePersonalInformation.setEmergencyContactRelationId(0);
    pMutablePersonalInformation.setEmergencyContactPhone(" ");
    pMutablePersonalInformation.setEmergencyContactAddress(" ");
  }

  private void prepareUserInformation(MutableUser pMutableUser, JsonObject pJsonObject) {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);

    pMutableUser.setId(pJsonObject.getString("shortName"));
    pMutableUser.setEmployeeId(pJsonObject.getString("id"));
    pMutableUser.setPrimaryRoleId(pJsonObject.getJsonObject("role").getInt("id"));
    // pMutableUser.setPassword('A');
    pMutableUser.setActive(true);
    pMutableUser.setPassword(null);
    pMutableUser.setCreatedOn(new Date());
    pMutableUser.setCreatedBy(user.getId());
  }

  @Override
  public EmployeeManager getContentManager() {
    return mEmployeeManager;
  }

  @Override
  public Builder<Employee, MutableEmployee> getBuilder() {
    return mBuilder;
  }

  @Override
  public String getETag(Employee pReadonly) {
    return pReadonly.getLastModified();
  }
}
