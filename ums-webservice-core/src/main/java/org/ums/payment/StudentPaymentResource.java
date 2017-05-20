package org.ums.payment;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/student-payment")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class StudentPaymentResource extends Resource {
  @Autowired
  StudentPaymentResourceHelper mStudentPaymentResourceHelper;

  @GET
  @Path("/semester-fee/{semesterId}")
  public JsonObject getSemesterFeeStatus(final @Context Request pRequest, final @PathParam("semesterId") int pSemesterId)
      throws Exception {
    return mStudentPaymentResourceHelper.getSemesterFeeStatus(getLoggedInUserId(), pSemesterId, mUriInfo);
  }

  @GET
  @Path("/certificate-fee")
  public JsonObject getCertificateFeeStatus(final @Context Request pRequest) throws Exception {
    return mStudentPaymentResourceHelper.getCertificateFeeStatus(getLoggedInUserId(), mUriInfo);
  }

  @GET
  @Path("/dues")
  public JsonObject getDuesStatus() throws Exception {
    return mStudentPaymentResourceHelper.getDuesStatus(getLoggedInUserId(), mUriInfo);
  }

  @GET
  @Path("/penalty")
  public JsonObject getPenaltyStatus() throws Exception {
    return mStudentPaymentResourceHelper.getPenaltyStatus(getLoggedInUserId(), mUriInfo);
  }

}
