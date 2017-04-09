package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.PublisherResourceHelper;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 04-Feb-17.
 */

@Component
@Path("publisher")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class PublisherResource extends MutableSupplierResource {
  @Autowired
  PublisherResourceHelper mResourceHelper;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") Integer pObjectId)
      throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }

  @GET
  @Path("/all/ipp/{item-per-page}/page/{page}/order/{order}/filter/{filter}")
  public JsonObject getAllForPagination(final @Context Request pRequest,
      final @PathParam("item-per-page") int pItemPerPage, final @PathParam("page") int pPage,
      final @PathParam("order") String pOrder, final @PathParam("filter") String pFilter) throws Exception {
    return mResourceHelper.getAllForPagination(pItemPerPage, pPage, pOrder, UmsUtils.getWhereClause(pFilter), mUriInfo);
  }

}
