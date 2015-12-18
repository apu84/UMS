package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.domain.model.MutableSyllabus;
import org.ums.domain.model.Syllabus;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class MutableSyllabusResource extends Resource {
  @Autowired
  ResourceHelper<Syllabus, MutableSyllabus, String> mResourceHelper;

  @POST
  public Response createSyllabus(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateSyllabus(final @PathParam("object-id") String pObjectId,
                                 final @Context Request pRequest,
                                 final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
                                 final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteSemester(final @PathParam("object-id") String pObjectId) throws Exception {
    return mResourceHelper.delete(pObjectId);
  }
}
