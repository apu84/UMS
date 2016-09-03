package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.EmployeeManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("academic/employee")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class EmployeeResource extends MutableEmployeeResource{
  @Autowired
  EmployeeManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception{
    return mEmployeeResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/employeeById")
  public JsonObject getEmployeeById() throws Exception{
    return mEmployeeResourceHelper.getByEmployeeId(mUriInfo);
  }

  @GET
  @Path("/designation/{designationId}")
  public JsonObject getByDesignation(final @Context Request pRequest, final @PathParam("designationId") String designationId) throws Exception{
    return mEmployeeResourceHelper.getByDesignation(designationId,pRequest,mUriInfo);
  }


  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") String pObjectId)throws Exception{
    return mEmployeeResourceHelper.get(pObjectId,pRequest,mUriInfo);
  }
}
