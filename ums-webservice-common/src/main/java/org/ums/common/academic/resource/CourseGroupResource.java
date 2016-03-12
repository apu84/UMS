package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.domain.model.mutable.MutableCourseGroup;
import org.ums.domain.model.readOnly.CourseGroup;
import org.ums.manager.CourseGroupManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/academic/courseGroup")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CourseGroupResource extends Resource {
  @Autowired
  ResourceHelper<CourseGroup, MutableCourseGroup, Integer> mResourceHelper;

  @Autowired
  @Qualifier("courseGroupManager")
  CourseGroupManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") int pObjectId) throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }
}
