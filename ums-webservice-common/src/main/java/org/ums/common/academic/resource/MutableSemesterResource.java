package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.SemesterResourceHelper;
import org.ums.domain.model.Semester;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class MutableSemesterResource extends Resource {
  @Autowired
  SemesterResourceHelper mResourceHelper;

  @POST
  public Response createSemester(final JsonObject pJsonObject) throws Exception {
    mResourceHelper.post(pJsonObject);
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateSemester(final @PathParam("object-id") String pObjectId, final JsonObject pJsonObject) throws Exception {
    Semester semester = mResourceHelper.load(pObjectId);
    if (semester != null) {
      mResourceHelper.put(semester, pJsonObject);
    }
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateSemester(final @PathParam("object-id") String pObjectId) throws Exception {
    Semester semester = mResourceHelper.load(pObjectId);
    if (semester != null) {
      mResourceHelper.delete(semester);
    }
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }
}
