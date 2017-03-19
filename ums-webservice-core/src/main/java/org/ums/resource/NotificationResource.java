package org.ums.resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.NotificationResourceHelper;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/notification")
@Produces(Resource.MIME_TYPE_JSON)
public class NotificationResource extends Resource {
  @Autowired
  NotificationResourceHelper mNotificationResourceHelper;

  @GET
  @Path("/{num-of-latest-notification}")
  public JsonObject getAdditionalRolePermissions(final @Context Request pRequest,
      final @PathParam("num-of-latest-notification") Integer pNum) {
    return mNotificationResourceHelper.getNotifications(SecurityUtils.getSubject().getPrincipal().toString(), pNum,
        mUriInfo);
  }

  @POST
  @Path("/read")
  public Response addLogger(final JsonArray pJsonArray) {
    mNotificationResourceHelper.updateReadStatus(pJsonArray);
    return Response.noContent().build();
  }
}
