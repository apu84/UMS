package org.ums.ems.createnew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class EmployeeCreateRequestResourceHelper extends
    ResourceHelper<EmployeeCreateRequest, MutableEmployeeCreateRequest, String> {

  @Autowired
  EmployeeCreateRequestManager mManager;

  @Autowired
  EmployeeCreateRequestBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    throw new NotImplementedException();
  }

  public Response update(JsonObject pJsonObject, UriInfo pUriInfo) {
    throw new NotImplementedException();
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
