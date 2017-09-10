package org.ums.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Component
@Path("employee/service")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ServiceInformationResource extends MutableServiceInformationResource {

  @Autowired
  private ServiceInformationResourceHelper mHelper;

  @GET
  @Path("/get/employeeId/{employee-id}")
  public JsonObject getEmployeeAcademicInformation(final @Context Request pRequest,
      final @PathParam("employee-id") String pEmployeeId, final UriInfo pUriInfo) {
    return mHelper.getServiceInformation(pEmployeeId, pUriInfo);
  }
}
