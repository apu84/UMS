package org.ums.academic.resource.helper;

import java.net.URI;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.EmployeeResource;
import org.ums.builder.Builder;
import org.ums.builder.EmployeeBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.manager.EmployeeManager;
import org.ums.manager.UserManager;
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

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableEmployee mutableEmployee = new PersistentEmployee();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableEmployee, pJsonObject, localCache);
    mutableEmployee.create();
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
