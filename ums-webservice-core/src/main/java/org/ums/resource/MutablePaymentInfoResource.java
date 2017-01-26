package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.PaymentInfoResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 25-Jan-17.
 */
public class MutablePaymentInfoResource extends Resource {

  @Autowired
  PaymentInfoResourceHelper mHelper;

  @POST
  @Path("/admission")
  public Response createPaymentInfo(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }
}
