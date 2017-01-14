package org.ums.academic.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.ClassRoomManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
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
