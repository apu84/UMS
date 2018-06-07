package org.ums.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.logs.GetLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
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
  @GetLog(message = "Get grade sheet")
  public JsonObject getExamGrade(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("course-id") String pCourseId,
      final @PathParam("exam-type") Integer pExamTypeId, final @PathParam("role") String pRequestedRole) {
    return mResourceHelper.getGradeList(pRequestedRole, pSemesterId, pCourseId, ExamType.get(pExamTypeId));

  }

  @GET
  @Path("/semester/{semester-id}/examtype/{exam-type}/dept/{dept-id}/program/{program-id}/yearsemester/{year-semester}/role/{role}/status/{status}")
  @GetLog(message = "Get course list for grade submission")
  public JsonObject getGradeSubmissionStatus(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("exam-type") Integer pExamTypeId,
      final @PathParam("program-id") Integer pProgramId, final @PathParam("year-semester") Integer pYearSemester,
      final @PathParam("dept-id") String pDeptId, final @PathParam("role") String pUserRole,
      final @PathParam("status") int pStatus) {
    return mResourceHelper.getGradeSubmissionStatus(pSemesterId, pExamTypeId, pProgramId, pYearSemester, pDeptId,
        pUserRole, pStatus, null);

  }

  @GET
  @Path("/semester/{semester-id}/examtype/{exam-type}/dept/{dept-id}/program/{program-id}/yearsemester/{year-semester}/role/{role}/status/{status}/courseno/{course-no}")
  @GetLog(message = "Course List for Grade Submission(by Course Number)")
  public JsonObject getGradeSubmissionStatus(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("exam-type") Integer pExamTypeId,
      final @PathParam("program-id") Integer pProgramId, final @PathParam("year-semester") Integer pYearSemester,
      final @PathParam("dept-id") String pDeptId, final @PathParam("role") String pUserRole,
      final @PathParam("status") int pStatus, final @PathParam("course-no") String pCourseNo) {
    return mResourceHelper.getGradeSubmissionStatus(pSemesterId, pExamTypeId, pProgramId, pYearSemester, pDeptId,
        pUserRole, pStatus, pCourseNo);

  }

  @GET
  @Path("/chartdata/semester/{semester-id}/courseid/{course-id}/examtype/{exam-type}/coursetype/{course-type}")
  @GetLog(message = "Chart Data for Grade Sheet")
  public JsonObject getChartData(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("course-id") String pCourseId,
      final @PathParam("exam-type") Integer pExamTypeId, final @PathParam("course-type") Integer pCourseTypeId) {
    return mResourceHelper.getChartData(pSemesterId, pCourseId, pExamTypeId, pCourseTypeId);
  }

  @GET
  @Path("/deadline/semester/{semester-id}/examType/{exam-type}/courseType/{course-type}/examDate/{exam-date}")
  @GetLog(message = "Grade Submission Deadline")
  public JsonObject getGradeSubmissionDeadLine(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("exam-type") Integer pExamType,
      final @PathParam("course-type") int pCourseType, final @PathParam("exam-date") String pExamDate) {
    return mResourceHelper.getGradeSubmissionDeadline(pSemesterId, ExamType.get(pExamType), pExamDate,
        CourseType.get(pCourseType), mUriInfo);
  }

  @GET
  @Path("/semester/{semester-id}/courseid/{course-id}/examType/{exam-type}")
  @GetLog(message = "Marks Submission Log")
  public JsonObject getMarksSubmissionLogs(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("course-id") String pCourseId,
      final @PathParam("exam-type") Integer pExamType) {
    return mResourceHelper.getMarksSubmissionLogs(pSemesterId, pCourseId, pExamType);
  }

  @GET
  @Path("/semester/{semester-id}/courseid/{course-id}/examType/{exam-type}/studentid/{student-id}")
  @GetLog(message = "Get grade log of student")
  public JsonObject getMarksLogs(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("course-id") String pCourseId,
      final @PathParam("exam-type") Integer pExamType, final @PathParam("student-id") String pStudentId) {
    return mResourceHelper.getMarksLogs(pSemesterId, pCourseId, ExamType.get(pExamType), pStudentId);
  }

  @GET
  @Path("/submissionstat/programtype/{program-type}/semester/{semester-id}/dept/{dept-id}/examtype/{exam-type}/status/{status}")
  @GetLog(message = "Grade submission statistics")
  public JsonObject getMarksSubmissionStat(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("program-type") Integer programType, final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("dept-id") String pDeptId, final @PathParam("exam-type") Integer pExamType,
      final @PathParam("status") String pStatus) throws Exception {
    return mResourceHelper.getMarksSubmissionStat(programType, pSemesterId, pDeptId, pExamType, pStatus);
  }
}
