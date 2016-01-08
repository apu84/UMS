package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.domain.model.regular.Course;
import org.ums.domain.model.mutable.MutableCourse;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class MutableCourseResource extends Resource {
  @Autowired
  ResourceHelper<Course, MutableCourse, String> mResourceHelper;

  @POST
  public Response createCourse(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateCourse(final @PathParam("object-id") String pObjectId,
                               final @Context Request pRequest,
                               final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
                               final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteCourse(final @PathParam("object-id") String pObjectId) throws Exception {
    return mResourceHelper.delete(pObjectId);
  }
}