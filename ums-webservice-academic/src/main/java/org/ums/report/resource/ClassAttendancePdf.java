package org.ums.report.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.helper.ClassAttendanceResourceHelper;
import org.ums.resource.Resource;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Ifti on 09-Nov-16.
 */

@Component
@Path("/classAttendanceReport/pdf/")
@Produces({"application/pdf"})
public class ClassAttendancePdf extends Resource {
  @Autowired
  ClassAttendanceResourceHelper mResourceHelper;
  @Context
  ServletContext servletContext;

  private static Logger mLogger = LoggerFactory.getLogger(ClassAttendancePdf.class);

  @GET
  @Path("/semester/{semester-id}/course/{course-id}/section/{section-id}/studentCategory/{student-category}")
  @Produces("application/pdf")
  public StreamingOutput createAttendanceSheetReport(
      final @PathParam("semester-id") int pSemesterId,
      final @PathParam("course-id") String pCourseId,
      final @PathParam("section-id") String pSection,
      final @PathParam("student-category") String pStudentCategory) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        String a = servletContext.getRealPath("");
        try {
          mResourceHelper.getAttendanceSheetReport(pOutputStream, pSemesterId, pCourseId, pSection,
              pStudentCategory);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

}
