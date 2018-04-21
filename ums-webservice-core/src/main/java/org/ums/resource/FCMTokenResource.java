package org.ums.resource;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("fcmToken")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class FCMTokenResource extends MutableFCMTokenResource {

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/userId/{user-id}")
  public JsonObject getToken(final @Context Request pRequest, final @PathParam("user-id") String pUserId)
      throws Exception {
    return mResourceHelper.getToken(pUserId, mUriInfo);
  }

}
