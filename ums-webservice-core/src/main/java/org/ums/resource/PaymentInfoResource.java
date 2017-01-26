package org.ums.resource;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 25-Jan-17.
 */
@Component
@Path("/core/payment")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class PaymentInfoResource extends MutablePaymentInfoResource {

  @GET
  @Path("/semester/{semester-id}/receiptId/{receipt-id}")
  public JsonObject getAdmissionPaymentInfo(final @Context Request pRequest,
      final @PathParam("semester-id") int pSemesterId,
      final @PathParam("receipt-id") String pReceiptId) {
    return mHelper.getAdmissionPaymentInfo(pReceiptId, pSemesterId, mUriInfo);
  }
}
