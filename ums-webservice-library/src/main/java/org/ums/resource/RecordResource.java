package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.logs.GetLog;
import org.ums.resource.helper.RecordResourceHelper;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 19-Feb-17.
 */

@Component
@Path("record")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class RecordResource extends MutableRecordResource {

  @Autowired
  RecordResourceHelper mResourceHelper;

  @GET
  @Path("/all")
  @GetLog(message = "Get all library records")
  public JsonObject getAll(@Context HttpServletRequest pHttpServletRequest) throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  @GetLog(message = "Get a library record")
  public Response get(@Context HttpServletRequest pHttpServletRequest, final @Context Request pRequest,
      final @PathParam("object-id") Long pObjectId) throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }

  @GET
  // @Path("/all/ipp/{item-per-page}/page/{page}/order/{order}/filter/{filter:.+}")
  @Path("/all/ipp/{item-per-page}/page/{page}/order/{order}")
  @GetLog(message = "Get library record paginated List")
  public JsonObject getAllForPagination(@Context HttpServletRequest pHttpServletRequest,
      final @Context Request pRequest, final @PathParam("item-per-page") int pItemPerPage,
      final @PathParam("page") int pPage, final @PathParam("order") String pOrder,
      final @QueryParam("filter") String pFilter) throws Exception {
    // return mResourceHelper.getAllForPagination(pItemPerPage, pPage, pOrder,
    // UmsUtils.getWhereClause(pFilter), mUriInfo);
    return mResourceHelper.searchRecord(pPage, pItemPerPage, pFilter, mUriInfo);
  }

}
