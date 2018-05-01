package org.ums.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.logs.UmsLogMessage;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
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
  @UmsLogMessage(message = "Get employee information (service data)")
  public JsonObject getEmployeeAcademicInformation(@Context HttpServletRequest pHttpServletRequest,
      final @Context Request pRequest, final @PathParam("employee-id") String pEmployeeId, final UriInfo pUriInfo) {
    return mHelper.getServiceInformation(pEmployeeId, pUriInfo);
  }
}
