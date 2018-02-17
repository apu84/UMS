package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.RoleBuilder;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.role.MutableRole;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.role.RoleManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class RoleResourceHelper extends ResourceHelper<Role, MutableRole, Integer> {

  @Autowired
  RoleManager mManager;

  @Autowired
  RoleBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<Role, MutableRole, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Role, MutableRole> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Role pReadonly) {
    return pReadonly.getLastModified();
  }
}
