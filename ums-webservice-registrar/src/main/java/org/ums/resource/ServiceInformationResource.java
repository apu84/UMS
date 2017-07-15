package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.resource.helper.ServiceInformationResourceHelper;

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
  @Path("/getServiceInformation/employeeId/{employee-id}")
  public JsonObject getEmployeeAcademicInformation(final @Context Request pRequest,
      final @PathParam("employee-id") String pEmployeeId, final UriInfo pUriInfo) {
    return mHelper.getServiceInformation(pEmployeeId, pUriInfo);
  }
}
