package org.ums.academic.resource.exam.attendant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.exam.attendant.helper.StudentsExamAttendantData;
import org.ums.domain.model.immutable.StudentsExamAttendantInfo;
import org.ums.report.generator.examAttendance.ExamAttendanceGenerator;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 6/9/2018.
 */
@Component
@Path("/academic/studentsExamAttendantInfo")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class StudentsExamAttendantInfoResource extends MutableStudentsExamAttendantInfoResource {
  @Autowired
  ExamAttendanceGenerator mExamAttendanceGenerator;

  @GET
  @Path("/getExamAttendantInfo/semesterId/{semester-id}/examDate/{exam-date}/examType/{exam-type}")
  public List<StudentsExamAttendantData> getExamAttendantInfo(@Context Request pRequest,
      @PathParam("semester-id") Integer pSemesterId, @PathParam("exam-date") String pExamDate,
      @PathParam("exam-type") final Integer pExamType) {
    return mHelper.getExamAttendantInfo(pSemesterId, pExamDate, pExamType, pRequest, mUriInfo);
  }

  @GET
  @Path("/getReport/semesterId/{semester-id}/examType/{exam-type}/examDate/{exam-date}")
  @Produces("application/pdf")
  public StreamingOutput createTesReport(@PathParam("semester-id") Integer pSemesterId,
      @PathParam("exam-type") Integer pExamType, @PathParam("exam-date") String pExamDate) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mExamAttendanceGenerator.createTestimonial(pSemesterId, pExamType, pExamDate, output);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }

}
