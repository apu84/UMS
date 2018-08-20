package org.ums.resource.helper;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.EmployeeBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.ems.profilemanagement.additional.AdditionalInformationManager;
import org.ums.ems.profilemanagement.additional.MutableAdditionalInformation;
import org.ums.ems.profilemanagement.additional.PersistentAdditionalInformation;
import org.ums.ems.profilemanagement.personal.MutablePersonalInformation;
import org.ums.ems.profilemanagement.personal.PersistentPersonalInformation;
import org.ums.ems.profilemanagement.personal.PersonalInformationManager;
import org.ums.ems.profilemanagement.service.*;
import org.ums.enums.common.EmploymentPeriod;
import org.ums.enums.common.EmploymentType;
import org.ums.formatter.DateFormat;
import org.ums.manager.DepartmentManager;
import org.ums.manager.DesignationManager;
import org.ums.manager.EmployeeManager;
import org.ums.manager.EmploymentTypeManager;
import org.ums.persistent.model.PersistentEmployee;
import org.ums.resource.EmployeeResource;
import org.ums.resource.ResourceHelper;
import org.ums.services.email.NewIUMSAccountInfoEmailService;
import org.ums.solr.repository.EmployeeRepository;
import org.ums.solr.repository.document.EmployeeDocument;
import org.ums.usermanagement.role.RoleManager;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.PersistentUser;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  @Qualifier("genericDateFormat")
  DateFormat mDateFormat;

  @Autowired
  ServiceInformationManager mServiceInformationManager;

  @Autowired
  ServiceInformationDetailManager mServiceInformationDetailManager;

  @Autowired
  DepartmentManager mDepartmentManager;

  @Autowired
  DesignationManager mDesignationManager;

  @Autowired
  EmploymentTypeManager mEmploymentTypeManager;

  @Autowired
  RoleManager mRoleManager;

  @Autowired
  AdditionalInformationManager mAdditionalInformationManager;

  @Autowired
  private NewIUMSAccountInfoEmailService mNewIUMSAccountInfoEmailService;

  @Override
  @Transactional
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {

    String tempPassword = "";

    MutableEmployee mutableEmployee = new PersistentEmployee();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableEmployee, pJsonObject.getJsonObject("entries"), localCache);
    mutableEmployee.create();

    MutablePersonalInformation mutablePersonalInformation = new PersistentPersonalInformation();
    preparePersonalInformation(mutablePersonalInformation, pJsonObject.getJsonObject("entries"));
    mPersonalInformationManager.create(mutablePersonalInformation);

    MutableServiceInformation mutableServiceInformation = new PersistentServiceInformation();
    MutableServiceInformationDetail mutableServiceInformationDetail = new PersistentServiceInformationDetail();
    prepareServiceInformation(mutableServiceInformation, mutableServiceInformationDetail,
        pJsonObject.getJsonObject("entries"));
    Long serviceId = mServiceInformationManager.create(mutableServiceInformation);
    mutableServiceInformationDetail.setServiceId(serviceId);
    mServiceInformationDetailManager.create(mutableServiceInformationDetail);

    if(pJsonObject.getJsonObject("entries").containsKey("academicInitial")
        && !pJsonObject.getJsonObject("entries").getString("academicInitial").isEmpty()) {
      MutableAdditionalInformation mutableAdditionalInformation = new PersistentAdditionalInformation();
      mutableAdditionalInformation
          .setAcademicInitial(pJsonObject.getJsonObject("entries").getString("academicInitial"));
      mutableAdditionalInformation.setExtNo("");
      mutableAdditionalInformation.setRoomNo("");
      mutableAdditionalInformation.setId(pJsonObject.getJsonObject("entries").getString("id"));

      mAdditionalInformationManager.create(mutableAdditionalInformation);
    }

    if(pJsonObject.getJsonObject("entries").containsKey("IUMSAccount")
        && pJsonObject.getJsonObject("entries").getBoolean("IUMSAccount")
        && pJsonObject.getJsonObject("entries").getJsonObject("designation").getInt("roleId") != 0) {

      tempPassword = RandomStringUtils.random(6, true, true);
      MutableUser mutableUser = new PersistentUser();
      prepareUserInformation(mutableUser, pJsonObject.getJsonObject("entries"));

      mutableUser.setPrimaryRole(mRoleManager.get(pJsonObject.getJsonObject("entries").getJsonObject("designation")
          .getInt("roleId")));
      mutableUser.setTemporaryPassword(tempPassword.toCharArray());
      mUserManager.create(mutableUser);

      mNewIUMSAccountInfoEmailService.sendEmail(mutablePersonalInformation.getName(), mutableUser.getId(),
          tempPassword, mutablePersonalInformation.getPersonalEmail(), "IUMS", "AUST: IUMS Account Credentials");
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
      newId =
          Integer.parseInt(pDepartmentId) < 10 ? "0" + String.valueOf(Integer.parseInt(currentMax) + 1) : String
              .valueOf(Integer.parseInt(currentMax) + 1);
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
    String email = pJsonObject.containsKey("email") ? pJsonObject.getString("email") : "-";
    if(email == null || email.equals("")) {
      email = "-";
    }
    pMutablePersonalInformation.setId(pJsonObject.getString("id"));
    pMutablePersonalInformation.setName(pJsonObject.getString("name"));
    pMutablePersonalInformation.setFatherName(" ");
    pMutablePersonalInformation.setMotherName(" ");
    pMutablePersonalInformation.setGender(" ");
    pMutablePersonalInformation.setDateOfBirth(mDateFormat.parse("1/1/1960"));
    pMutablePersonalInformation.setNationalityId(0);
    pMutablePersonalInformation.setReligionId(0);
    pMutablePersonalInformation.setMaritalStatusId(0);
    pMutablePersonalInformation.setSpouseName(null);
    pMutablePersonalInformation.setNidNo(null);
    pMutablePersonalInformation.setBloodGroupId(0);
    pMutablePersonalInformation.setSpouseNidNo(null);
    pMutablePersonalInformation.setWebsite(null);
    pMutablePersonalInformation.setOrganizationalEmail(null);
    pMutablePersonalInformation.setPersonalEmail(email);
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
    pMutableUser.setId(pJsonObject.getString("id"));
    pMutableUser.setEmployeeId(pJsonObject.getString("id"));
    pMutableUser.setPassword(null);
    pMutableUser.setActive(true);
    pMutableUser.setCreatedOn(new Date());
    pMutableUser.setCreatedBy(user.getId());
  }

  private void prepareServiceInformation(MutableServiceInformation pMutableServiceInformation,
      MutableServiceInformationDetail pMutableServiceInformationDetail, JsonObject pJsonObject) {
    pMutableServiceInformation.setEmployeeId(pJsonObject.getString("id"));
    pMutableServiceInformation.setDepartment(mDepartmentManager.get(pJsonObject.getJsonObject("department").getString(
        "id")));
    pMutableServiceInformation.setDesignation(mDesignationManager.get(pJsonObject.getJsonObject("designation").getInt(
        "id")));
    pMutableServiceInformation.setEmployment(mEmploymentTypeManager.get(pJsonObject.getJsonObject("employmentType")
        .getInt("id")));
    pMutableServiceInformation.setJoiningDate(mDateFormat.parse(pJsonObject.getString("joiningDate")));
    pMutableServiceInformation.setResignDate(null);

    pMutableServiceInformationDetail
        .setEmploymentPeriod(pJsonObject.getJsonObject("employmentType").getInt("id") == EmploymentType.REGULAR.getId() ? EmploymentPeriod.CONTRACTUAL
            : EmploymentPeriod.CONTRACT);
    pMutableServiceInformationDetail.setStartDate(mDateFormat.parse(pJsonObject.getString("joiningDate")));
    pMutableServiceInformationDetail.setEndDate(null);
    pMutableServiceInformationDetail.setComment(" ");
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
