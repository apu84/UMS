package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.FineResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableFineResource extends Resource {

  @Autowired
  FineResourceHelper mHelper;

  @PUT
  @Path("/update")
  public Response updateForCheckIn(final JsonObject pJsonObject) {
    return mHelper.updateFine(pJsonObject, mUriInfo);
  }

}
