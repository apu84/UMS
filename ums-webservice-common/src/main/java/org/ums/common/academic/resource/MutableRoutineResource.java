package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.RoutineResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by My Pc on 3/6/2016.
 */
public class MutableRoutineResource extends Resource {

  @Autowired
  RoutineResourceHelper mRoutineResourceHelper;

  @POST
  public Response createRoutine(final  JsonObject pJsonObject) throws Exception{
    return mRoutineResourceHelper.post(pJsonObject,mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateRoutine(
      final @PathParam("object-id") String pObjectId,
      final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
      final JsonObject pJsonObject
      )throws Exception{

    return mRoutineResourceHelper.put(pObjectId,pRequest,pIfMatchHeader,pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteRoutine(final @PathParam("object-id") String objectId) throws Exception{
    return mRoutineResourceHelper.delete(objectId);
  }


}
