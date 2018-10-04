package org.ums.ems.createnew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/create/request")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class EmployeeCreateRequestResource extends MutableEmployeeCreateRequestResource {

  @Autowired
  private EmployeeCreateRequestResourceHelper mHelper;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/{employee-id}")
  public JsonObject get(@Context HttpServletRequest pHttpServletRequest, final @Context Request pRequest,
      final @PathParam("employee-id") String pEmployeeId) throws Exception {
    return mHelper.getAll(mUriInfo);
  }

}
