package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.SemesterWithdrawalLogManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("academic/semesterWithdrawalLog")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SemesterWithdrawalLogResource extends MutableSemesterWithdrawalLogResource {

  @Autowired
  SemesterWithdrawalLogManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/semesterWithdrawalId/{semesterWithdrawalId}")
  public JsonObject getForStudent(final @Context Request pRequest,
      final @PathParam("semesterWithdrawalId") int semesterWithdrawalId) {
    return mHelper.getBySemesterWithdrawalId(semesterWithdrawalId, pRequest, mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") int pObjectId)
      throws Exception {
    return mHelper.get(pObjectId, pRequest, mUriInfo);
  }

}
