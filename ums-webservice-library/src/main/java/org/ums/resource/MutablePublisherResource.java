package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.mutable.library.MutablePublisher;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class MutablePublisherResource extends Resource {
  @Autowired
  ResourceHelper<Publisher, MutablePublisher, Long> mResourceHelper;

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updatePublisher(final @PathParam("object-id") Long pObjectId, final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @POST
  public Response createPublisher(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deletePublisher(final @PathParam("object-id") Long pObjectId) throws Exception {
    return mResourceHelper.delete(pObjectId);
  }

}
