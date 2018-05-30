package org.ums.resource;

import org.springframework.stereotype.Component;
import org.ums.logs.GetLog;

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
@Path("item")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ItemResource extends MutableItemResource {

  @GET
  @Path("/all")
  @GetLog(message = "Get all items")
  public JsonObject getAll(@Context HttpServletRequest pHttpServletRequest) throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  @GetLog(message = "Get an item")
  public Response get(@Context HttpServletRequest pHttpServletRequest, final @Context Request pRequest, final @PathParam("object-id") Long pObjectId) throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }

  @GET
  @Path("/mfn/{mfn-no}")
  @GetLog(message = "Get an item by mfn")
  public JsonObject getByMfn(@Context HttpServletRequest pHttpServletRequest, final @Context Request pRequest, final @PathParam("mfn-no") Long pMfn) throws Exception {
    return mResourceHelper.getByMfn(pMfn, mUriInfo);
  }

  @GET
  @Path("/accessionNumber/{accession-no}")
  @GetLog(message = "Get an item by accession number")
  public JsonObject getByAccessionNo(@Context HttpServletRequest pHttpServletRequest, final @Context Request pRequest,
      final @PathParam("accession-no") String pAccessionNumber) throws Exception {
    return mResourceHelper.getByAccessionNUmber(pAccessionNumber, mUriInfo);
  }

}
