package org.ums.ems.createnew;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.UserManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Date;
import java.util.List;

@Component
public class EmployeeCreateRequestResourceHelper extends
    ResourceHelper<EmployeeCreateRequest, MutableEmployeeCreateRequest, String> {

  @Autowired
  EmployeeCreateRequestManager mManager;

  @Autowired
  EmployeeCreateRequestBuilder mBuilder;

  @Autowired
  UserManager mUserManger;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableEmployeeCreateRequest mutableEmployeeCreateRequest = new PersistentEmployeeCreateRequest();
    mBuilder.build(mutableEmployeeCreateRequest, pJsonObject.getJsonObject("entries"), localCache);
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    mutableEmployeeCreateRequest.setRequestedBy(userId);
    mutableEmployeeCreateRequest.setRequestedOn(new Date());
    mutableEmployeeCreateRequest.setActionTakenBy("");
    mutableEmployeeCreateRequest.setActionTakenOn(null);
    mutableEmployeeCreateRequest.setActionStatus(0);
    mManager.create(mutableEmployeeCreateRequest);
    localCache.invalidate();
    return Response.ok().build();
  }

  public JsonObject get(final Integer pActionStatus, final UriInfo pUriInfo) {
    List<EmployeeCreateRequest> employeeCreateRequests = mManager.getAll(pActionStatus);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(EmployeeCreateRequest employeeCreateRequest : employeeCreateRequests) {
      children.add(toJson(employeeCreateRequest, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public Response update(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableEmployeeCreateRequest mutableEmployeeCreateRequest = new PersistentEmployeeCreateRequest();
    mBuilder.build(mutableEmployeeCreateRequest, pJsonObject.getJsonObject("entries"), localCache);
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    mutableEmployeeCreateRequest.setActionTakenBy(userId);
    mutableEmployeeCreateRequest.setActionTakenOn(new Date());
    mManager.update(mutableEmployeeCreateRequest);
    localCache.invalidate();
    return Response.ok().build();
  }

  public Response delete(String pObjectId, UriInfo pUriInfo) {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<EmployeeCreateRequest, MutableEmployeeCreateRequest, String> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<EmployeeCreateRequest, MutableEmployeeCreateRequest> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(EmployeeCreateRequest pReadonly) {
    return pReadonly.getLastModified();
  }
}
