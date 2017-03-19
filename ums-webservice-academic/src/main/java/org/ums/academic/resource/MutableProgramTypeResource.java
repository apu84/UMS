package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.mutable.MutableProgramType;
import org.ums.resource.Resource;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class MutableProgramTypeResource extends Resource {

  @Autowired
  ResourceHelper<ProgramType, MutableProgramType, Integer> mResourceHelper;

  @POST
  public Response createProgramType(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateProgramType(final @PathParam("object-id") int pObjectId, final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject) throws Exception {

    return mResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteProgramType(final @PathParam("object-id") int pObjectId) throws Exception {
    return mResourceHelper.delete(pObjectId);
  }
}
