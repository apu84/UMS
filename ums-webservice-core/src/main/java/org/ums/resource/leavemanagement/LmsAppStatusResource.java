package org.ums.resource.leavemanagement;

import org.springframework.stereotype.Component;
import org.ums.enums.common.DepartmentType;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.enums.common.LeaveCategories;
import org.ums.logs.GetLog;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
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
  @Path("activeLeave/deptId/{dept-id}/type/{type}/pageNumber/{page-number}/pageSize/{page-size}")
  public JsonObject getLeaveApplicationsOfTheDay(final @PathParam("dept-id") String pDepartmentId,
      final @PathParam("type") int pCategory, final @PathParam("page-number") int pageNumber,
      final @PathParam("page-size") int totalSize, final @Context Request pRequest) {
    return mHelper.getActiveLeaveApplicationsOfTheDay(DepartmentType.get(pDepartmentId),
        LeaveCategories.get(pCategory), pageNumber, totalSize, mUriInfo);
  }

  @GET
  @Path("/pendingLeaves/employee/{employee-id}")
  @GetLog(message = "Requested for pending leaves")
  public JsonObject getPendingLeaves(final @Context HttpServletRequest pHttpServletRequest,
      @PathParam("employee-id") String pEmployeeId) {
    return mHelper.getPendingApplicationsOfEmployee(pEmployeeId, mUriInfo);
  }

  @GET
  @Path("/leaveApplications/employee/{employee-id}/status/{approval-status}/pageNumber/{page-number}/pageSize/{page-size}")
  public JsonObject getAllLeaves(@PathParam("employee-id") String pEmployeeId,
      @PathParam("page-number") int pageNumber, @PathParam("page-size") int pageSize,
      @PathParam("approval-status") int pApprovalStatus, final @Context Request pRequest) {
    return mHelper.getAllApplicationsOfEmployee(pEmployeeId, LeaveApplicationApprovalStatus.get(pApprovalStatus),
        pageNumber, pageSize, mUriInfo);
  }

  @GET
  @Path("/leaveApplications/status/{status}/pageNumber/{page-number}/pageSize/{page-size}")
  public JsonObject getLeaveApplications(final @Context Request pRequest, @PathParam("status") int pStatus,
      @PathParam("page-number") int pageNumber, @PathParam("page-size") int pageSize) {
    return mHelper.getLeaveApplications(LeaveApplicationApprovalStatus.get(pStatus), pageNumber, pageSize, mUriInfo);
  }

  @GET
  @Path("/leaveApplications/status/{status}")
  public JsonObject getLeaveApplications(final @Context Request pRequest, @PathParam("status") int pStatus) {
    return mHelper.getLeaveApplications(LeaveApplicationApprovalStatus.get(pStatus), mUriInfo);
  }
}
