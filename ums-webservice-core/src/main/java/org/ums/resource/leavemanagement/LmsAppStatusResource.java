package org.ums.resource.leavemanagement;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 20-May-17.
 */
@Component
@Path("/lmsAppStatus")
public class LmsAppStatusResource extends MutableLmsAppStatusResource {

  @GET
  @Path("/appId/{app-id}")
  public JsonObject getApplicationStatus(final @Context Request pRequest, @PathParam("app-id") String appId) {
    return mHelper.getApplicationStatus(Long.parseLong(appId), mUriInfo);
  }
}
