package org.ums.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;
import org.ums.resource.filter.FilterItem;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/payment-status")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class PaymentStatusResource extends Resource {
  @Autowired
  PaymentStatusHelper mPaymentStatusHelper;
  private int mDefaultNoOfItems = 20;

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

  @POST
  @Path("/paginated/filtered")
  public JsonObject getFilteredPaymentStatus(@QueryParam("pageNumber") Integer pageNumber,
      @QueryParam("itemsPerPage") Integer itemsPerPage, JsonObject pFilter) throws Exception {
    return mPaymentStatusHelper.getReceivedPayments(itemsPerPage == null ? mDefaultNoOfItems : itemsPerPage,
        pageNumber == null ? 1 : pageNumber, pFilter, mUriInfo);
  }

  @PUT
  @Path("/conclude-payment")
  public Response receivePayments(JsonObject pJsonObject) throws Exception {
    return mPaymentStatusHelper.updatePaymentStatus(pJsonObject);
  }
}
