package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
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
@Consumes(Resource.MIME_TYPE_JSON)
public class CourseMaterialResource extends Resource {

  @Autowired
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Autowired
  SemesterManager mSemesterManager;

  @Autowired
  CourseManager mCourseManager;

  @POST
  @Path("/semester/{semester-name}/course/{course-no}")
  public List<Map<String, Object>> getBySemesterCourse(final @Context Request pRequest,
                                                       final @PathParam("semester-name") String pSemesterName,
                                                       final @PathParam("course-no") String pCourseNo) throws Exception {
    return mBinaryContentManager.list("/" + pSemesterName + "/" + pCourseNo, BinaryContentManager.Domain.COURSE_MATERIAL);
  }
}
