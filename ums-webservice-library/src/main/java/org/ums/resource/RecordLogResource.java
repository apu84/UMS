package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.RecordLogResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.UriInfo;
import java.util.Date;

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
  @Path("/modifiedDate/{modified-date}/employeeId/{employee-id}/mfn/{mfn-no}")
  public JsonObject get(final @PathParam("modified-date") Date pModifiedDate,
      final @PathParam("employee-id") String pEmployeeId, final @PathParam("mfn-no") Long pMfn, final UriInfo pUriInfo)
      throws Exception {
    return mHelper.get(pModifiedDate, pEmployeeId, pMfn, mUriInfo);
  }

}
