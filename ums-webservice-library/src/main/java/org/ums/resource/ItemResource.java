package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.ItemResourceHelper;
import org.ums.resource.helper.RecordResourceHelper;
import javax.json.JsonObject;
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
  public JsonObject getAll() throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") Long pObjectId) throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }

  @GET
  @Path("/mfn/{mfn-no}")
  public JsonObject getByMfn(final @Context Request pRequest, final @PathParam("mfn-no") Long pMfn) throws Exception {
    return mResourceHelper.getByMfn(pMfn, mUriInfo);
  }

  @GET
  @Path("/accessionNumber/{accession-no}")
  public JsonObject getByAccessionNo(final @Context Request pRequest,
      final @PathParam("accession-no") String pAccessionNumber) throws Exception {
    return mResourceHelper.getByAccessionNUmber(pAccessionNumber, mUriInfo);
  }

}
