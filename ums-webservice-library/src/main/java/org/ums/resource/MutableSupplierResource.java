package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableSupplier;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class MutableSupplierResource extends Resource {
  @Autowired
  ResourceHelper<Supplier, MutableSupplier, Long> mResourceHelper;

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateSupplier(final @PathParam("object-id") Long pObjectId, final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @POST
  public Response createSupplier(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteSupplier(final @PathParam("object-id") Long pObjectId) throws Exception {
    return mResourceHelper.delete(pObjectId);
  }

}
