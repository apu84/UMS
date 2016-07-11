package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.CourseMaterialResourceHelper;
import org.ums.manager.BinaryContentManager;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import java.util.List;
import java.util.Map;

@Component
@Path("/academic/courseMaterial")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CourseMaterialResource {
  @Autowired
  CourseMaterialResourceHelper mCourseMaterialResourceHelper;

  @Autowired
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Autowired
  SemesterManager mSemesterManager;

  @Autowired
  CourseManager mCourseManager;

  @GET
  @Path("/semester/{semester-id}/course/{course-id}")
  public List<Map<String, Object>> getBySemesterProgram(final @Context Request pRequest, final @PathParam("semester-id") Integer pSemesterId,
                                                        final @PathParam("course-id") String pCourseId) throws Exception {
    String semesterName = mSemesterManager.get(pSemesterId).getName();
    String courseName = mCourseManager.get(pCourseId).getNo();
    return mBinaryContentManager.list("/" + semesterName + "/" + courseName, BinaryContentManager.Domain.COURSE_MATERIAL);
  }
}
