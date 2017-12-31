package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.CirculationResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableCirculationResource extends Resource {

  @Autowired
  CirculationResourceHelper mHelper;

  @POST
  @Path("/save")
  public Response save(final JsonObject pJsonObject) throws Exception {
    return mHelper.saveCheckout(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/update")
  public Response update(final JsonObject pJsonObject) {
    return mHelper.updateCirculation(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/update/checkIn")
  public Response updateForCheckIn(final JsonObject pJsonObject) {
    return mHelper.updateCirculationForCheckIn(pJsonObject, mUriInfo);
  }
}
