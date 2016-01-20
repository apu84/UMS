package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.CourseTeacherResourceHelper;
import org.ums.manager.CourseTeacherManager;
import org.ums.manager.SemesterSyllabusMapManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("/academic/course")
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
  public JsonObject getBySyllabus(final @Context Request pRequest,
                                  final @PathParam("program-id") Integer pProgramId,
                                  final @PathParam("semester-id") Integer pSemesterId) throws Exception {
    /*List<Syllabus> syllabusList =
    return mResourceHelper.getCourseTeachers(pProgramId, pSemesterId, mUriInfo);*/
    return null;
  }
}
