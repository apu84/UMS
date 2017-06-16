package org.ums.resource;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employmentType")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class EmploymentTypeResource extends MutableEmploymentTypeResource {

  @GET
  @Path("/all")
  public JsonObject getAll(final @Context Request pRequest) throws Exception {
    return mResourceHelper.getEmploymentTypes(mUriInfo);
  }
}
