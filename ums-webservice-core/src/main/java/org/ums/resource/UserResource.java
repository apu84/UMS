package org.ums.resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.UserResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("users")
@Produces(Resource.MIME_TYPE_JSON)
public class UserResource extends Resource {
  @Autowired
  UserResourceHelper mUserResourceHelper;

  @GET
  public JsonObject getUsers(final @Context Request pRequest) {
    return mUserResourceHelper.getUsers(mUriInfo);
  }

  @GET
  @Path("/current")
  public Response get(final @Context Request pRequest) throws Exception {
    Subject subject = SecurityUtils.getSubject();
    return mUserResourceHelper.get(subject.getPrincipal().toString(), pRequest, mUriInfo);
  }

  @GET
  @Path("/id/{user-id}")
  public JsonObject getUser(final @PathParam("user-id") String pUserId, final @Context Request pRequest) {
    return mUserResourceHelper.getUser(pUserId, mUriInfo);
  }
}
