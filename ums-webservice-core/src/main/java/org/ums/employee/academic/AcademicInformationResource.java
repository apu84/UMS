package org.ums.employee.academic;

import org.springframework.stereotype.Component;
import org.ums.logs.UmsLogMessage;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/academic")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AcademicInformationResource extends MutableAcademicInformationResource {

  @GET
  @Path("/get/employeeId/{employee-id}")
  @UmsLogMessage(message = "Get employee information (academic data)")
  public JsonObject getAcademicInformation(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("employee-id") String pEmployeeId, final @Context Request pRequest) throws Exception {
    return mHelper.getAcademicInformation(pEmployeeId, mUriInfo);
  }
}
