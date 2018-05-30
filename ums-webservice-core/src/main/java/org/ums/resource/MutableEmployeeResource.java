package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.DeleteLog;
import org.ums.logs.PostLog;
import org.ums.logs.PutLog;
import org.ums.resource.helper.EmployeeResourceHelper;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class MutableEmployeeResource extends Resource {

  @Autowired
  EmployeeResourceHelper mEmployeeResourceHelper;

  @POST
  @PostLog(message = "Created a new employee")
  public Response createEmployee(@Context HttpServletRequest httpServletRequest, final JsonObject pJsonObject) {
    return mEmployeeResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  @PutLog(message = "Updated an employee")
  public Response updateEmployeeInformation(@Context HttpServletRequest httpServletRequest, final @PathParam("object-id") String pObjectId,
      final @Context Request pRequest, final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
      final JsonObject pJsonObject) throws Exception {

    return mEmployeeResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  @DeleteLog(message = "Deleted an employee")
  public Response deleteAnEmployee(@Context HttpServletRequest httpServletRequest, final @PathParam("object-id") String objectId) throws Exception {
    return mEmployeeResourceHelper.delete(objectId);
  }
}
