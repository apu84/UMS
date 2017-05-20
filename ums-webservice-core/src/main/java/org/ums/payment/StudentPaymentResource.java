package org.ums.payment;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.apache.shiro.SecurityUtils;
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
    String studentId = SecurityUtils.getSubject().toString();
    return mStudentPaymentResourceHelper.getSemesterFeeStatus(studentId, pSemesterId, mUriInfo);
  }

  @GET
  @Path("/certificate-fee")
  public JsonObject getCertificateFeeStatus(final @Context Request pRequest) throws Exception {
    String studentId = SecurityUtils.getSubject().toString();
    return mStudentPaymentResourceHelper.getCertificateFeeStatus(studentId, mUriInfo);
  }
}
