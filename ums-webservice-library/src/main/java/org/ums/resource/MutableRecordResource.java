package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.logs.DeleteLog;
import org.ums.logs.PostLog;
import org.ums.logs.PutLog;
import org.ums.resource.helper.RecordResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 19-Feb-17.
 */
public class MutableRecordResource extends Resource {
  @Autowired
  ResourceHelper<Record, MutableRecord, Long> mResourceHelper;

  @Autowired
  RecordResourceHelper mHelper;

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  @PutLog(message = "Updated a library record")
  public Response updateRecord(final @PathParam("object-id") Long pObjectId, final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @POST
  @PostLog(message = "Created a library record")
  public Response createRecord(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  @DeleteLog(message = "Delete a library record")
  public Response deleteRecord(final @PathParam("object-id") String pMfnNo) throws Exception {
    return mHelper.deleteRecord(pMfnNo);
  }
}
