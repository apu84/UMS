package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.SemesterWithdrawalResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class MutableSemesterWithdrawalResource extends Resource {

  @Autowired
  SemesterWithdrawalResourceHelper mHelper;

  @POST
  public Response createSemesterWithdrawApplication(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateSemesterWithdrawApplication(final @PathParam("object-id") int pObjectId,
      final @Context Request pRequest, final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
      final JsonObject pJsonObject) throws Exception {

    return mHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteSemesterWithdrawApplication(final @PathParam("object-id") int objectId)
      throws Exception {
    return mHelper.delete(objectId);
  }
}
