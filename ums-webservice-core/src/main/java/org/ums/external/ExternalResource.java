package org.ums.external;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Employee;
import org.ums.ems.profilemanagement.academic.AcademicInformation;
import org.ums.ems.profilemanagement.academic.AcademicInformationManager;
import org.ums.ems.profilemanagement.personal.PersonalInformationManager;
import org.ums.ems.profilemanagement.publication.PublicationInformation;
import org.ums.ems.profilemanagement.publication.PublicationInformationManager;
import org.ums.enums.common.DegreeLevel;
import org.ums.enums.common.PublicationType;
import org.ums.enums.common.RoleType;
import org.ums.logs.GetLog;
import org.ums.manager.EmployeeManager;
import org.ums.manager.common.CountryManager;
import org.ums.manager.common.DegreeTitleManager;
import org.ums.resource.Resource;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("external/employee")
public class ExternalResource extends Resource {

  @Autowired
  EmployeeManager mEmployeeManager;

  @Autowired
  PersonalInformationManager mPersonalInformationManager;

  @Autowired
  UserManager mUserManager;

  @Autowired
  PublicationInformationManager mPublicationInformationManager;

  @Autowired
  AcademicInformationManager mAcademicInformationManager;

  @Autowired
  CountryManager mCountryManager;

  @Autowired
  DegreeTitleManager mDegreeTitleManager;

  @GET
  @GetLog(message = "Get external information of all teacher")
  public JsonObject get(@Context HttpServletRequest pHttpServletRequest, final @Context Request pRequest) {
    return getTeachersInformation(mUriInfo);
  }

  private JsonObject getTeachersInformation(final UriInfo pUriInfo) {
    List<User> users = getUsers(RoleType.TEACHER);
    List<Employee> employees = getEmployees(users);
    Map<String, List<AcademicInformation>> academicListMap = getAcademicListMap(users);
    Map<String, List<PublicationInformation>> publicationListMap = getPublicationListMap(users);
    return getJson(users, employees, publicationListMap, academicListMap);
  }

  @NotNull
  private Map<String, List<AcademicInformation>> getAcademicListMap(List<User> users) {
    Map<String, List<AcademicInformation>> academicListMap = new HashMap<>();
    for(User user : users) {
      List<AcademicInformation> academicInformations = mAcademicInformationManager.get(user.getId());
      academicListMap.put(user.getId(), academicInformations);
    }
    return academicListMap;
  }

  @NotNull
  private Map<String, List<PublicationInformation>> getPublicationListMap(List<User> users) {
    Map<String, List<PublicationInformation>> publicationListMap = new HashMap<>();
    for(User user : users) {
      List<PublicationInformation> publicationInformations = mPublicationInformationManager.get(user.getId());
      publicationListMap.put(user.getId(), publicationInformations);
    }
    return publicationListMap;
  }

  @NotNull
  private List<Employee> getEmployees(List<User> users) {
    List<Employee> employees = new ArrayList<>();
    for(User user : users) {
      Employee employee = mEmployeeManager.get(user.getId());
      employees.add(employee);
    }
    return employees;
  }

  private List<User> getUsers(RoleType roleType) {
    return mUserManager.getUsers(roleType);
  }

  private JsonObject getJson(List<User> users, List<Employee> employeeList,
      Map<String, List<PublicationInformation>> publicationListMap,
      Map<String, List<AcademicInformation>> academicListMap) {

    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

    for(User user : users) {
      getJson(jsonObjectBuilder, mEmployeeManager.get(user.getId()));
      getJson(academicListMap, jsonObjectBuilder, user);
      getJson(publicationListMap, jsonObjectBuilder, jsonArrayBuilder, user);
    }

    jsonObjectBuilder.add("entries", jsonArrayBuilder);
    return jsonObjectBuilder.build();
  }

