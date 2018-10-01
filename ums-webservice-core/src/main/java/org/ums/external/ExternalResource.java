package org.ums.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Employee;
import org.ums.ems.profilemanagement.academic.AcademicInformation;
import org.ums.ems.profilemanagement.academic.AcademicInformationManager;
import org.ums.ems.profilemanagement.personal.PersonalInformationManager;
import org.ums.ems.profilemanagement.publication.PublicationInformation;
import org.ums.ems.profilemanagement.publication.PublicationInformationManager;
import org.ums.enums.common.DegreeLevel;
import org.ums.enums.common.RoleType;
import org.ums.logs.GetLog;
import org.ums.manager.EmployeeManager;
import org.ums.resource.Resource;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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

  @GET
  @GetLog(message = "Get dummy information")
  public JsonObject get(@Context HttpServletRequest pHttpServletRequest, final @Context Request pRequest) {
    return get(mUriInfo);
  }

  public JsonObject get(final UriInfo pUriInfo) {
    List<User> users = mUserManager.getUsers(RoleType.get(1021));
    List<Employee> employees = new ArrayList<>();
    Map<String, List<PublicationInformation>> publicationListMap = new HashMap<>();
    Map<String, List<AcademicInformation>> academicListMap = new HashMap<>();

    for(User user : users) {
      Employee employee = mEmployeeManager.get(user.getId());
      employees.add(employee);
      List<PublicationInformation> publicationInformations = new ArrayList<>();
      publicationInformations = mPublicationInformationManager.get(user.getId());
      publicationListMap.put(user.getId(), publicationInformations);
      List<AcademicInformation> academicInformations = new ArrayList<>();
      academicInformations = mAcademicInformationManager.get(user.getId());
      academicListMap.put(user.getId(), academicInformations);
    }
    return convertToJson(employees, publicationListMap, academicListMap);
  }

  private JsonObject convertToJson(List<Employee> employeeList,
      Map<String, List<PublicationInformation>> publicationListMap,
      Map<String, List<AcademicInformation>> academicListMap) {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
    for(Employee employee : employeeList) {
      JsonObjectBuilder employeeJsonBuilder = Json.createObjectBuilder();
      employeeJsonBuilder.add("id", employee.getId());
      employeeJsonBuilder.add("name", employee.getPersonalInformation().getFullName());
      employeeJsonBuilder.add("gender", employee.getPersonalInformation().getGender());
      jsonObjectBuilder.add("personal", employeeJsonBuilder);

      JsonArrayBuilder academicJsonArrayBuilder = Json.createArrayBuilder();
      for(AcademicInformation academicInformation : academicListMap.get(employee.getId())) {
        JsonObjectBuilder academicJsonBuilder = Json.createObjectBuilder();
        academicJsonBuilder.add("degree", DegreeLevel.get(academicInformation.getDegreeLevelId()).getLabel());
        academicJsonBuilder.add("institution", academicInformation.getInstitute());
        academicJsonArrayBuilder.add(academicJsonBuilder);
      }
      jsonObjectBuilder.add("education", academicJsonArrayBuilder);

      JsonArrayBuilder publicationJsonArrayBuilder = Json.createArrayBuilder();
      for(PublicationInformation publicationInformation : publicationListMap.get(employee.getId())) {
        JsonObjectBuilder publicationJsonBuilder = Json.createObjectBuilder();
        publicationJsonBuilder.add("title", publicationInformation.getTitle());
        publicationJsonArrayBuilder.add(publicationJsonBuilder);
      }
      jsonObjectBuilder.add("publication", publicationJsonArrayBuilder);

      jsonArrayBuilder.add(jsonObjectBuilder);
    }
    jsonObjectBuilder.add("entries", jsonArrayBuilder);
    return jsonObjectBuilder.build();
  }
}
