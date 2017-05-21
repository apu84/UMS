package org.ums.payment;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/receive-payment")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ReceivePayment extends Resource {
  @Autowired
  ReceivePaymentHelper mReceivePaymentHelper;

  @GET
  @Path("/{student-id}")
  public JsonObject getStudentPayments(@PathParam("student-id") String pStudentId) throws Exception {
    return mReceivePaymentHelper.getStudentPayments(pStudentId, mUriInfo);
  }

  @GET
  @Path("/{student-id}/{fee-type}")
  public JsonObject getStudentPayments(@PathParam("student-id") String pStudentId,
      @PathParam("fee-type") Integer pFeeType) throws Exception {
    return mReceivePaymentHelper.getStudentPayments(pStudentId, pFeeType, mUriInfo);
  }

  @POST
  @Path("/{student-id}")
  public Response receivePayments(@PathParam("student-id") String pStudentId, JsonObject pJsonObject) throws Exception {
    return mReceivePaymentHelper.receivePayment(pStudentId, pJsonObject);
  }
}
