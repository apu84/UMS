package org.ums.resource;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 14-May-17.
 */
@Component
@Path("/lmsApplication")
public class LmsApplicationResource extends MutableLmsApplicationResource {
  @GET
  @Path("/remainingLeaves")
  public JsonObject getRemainingLeaves(final @Context Request pRequest) throws Exception {
    return mHelper.getRemainingLeaves();
  }

  @GET
  @Path("/pendingLeaves")
  public JsonObject getPendingLeavesOfEmployee(final @Context Request pRequest) throws Exception {
    return mHelper.getPendingLeavesOfEmployee(mUriInfo);
  }
}
