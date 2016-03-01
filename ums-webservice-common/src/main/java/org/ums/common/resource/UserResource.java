package org.ums.common.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.resource.helper.UserResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("/users")
@Produces(Resource.MIME_TYPE_JSON)
public class UserResource extends Resource {
  @Autowired
  UserResourceHelper mUserResourceHelper;

  @GET
  public JsonObject getUsers(final @Context Request pRequest)
      throws Exception {
    return mUserResourceHelper.getUsers(mUriInfo);
  }
}
