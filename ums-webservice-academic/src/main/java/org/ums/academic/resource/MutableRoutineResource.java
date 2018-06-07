package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.RoutineResourceHelper;
import org.ums.logs.GetLog;
import org.ums.logs.PostLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class MutableRoutineResource extends Resource {

  @Autowired
  RoutineResourceHelper mRoutineResourceHelper;

  @POST
  @PostLog(message = "Created new routine information")
  public Response createRoutine(final @Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject) {
    return mRoutineResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @PostLog(message = "Updated routine information")
  public Response updateRoutine(@Context HttpServletRequest httpServletRequest, final JsonObject pJsonObject,
      final @Context Request pRequest, final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader) throws Exception {
    return mRoutineResourceHelper.put(Long.parseLong(pJsonObject.getString("id")), pRequest, pIfMatchHeader,
        pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  @PostLog(message = "Deleted routine information")
  public Response deleteRoutine(@Context HttpServletRequest httpServletRequest,
      final @PathParam("object-id") String objectId) throws Exception {
    return mRoutineResourceHelper.delete(Long.parseLong(objectId));
  }

}
