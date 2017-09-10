package org.ums.employee.additional;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/areaOfInterest")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AreaOfInterestInformationResource extends MutableAreaOfInterestInformationResource {

  @GET
  @Path("/getAreaOfInterestInformation/employeeId/{employee-id}")
  public JsonObject getAreaOfInterestInformation(final @PathParam("employee-id") String pEmployeeId,
      final @Context Request pRequest) throws Exception {
    return mResourceHelper.getAreaOfInterestInformation(pEmployeeId, mUriInfo);
  }
}
