package org.ums.employee.experience;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/experience")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ExperienceInformationResource extends MutableExperienceInformationResource {

  @GET
  @Path("/get/employeeId/{employee-id}")
  public JsonObject getExperienceInformation(final @PathParam("employee-id") String pEmployeeId,
      final @Context Request pRequest) throws Exception {
    return mExperienceInformationResourceHelper.getExperienceInformation(pEmployeeId, mUriInfo);
  }
}
