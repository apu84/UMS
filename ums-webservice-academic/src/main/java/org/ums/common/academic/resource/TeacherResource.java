package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.academic.resource.helper.TeacherResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("/academic/teacher")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class TeacherResource extends Resource {
  @Autowired
  TeacherResourceHelper mResourceHelper;

  @GET
  @Path("/department" + PATH_PARAM_OBJECT_ID)
  public JsonObject getSyllabusList(final @Context Request pRequest,
      final @PathParam("object-id") String pDepartmentId) {
    return mResourceHelper.getByDepartment(pDepartmentId, mUriInfo);
  }
}
