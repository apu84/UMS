package org.ums.academic.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.enums.ExamType;
import org.ums.logs.UmsLogMessage;
import org.ums.manager.ClassRoomManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Ifti on 25-Oct-16.
 */
@Component
@Path("/classAttendance")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ClassAttendanceResource extends MutableClassAttendanceResource {

  @GET
  @Path("/semester/{semester-id}/course/{course-id}/section/{section-id}/studentCategory/{student-category}")
  @UmsLogMessage(message = "::: Get Attendance Information :::")
  public JsonObject getAttendance(@Context HttpServletRequest pHttpServletRequest,
      @HeaderParam("user-agent") String userAgent, final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("course-id") String pCourseId,
      final @PathParam("section-id") String pSection, final @PathParam("student-category") String pStudentCategory) {
    return mResourceHelper.getClassAttendance(pSemesterId, pCourseId, pSection, pStudentCategory);

  }

}
