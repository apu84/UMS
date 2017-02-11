package org.ums.report.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.helper.ClassAttendanceResourceHelper;
import org.ums.academic.resource.helper.ExamRoutineResourceHelper;
import org.ums.resource.Resource;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Ifti on 07-Feb-17.
 */
@Component
@Path("/ugExamRoutineReport/pdf/")
@Produces({"application/pdf"})
public class UGExamRoutinePdf extends Resource {
  @Autowired
  ExamRoutineResourceHelper mResourceHelper;
  @Context
  ServletContext servletContext;

  private static Logger mLogger = LoggerFactory.getLogger(UGExamRoutinePdf.class);

  @GET
  @Path("/semester/{semester-id}/examType/{exam-type}")
  @Produces("application/pdf")
  public StreamingOutput createAttendanceSheetReport(
      final @PathParam("semester-id") int pSemesterId,
      final @PathParam("exam-type") Integer examType) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        String a = servletContext.getRealPath("");
        try {
          mResourceHelper.getExamRoutineReport(pOutputStream, pSemesterId, examType);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

}
