package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.library.Item;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.resource.helper.ItemResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 04-Mar-17.
 */
public class MutableItemResource extends Resource {
  @Autowired
  ItemResourceHelper mResourceHelper;

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateItem(final @PathParam("object-id") Long pObjectId,
      final @Context Request pRequest, final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
      final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @POST
  public Response createItem(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @POST
  @Path("/batch")
  public Response createItems(final JsonObject pJsonObject) throws Exception {

    return mResourceHelper.batchPost(pJsonObject, mUriInfo);
  }

}
