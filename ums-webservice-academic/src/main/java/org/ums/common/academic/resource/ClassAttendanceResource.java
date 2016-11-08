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
@Path("/academic/classAttendance")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ClassAttendanceResource extends MutableClassAttendanceResource {

  private static Logger mLogger = LoggerFactory.getLogger(ClassAttendanceResource.class);

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

  @GET
  @Path("classAttendanceReport/semester/{semester-id}/course/{course-id}/section/{section-id}")
  @Produces("application/pdf")
  public StreamingOutput createAttendanceSheetReport(
      final @PathParam("semester-id") int pSemesterId,
      final @PathParam("course-id") String pCourseId, final @PathParam("section") String pSection)
      throws Exception {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mResourceHelper.getAttendanceSheetReport(pOutputStream, pSemesterId, pCourseId, pSection);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
