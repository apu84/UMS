package org.ums.common.academic.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.academic.resource.helper.ClassAttendanceResourceHelper;
import org.ums.common.academic.resource.helper.GradeSubmissionResourceHelper;
import org.ums.enums.ExamType;
import org.ums.manager.ClassRoomManager;
import org.ums.resource.Resource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

/**
 * Created by Ifti on 25-Oct-16.
 */
@Component
@Path("/classAttendance")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ClassAttendanceResource extends MutableClassAttendanceResource {

  private static Logger mLogger = LoggerFactory.getLogger(ClassAttendanceResource.class);

  @Autowired
  ClassRoomManager mManager;

  @GET
  @Path("/semester/{semester-id}/course/{course-id}/section/{section-id}/studentCategory/{student-category}")
  public JsonObject getAttendance(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("course-id") String pCourseId,
      final @PathParam("section-id") String pSection,
      final @PathParam("student-category") String pStudentCategory) {
    return mResourceHelper.getClassAttendance(pSemesterId, pCourseId, pSection, pStudentCategory);

  }

}
