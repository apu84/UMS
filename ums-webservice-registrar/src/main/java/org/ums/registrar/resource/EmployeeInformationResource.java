package org.ums.registrar.resource;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Component
@Path("registrar/employee")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class EmployeeInformationResource extends MutableEmployeeInformationResource {

  @GET
  @Path("/getAcademicInformation/employeeId/{employee-id}")
  public JsonObject getEmployeeAcademicInformation(final @Context Request pRequest,
      final @PathParam("employee-id") int pEmployeeId, final UriInfo pUriInfo) {
    return employeeInformationResourceHelper.getEmployeeAcademicInformation(pEmployeeId, pUriInfo);
  }
}