  private void getJson(Map<String, List<PublicationInformation>> publicationListMap,
      JsonObjectBuilder jsonObjectBuilder, JsonArrayBuilder jsonArrayBuilder, User user) {
    JsonArrayBuilder publicationJsonArrayBuilder = Json.createArrayBuilder();
    for(PublicationInformation publicationInformation : publicationListMap.get(user.getId())) {
      JsonObjectBuilder publicationJsonBuilder = Json.createObjectBuilder();
      publicationJsonBuilder.add("title", publicationInformation.getTitle());
      publicationJsonBuilder.add("id", publicationInformation.getId().toString());
      publicationJsonBuilder.add("employeeId", publicationInformation.getEmployeeId());
      publicationJsonBuilder.add("publicationTitle", publicationInformation.getTitle());
      publicationJsonBuilder.add("publicationInterestGenre", publicationInformation.getInterestGenre() == null ? ""
          : publicationInformation.getInterestGenre());
      publicationJsonBuilder.add("publisherName", publicationInformation.getPublisherName() == null ? ""
          : publicationInformation.getPublisherName());
      publicationJsonBuilder.add("dateOfPublication", publicationInformation.getDateOfPublication());
      publicationJsonBuilder.add("publicationType",
          publicationInformation.getTypeId() == 0 ? "" : PublicationType.get(publicationInformation.getTypeId())
              .getLabel());
      publicationJsonBuilder.add("publicationWebLink", publicationInformation.getWebLink() == null ? ""
          : publicationInformation.getWebLink());
      publicationJsonBuilder.add("publicationISSN", publicationInformation.getISSN() == null ? ""
          : publicationInformation.getISSN());
      publicationJsonBuilder.add("publicationIssue", publicationInformation.getIssue() == null ? ""
          : publicationInformation.getIssue());
      publicationJsonBuilder.add("publicationVolume", publicationInformation.getVolume() == null ? ""
          : publicationInformation.getVolume());
      publicationJsonBuilder.add("publicationJournalName", publicationInformation.getJournalName() == null ? ""
          : publicationInformation.getJournalName());
      if(publicationInformation.getCountryId() == 0) {
        publicationJsonBuilder.add("location", JsonValue.NULL);
      }
      else {
        publicationJsonBuilder.add("location", mCountryManager.get(publicationInformation.getCountryId()).getName());
      }
      publicationJsonArrayBuilder.add(publicationJsonBuilder);
    }
    jsonObjectBuilder.add("publication", publicationJsonArrayBuilder);
    jsonArrayBuilder.add(jsonObjectBuilder);
  }

  private void getJson(Map<String, List<AcademicInformation>> academicListMap, JsonObjectBuilder jsonObjectBuilder,
      User user) {
    JsonArrayBuilder academicJsonArrayBuilder = Json.createArrayBuilder();
    for(AcademicInformation academicInformation : academicListMap.get(user.getId())) {
      JsonObjectBuilder academicJsonBuilder = Json.createObjectBuilder();
      academicJsonBuilder.add("degree", DegreeLevel.get(academicInformation.getDegreeLevelId()).getLabel());
      academicJsonBuilder.add("degreeTitle", academicInformation.getDegreeTitleId() == 0 ? "" : mDegreeTitleManager
          .get(academicInformation.getDegreeTitleId()).getTitle());
      academicJsonBuilder.add("institution", academicInformation.getInstitute());
      academicJsonBuilder.add("passingYear", academicInformation.getPassingYear());
      academicJsonArrayBuilder.add(academicJsonBuilder);
    }
    jsonObjectBuilder.add("education", academicJsonArrayBuilder);
  }

  private void getJson(JsonObjectBuilder jsonObjectBuilder, Employee employee) {
    JsonObjectBuilder employeeJsonBuilder = Json.createObjectBuilder();
    employeeJsonBuilder.add("id", employee.getId());
    employeeJsonBuilder.add("name", employee.getPersonalInformation().getFullName());
    employeeJsonBuilder.add("designation", employee.getDesignation().getDesignationName());
    employeeJsonBuilder.add("department", employee.getDepartment().getShortName());
    jsonObjectBuilder.add("employee", employeeJsonBuilder);
  }
}
