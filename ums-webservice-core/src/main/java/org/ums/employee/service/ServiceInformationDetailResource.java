package org.ums.employee.service;

import org.springframework.stereotype.Component;
import org.ums.employee.service.MutableServiceInformationDetailResource;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Component
@Path("employee/serviceDetail")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ServiceInformationDetailResource extends MutableServiceInformationDetailResource {

  @GET
  @Path("/get")
  public JsonObject getEmployeeAcademicInformation(final @Context Request pRequest, final UriInfo pUriInfo) {
    return null;
  }
}
