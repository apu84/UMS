package org.ums.academic.resource.exam.attendant;

import org.springframework.stereotype.Component;
import org.ums.academic.resource.exam.attendant.helper.StudentsExamAttendantData;
import org.ums.domain.model.immutable.StudentsExamAttendantInfo;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
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
  @GET
  @Path("/getExamAttendantInfo/semesterId/{semester-id}/examDate/{exam-date}/examType/{exam-type}")
  public List<StudentsExamAttendantData> getExamAttendantInfo(@Context Request pRequest,
      @PathParam("semester-id") Integer pSemesterId, @PathParam("exam-date") String pExamDate,
      @PathParam("exam-type") final Integer pExamType) {
    return mHelper.getExamAttendantInfo(pSemesterId, pExamDate, pExamType, pRequest, mUriInfo);
  }
}
