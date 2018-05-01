package org.ums.employee.experience;

import org.springframework.stereotype.Component;
import org.ums.logs.UmsLogMessage;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
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
  @UmsLogMessage(message = "Get employee information (experience data)")
  public JsonObject getExperienceInformation(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("employee-id") String pEmployeeId, final @Context Request pRequest) throws Exception {
    return mExperienceInformationResourceHelper.getExperienceInformation(pEmployeeId, mUriInfo);
  }
}
