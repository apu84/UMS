package org.ums.academic.resource;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by My Pc on 7/13/2016.
 */

@Component
@Path("/academic/UGRegistrationResultResource")
public class UGRegistratonResultResource extends MutableUGRegistrationResource {

  @GET
  @Path("/CarryClearanceImprovement")
  public JsonObject getResultByCarryClearanceAndImprovement(final @Context Request pRequest) {
    return mHelper.getResultForCarryClearanceAndImprovement(pRequest, mUriInfo);
  }
}
