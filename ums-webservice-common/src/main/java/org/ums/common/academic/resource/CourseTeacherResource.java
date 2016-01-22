package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.CourseTeacherResourceHelper;
import org.ums.domain.model.regular.Syllabus;
import org.ums.manager.CourseTeacherManager;
import org.ums.manager.SemesterSyllabusMapManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/academic/courseTeacher")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CourseTeacherResource extends Resource {
  @Autowired
  CourseTeacherManager mCourseTeacherManager;

  @Autowired
  CourseTeacherResourceHelper mResourceHelper;

  @Autowired
  SemesterSyllabusMapManager mSemesterSyllabusMapManager;

  @GET
  @Path("/programId" + "/{program-id}" + "/semesterId" + "/{semester-id}")
  public JsonObject get(final @Context Request pRequest,
                                  final @PathParam("program-id") Integer pProgramId,
                                  final @PathParam("semester-id") Integer pSemesterId) throws Exception {
    return mResourceHelper.getCourseTeachers(pProgramId, pSemesterId, mUriInfo);
  }

  @GET
  @Path("/programId" + "/{program-id}" + "/semesterId" + "/{semester-id}" + "/year" + "/{year}" + "/semester"+ "/{semester}")
  public JsonObject get(final @Context Request pRequest,
                        final @PathParam("program-id") Integer pProgramId,
                        final @PathParam("semester-id") Integer pSemesterId,
                        final @PathParam("year") Integer pYear,
                        final @PathParam("semester") Integer pSemester) throws Exception {
    return mResourceHelper.getCourseTeachers(pProgramId, pSemesterId, pYear, pSemester, mUriInfo);
  }

  @POST
  public Response post(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }
}
