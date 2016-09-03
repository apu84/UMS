package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.ums.common.academic.resource.helper.StudentResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class MutableStudentResource extends Resource {
  @Autowired
  @Qualifier("StudentResourceHelper")
  StudentResourceHelper mResourceHelper;

  @POST
  public Response create(final JsonObject pJsonObject) throws Exception {

    return  mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response update(final @PathParam("object-id") String pObjectId,
                         final @Context Request pRequest,
                         final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
                         final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response delete(final @PathParam("object-id") String pObjectId) throws Exception {
    return mResourceHelper.delete(pObjectId);
  }
}
