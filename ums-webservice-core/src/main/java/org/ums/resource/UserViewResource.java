package org.ums.resource;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Component
@Path("user/view")
@Produces(Resource.MIME_TYPE_JSON)
public class UserViewResource extends MutableUserViewResource {

  @GET
  @Path("/id/{user-id}")
  public JsonObject getUserView(final @PathParam("user-id") String pUserId, final @Context Request pRequest) {
    return mHelper.getUser(pUserId, mUriInfo);
  }
}
