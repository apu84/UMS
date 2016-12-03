package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.EmployeeResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class MutableEmployeeResource extends Resource {

  @Autowired
  EmployeeResourceHelper mEmployeeResourceHelper;

  @POST
  public Response createEmployee(final JsonObject pJsonObject) {
    return mEmployeeResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateEmployeeInformation(final @PathParam("object-id") String pObjectId,
      final @Context Request pRequest, final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
      final JsonObject pJsonObject) throws Exception {

    return mEmployeeResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteAnEmployee(final @PathParam("object-id") String objectId) throws Exception {
    return mEmployeeResourceHelper.delete(objectId);
  }
}
