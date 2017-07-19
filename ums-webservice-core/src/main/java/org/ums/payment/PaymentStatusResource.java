package org.ums.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;

@Component
@Path("/payment-status")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class PaymentStatusResource extends Resource {
  @Autowired
  @Qualifier("PaymentStatusHelper")
  private PaymentStatusHelper mPaymentStatusHelper;
  protected int mDefaultNoOfItems = 20;

  @POST
  @Path("/paginated/filtered")
  public JsonObject getFilteredPaymentStatus(@QueryParam("pageNumber") Integer pageNumber,
      @QueryParam("itemsPerPage") Integer itemsPerPage, JsonObject pFilter) throws Exception {
    return mPaymentStatusHelper.getReceivedPayments(itemsPerPage == null ? mDefaultNoOfItems : itemsPerPage,
        pageNumber == null ? 1 : pageNumber, pFilter, mUriInfo);
  }

  @GET
  @Path("/filters")
  public JsonArray getFilters() throws Exception {
    return mPaymentStatusHelper.getFilterItems();
  }

  @GET
  @Path("/paginated")
  public JsonObject getPaymentStatus(@QueryParam("pageNumber") Integer pageNumber,
      @QueryParam("itemsPerPage") Integer itemsPerPage) throws Exception {
    return mPaymentStatusHelper.getReceivedPayments(itemsPerPage == null ? mDefaultNoOfItems : itemsPerPage,
        pageNumber == null ? 1 : pageNumber, mUriInfo);
  }
}
