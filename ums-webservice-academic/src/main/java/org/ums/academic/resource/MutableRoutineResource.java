package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.RoutineResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class MutableRoutineResource extends Resource {

  @Autowired
  RoutineResourceHelper mRoutineResourceHelper;

  @POST
  public Response createRoutine(final JsonObject pJsonObject) {
    return mRoutineResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  public Response updateRoutine(final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject) throws Exception {
    return mRoutineResourceHelper.put(Long.parseLong(pJsonObject.getString("id")), pRequest, pIfMatchHeader,
        pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteRoutine(final @PathParam("object-id") String objectId) throws Exception {
    return mRoutineResourceHelper.delete(Long.parseLong(objectId));
  }

}
