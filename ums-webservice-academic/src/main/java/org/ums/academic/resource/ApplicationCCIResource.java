package org.ums.academic.resource;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.ApplicationCCIManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by My Pc on 7/14/2016.
 */
@Component
@Path("academic/applicationCCI")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ApplicationCCIResource extends MutableApplicationCCIResource {

  @Autowired
  ApplicationCCIManager mApplicationCCIManager;

  @GET
  @Path("/student")
  public JsonObject getApplicationCCIForStudent(@Context Request pRequest) {
    return mHelper.getApplicationCCIInfoForStudent(pRequest, mUriInfo);
  }

  @GET
  @Path("/semester/{semester-id}/examDate/{exam-date}")
  public JsonObject getApplicationCCIForSeatPlan(@Context Request pRequest,
      @PathParam("semester-id") Integer pSemesterId, @PathParam("exam-date") String pExamDate) {
    return mHelper.getApplicationCCIForSeatPlan(pSemesterId, pExamDate, pRequest, mUriInfo);
  }

  @GET
  @Path("/studentId/{student-id}/semesterId/{semester-id}")
  public JsonObject getTotalcarry(final @Context Request pRequest, final @PathParam("student-id") String pStudentId,
      final @PathParam("semester-id") Integer pSemesterid) {
    return mHelper.getTotalCarry(pStudentId, pSemesterid, pRequest, mUriInfo);
  }

  @GET
  @Path("/seatPlanView")
  public JsonObject getApplicationCCIForSeatPlanView(@Context Request pRequest) {
    return mHelper.getApplicationCCIForSeatPlanViewingOfStudent(mUriInfo);
  }

  @GET
  @Path("/headsApproval")
  public JsonObject getApplicationCarryForHeadsApproval(@Context Request pRequest) {
    return mHelper.getApplicationCarryForHeadsApproval(pRequest, mUriInfo);
  }

  @GET
  @Path("/getAllcarryInfo")
  public JsonObject getApplicationCarryForHeadsApprovalAndAppiled(@Context Request pRequest) {
    return mHelper.getApplicationCarryForHeadsApprovalAndAppiled(pRequest, mUriInfo);
  }

}
