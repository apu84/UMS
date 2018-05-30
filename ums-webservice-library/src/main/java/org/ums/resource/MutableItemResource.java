package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.DeleteLog;
import org.ums.logs.PostLog;
import org.ums.logs.PutLog;
import org.ums.resource.helper.ItemResourceHelper;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
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
  @PutLog(message = "Updated an item")
  public Response updateItem(@Context HttpServletRequest pHttpServletRequest, final @PathParam("object-id") Long pObjectId, final @Context Request pRequest,
                             final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject) throws Exception {
    /* return mResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject); */

    return mResourceHelper.updateItem(pJsonObject, mUriInfo);
  }

  @POST
  @PostLog(message = "Created an item")
  public Response createItem(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @POST
  @Path("/batch")
  @PostLog(message = "Batch create items")
  public Response createItems(@Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject) throws Exception {

    return mResourceHelper.batchPost(pJsonObject, mUriInfo);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  @DeleteLog(message = "Deleted an item")
  public Response deleteItem(@Context HttpServletRequest pHttpServletRequest, final @PathParam("object-id") String pItemNo) throws Exception {
    return mResourceHelper.deleteItem(pItemNo);
  }

}
