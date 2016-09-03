package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.ParameterResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by My Pc on 3/13/2016.
 */
public class MutableParameterResource extends Resource {
  @Autowired
  ParameterResourceHelper mParameterResourceHelper;

  @POST
  public Response createAcademicCalenderParameter(final JsonObject pJsonObject) throws Exception{
    return mParameterResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateRoutine(
      final @PathParam("object-id") String pObjectId,
      final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
      final JsonObject pJsonObject
      )throws Exception{
    return mParameterResourceHelper.put(pObjectId,pRequest,pIfMatchHeader,pJsonObject);
  }
}
