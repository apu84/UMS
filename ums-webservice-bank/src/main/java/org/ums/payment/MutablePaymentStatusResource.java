package org.ums.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.fee.accounts.PaymentStatus;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Component
@Path("/payment-status")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class MutablePaymentStatusResource extends PaymentStatusResource {
  @Autowired
  private MutablePaymentStatusHelper mMutablePaymentStatusHelper;

  @PUT
  @Path("/conclude-payment")
  public Response receivePayments(JsonObject pJsonObject) throws Exception {
    return mMutablePaymentStatusHelper.updatePaymentStatus(pJsonObject, PaymentStatus.Status.VERIFIED);
  }

  @PUT
  @Path("/reject-payment")
  public Response rejectPayments(JsonObject pJsonObject) throws Exception {
    return mMutablePaymentStatusHelper.updatePaymentStatus(pJsonObject, PaymentStatus.Status.REJECTED);
  }
}
