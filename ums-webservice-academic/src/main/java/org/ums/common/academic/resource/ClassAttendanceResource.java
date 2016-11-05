package org.ums.common.academic.resource;

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
import java.io.StringReader;

/**
 * Created by Ifti on 25-Oct-16.
 */
@Component
@Path("/academic/classAttendance")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ClassAttendanceResource extends MutableClassAttendanceResource {

  @Autowired
  ClassRoomManager mManager;

  @GET
  @Path("semester/{semester-id}/course/{course-id}/section/{section-id}")
  public JsonObject getAttendance(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("course-id") String pCourseId, final @PathParam("section-id") String section)
      throws Exception {
    return mResourceHelper.getClassAttendance(pSemesterId, pCourseId, section);

  }

}
