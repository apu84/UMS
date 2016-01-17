package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.CourseResourceHelper;
import org.ums.domain.model.regular.Course;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/academic/course")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CourseResource extends MutableCourseResource {
  @Autowired
  CourseResourceHelper mResourceHelper;

  @Autowired
  @Qualifier("courseManager")
  ContentManager<Course, MutableCourse, String> mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }
  @GET
  @Path("/syllabus" + PATH_PARAM_OBJECT_ID)
  public JsonObject getBySyllabus(final @Context Request pRequest, final @PathParam("object-id") String pObjectId) throws Exception {
    return mResourceHelper.getBySyllabus(pObjectId, pRequest, mUriInfo);
  }
  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") String pObjectId) throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }

}
