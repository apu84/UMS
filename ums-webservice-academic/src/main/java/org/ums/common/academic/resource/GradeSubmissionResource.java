package org.ums.common.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by ikh on 4/29/2016.
 */
@Component
@Path("/academic/gradeSubmission")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class GradeSubmissionResource extends MutableGradeSubmissionResource {

    @GET
    @Path("/semester/{semester-id}/courseid/{course-id}/examtype/{exam-type}/role/{role}")
    public JsonObject getExamGrade(final @Context Request pRequest,
                                     final @PathParam("semester-id") Integer pSemesterId,
                                     final @PathParam("course-id") String pCourseId,
                                     final @PathParam("exam-type") Integer pExamTypeId,
                                     final @PathParam("role") String pRequestedRole) throws Exception {
        return mResourceHelper.getGradeList(pRequestedRole,pSemesterId,pCourseId,pExamTypeId);

    }

    @GET
    @Path("/semester/{semester-id}/examtype/{exam-type}/dept/{dept-id}/program/{program-id}/yearsemester/{year-semester}/role/{role}/status/{status}")
    public JsonObject getGradeSubmissionStatus(final @Context Request pRequest,
                                   final @PathParam("semester-id") Integer pSemesterId,
                                   final @PathParam("exam-type") Integer pExamTypeId,
                                   final @PathParam("program-id") Integer pProgramId,
                                   final @PathParam("year-semester") Integer pYearSemester,
                                   final @PathParam("dept-id") String pDeptId,
                                   final @PathParam("role") String pUserRole,
                                   final @PathParam("status") int pStatus) throws Exception {
        return mResourceHelper.getGradeSubmissionStatus( pSemesterId, pExamTypeId,pProgramId,pYearSemester,pDeptId,pUserRole,pStatus);

    }

  @GET
  @Path("/chartdata/semester/{semester-id}/courseid/{course-id}/examtype/{exam-type}/coursetype/{course-type}")
  public JsonObject getChartData(final @Context Request pRequest,
                                             final @PathParam("semester-id") Integer pSemesterId,
                                             final @PathParam("course-id") String pCourseId,
                                             final @PathParam("exam-type") Integer pExamTypeId,
                                             final @PathParam("course-type") Integer pCourseTypeId) throws Exception {
    return mResourceHelper.getChartData(pSemesterId, pCourseId, pExamTypeId,pCourseTypeId);
  }


  @GET
  @Path("/deadline/semester/{semester-id}/examType/{exam-type}/examDate/{exam-date}")
  public JsonObject getGradeSubmissionDeadLine(final @Context Request pRequest,
                                               final @PathParam("semester-id") Integer pSemesterId,
                                               final @PathParam("exam-type") Integer pExamType,
                                               final @PathParam("exam-date") String pExamDate) throws Exception{
    return mResourceHelper.getGradeSubmissionDeadline(pSemesterId,pExamType,pExamDate,mUriInfo);
  }

  @GET
  @Path("/semester/{semester-id}/courseid/{course-id}/examType/{exam-type}")
  public JsonObject getMarksSubmissionLogs(final @Context Request pRequest,
                                               final @PathParam("semester-id") Integer pSemesterId,
                                               final @PathParam("course-id") String pCourseId,
                                               final @PathParam("exam-type") Integer pExamType) throws Exception{
    return mResourceHelper.getMarksSubmissionLogs(pSemesterId,pCourseId,pExamType);
  }

  @GET
  @Path("/semester/{semester-id}/courseid/{course-id}/examType/{exam-type}/studentid/{student-id}")
  public JsonObject getMarksLogs(final @Context Request pRequest,
                                           final @PathParam("semester-id") Integer pSemesterId,
                                           final @PathParam("course-id") String pCourseId,
                                           final @PathParam("exam-type") Integer pExamType,
                                           final @PathParam("student-id") String pStudentId) throws Exception{
    return mResourceHelper.getMarksLogs(pSemesterId, pCourseId, pExamType, pStudentId);
  }
}
