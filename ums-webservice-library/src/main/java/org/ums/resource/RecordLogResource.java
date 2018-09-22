package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.RecordLogResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.UriInfo;

@Component
@Path("record/log")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class RecordLogResource extends MutableRecordLogResource {

  @Autowired
  private RecordLogResourceHelper mHelper;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/filter/query")
  public JsonObject get(final @QueryParam("modifiedDate") String pModifiedDate,
      final @QueryParam("modifiedBy") String pModifiedBy, final @QueryParam("mfn") String pMfn, final UriInfo pUriInfo)
      throws Exception {
    return mHelper.get(pModifiedDate, pModifiedBy, pMfn, mUriInfo);
  }

}
