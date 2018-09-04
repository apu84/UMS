package org.ums.ems.profilemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.logs.GetLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/service")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ServiceInformationResource extends MutableServiceInformationResource {

  @Autowired
  private ServiceInformationResourceHelper mHelper;

  @GET
  @Path("/{employee-id}")
  @GetLog(message = "Get service information list")
  public JsonObject get(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("employee-id") String pEmployeeId, final @Context Request pRequest) throws Exception {
    return mHelper.get(pEmployeeId, mUriInfo);
  }
}
