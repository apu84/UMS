package org.ums.common.academic.resource.helper;


import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.EmployeeResource;
import org.ums.common.ResourceHelper;
import org.ums.common.builder.Builder;
import org.ums.common.builder.EmployeeBuilder;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.manager.EmployeeManager;
import org.ums.manager.UserManager;
import org.ums.persistent.model.PersistentEmployee;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Component
public class EmployeeResourceHelper extends ResourceHelper<Employee,MutableEmployee,String>{

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  private EmployeeBuilder mBuilder;

  @Autowired
  private UserManager mUserManager;



  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    MutableEmployee mutableEmployee = new PersistentEmployee();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableEmployee, pJsonObject, localCache);
    mutableEmployee.commit(false);
    URI contextURI = pUriInfo.getBaseUriBuilder().path(EmployeeResource.class).path(EmployeeResource.class, "get").build(mutableEmployee.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getByEmployeeId(final UriInfo pUriInfo)throws Exception{
    //String employeeId = SecurityUtils.getSubject().getPrincipal().toString();
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    String employeeId=user.getEmployeeId();
    Employee employee = getContentManager().getByEmployeeId(employeeId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    children.add(toJson(employee,pUriInfo,localCache));
    object.add("entries",children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getByDesignation(final String designationId,final Request pRequest,final UriInfo pUriInfo) throws Exception{
    List<Employee> employees = getContentManager().getByDesignation(designationId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(Employee employee: employees){
      children.add(toJson(employee,pUriInfo,localCache));
    }
    object.add("entries",children);
    localCache.invalidate();
    return object.build();
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
  public String getEtag(Employee pReadonly) {
    return pReadonly.getLastModified();
  }
}
