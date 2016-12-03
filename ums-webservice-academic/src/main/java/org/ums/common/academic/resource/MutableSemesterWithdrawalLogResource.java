package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.SemesterWithdrawalLogResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class MutableSemesterWithdrawalLogResource extends Resource {

  @Autowired
  SemesterWithdrawalLogResourceHelper mHelper;

  @POST
  public Response createSemesterWithdrawalLog(final JsonObject pJsonObject) {
    return mHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateSemesterWithdrawalLog(final @PathParam("object-id") int pObjectId,
      final @Context Request pRequest, final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
      final JsonObject pJsonObject) throws Exception {

    return mHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteSemesterWithdrawalLog(final @PathParam("object-id") int objectId)
      throws Exception {
    return mHelper.delete(objectId);
  }
}
