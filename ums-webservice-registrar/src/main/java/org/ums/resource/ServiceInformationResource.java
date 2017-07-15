package org.ums.resource;

import org.springframework.stereotype.Component;

import javax.ws.rs.*;

@Component
@Path("registrar/employee")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ServiceInformationResource extends MutableServiceInformationResource {

  // @GET
  // @Path("/getAcademicInformation/employeeId/{employee-id}")
  // public JsonObject getEmployeeAcademicInformation(final @Context Request pRequest,
  // final @PathParam("employee-id") int pEmployeeId, final UriInfo pUriInfo) {
  // return employeeInformationResourceHelper.getEmployeeInformation(pEmployeeId, pUriInfo);
  // }
}
