package org.ums.resource.leavemanagement;

import org.springframework.stereotype.Component;
import org.ums.enums.common.LeaveApprovalStatus;

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

  @GET
  @Path("/pendingLeaves")
  public JsonObject getPendingLeaves(final @Context Request pRequest) {
    return mHelper.getPendingApplicationsOfEmployee(mUriInfo);
  }

  @GET
  @Path("/leaveApplications/status/{status}/pageNumber/{page-number}/pageSize/{page-size}")
  public JsonObject getLeaveApplications(final @Context Request pRequest, @PathParam("status") int pStatus,
      @PathParam("page-number") int pageNumber, @PathParam("page-size") int pageSize) {
    return mHelper.getLeaveApplications(LeaveApprovalStatus.get(pStatus), pageNumber, pageSize, mUriInfo);
  }
}
