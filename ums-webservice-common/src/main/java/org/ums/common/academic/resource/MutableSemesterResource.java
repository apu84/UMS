package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.domain.model.MutableSemester;
import org.ums.domain.model.Semester;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class MutableSemesterResource extends Resource {
  @Autowired
  ResourceHelper<Semester, MutableSemester, Integer> mResourceHelper;

  @POST
  public Response createSemester(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateSemester(final @PathParam("object-id") String pObjectId,
                                 final @Context Request pRequest,
                                 final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
                                 final JsonObject pJsonObject) throws Exception {
    Semester semester = mResourceHelper.load(Integer.parseInt(pObjectId));
    if (semester != null) {
      mResourceHelper.put(semester, pRequest, pIfMatchHeader, pJsonObject);
    }
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteSemester(final @PathParam("object-id") String pObjectId) throws Exception {
    Semester semester = mResourceHelper.load(Integer.parseInt(pObjectId));
    if (semester != null) {
      mResourceHelper.delete(semester.edit());
    }
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }
}
