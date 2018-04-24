package org.ums.academic.resource.teacher.evaluation.system;

import javax.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.teacher.evaluation.system.helper.ComparisonReport;
import org.ums.academic.resource.teacher.evaluation.system.helper.QuestionWiseReport;
import org.ums.academic.resource.teacher.evaluation.system.helper.Report;
import org.ums.academic.resource.teacher.evaluation.system.helper.StudentComment;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.logs.UmsLogMessage;
import org.ums.manager.ApplicationTESManager;
import org.ums.report.generator.teachersEvaluationReport.TesGenerator;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 2/20/2018.
 */

@Component
@Path("academic/applicationTES")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ApplicationTESResource extends MutableApplicationTESResource {
  private final Logger mLogger = LoggerFactory.getLogger(ApplicationTESResource.class);
  @Autowired
  ApplicationTESManager mApplicationTESManager;
  @Autowired
  TesGenerator mTesGeneratorManager;

  @GET
  @Path("/getAllQuestions")
  @UmsLogMessage(message = "Students Fetching Questions For Evaluation")
  public JsonObject getAllQuestions(@Context Request pRequest) {
    return mHelper.getAllQuestions(pRequest, mUriInfo);
  }

  @GET
  @Path("/getSemesterWiseQuestions/semesterId/{semester-id}")
  @UmsLogMessage(message = "Get Semester Wise Questions For Specific Semester Id")
  public JsonObject getSemesterWiseQuestions(@Context Request pRequest, @PathParam("semester-id") Integer pSemesterId) {
    return mHelper.getSemesterWiseQuestions(pSemesterId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getQuestions")
  @UmsLogMessage(message = "Get Questions For Set Evaluation,Migrate Questions")
  public JsonObject getQuestions(@Context Request pRequest) {
    return mHelper.getQuestions(pRequest, mUriInfo);
  }

  @GET
  @Path("/getDeleteEligibleQuestions")
  @UmsLogMessage(message = "Get Questions that has been already been assigned to delete before final confirmation")
  public JsonObject getDeleteEligibleQuestions(@Context Request pRequest) {
    return mHelper.getDeleteEligibleQuestions(pRequest, mUriInfo);
  }

  // getDeleteEligibleQuestions
  @GET
  @Path("/getInitialSemesterParameter")
  @UmsLogMessage(message = "Semester Initializatio")
  public JsonObject getInitialSemesterParameter(@Context Request pRequest) {
    return mHelper.getInitialSemesterParameter(pRequest, mUriInfo);
  }

  // getMigrateQuestions/semesterId/'+this.selectedSemesterId
  @GET
  @Path("/getMigrateQuestions/semesterId/{semester-id}")
  @UmsLogMessage(message = "Get Questions For Migration")
  public JsonObject getMigrationQuestions(@Context Request pRequest, @PathParam("semester-id") Integer pSemesterId) {
    return mHelper.getMigrationQuestions(pSemesterId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getQuestionWiseReport/deptId/{dept-id}/year/{year}/semester/{semester}/semesterId/{semester-id}/questionId/{question-id}")
  @UmsLogMessage(message = "Get Questions Wise Report")
  public List<QuestionWiseReport> getQuestionWiseReport(@Context Request pRequest,
      @PathParam("dept-id") String pDeptId, @PathParam("year") Integer pYear, @PathParam("semester") Integer pSemester,
      @PathParam("semester-id") Integer pSemesterId, @PathParam("question-id") Long pQuestionId) {
    return mHelper.getQuestionWiseReport(pDeptId, pYear, pSemester, pSemesterId, pQuestionId, pRequest, mUriInfo);
  }

  // getInitialSemesterParameter
  @GET
  @Path("/getResult/courseId/{course-id}/teacherId/{teacher-id}/semesterId/{semester-id}")
  @UmsLogMessage(message = "Get Individual Result For  Faculty")
  public List<Report> getResult(@Context Request pRequest, @PathParam("course-id") String pCourseId,
      @PathParam("teacher-id") String pTeacherId, @PathParam("semester-id") Integer pSemesterId) {
    return mHelper.getResult(pCourseId, pTeacherId, pSemesterId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getComment/courseId/{course-id}/teacherId/{teacher-id}/semesterId/{semester-id}")
  @UmsLogMessage(message = "Get Individual Result(Comments) For  Faculty")
  public List<StudentComment> getComment(@Context Request pRequest, @PathParam("course-id") String pCourseId,
      @PathParam("teacher-id") String pTeacherId, @PathParam("semester-id") Integer pSemesterId) {
    return mHelper.getComments(pCourseId, pTeacherId, pSemesterId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getReviewPercentage/courseId/{course-id}/teacherId/{teacher-id}/semesterId/{semester-id}")
  @UmsLogMessage(message = "Get Review percentage Individual Result  For  Faculty")
  public JsonObject getReviewPercentage(@Context Request pRequest, @PathParam("course-id") String pCourseId,
      @PathParam("teacher-id") String pTeacherId, @PathParam("semester-id") Integer pSemesterId) {
    return mHelper.getReviewPercentage(pCourseId, pTeacherId, pSemesterId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getRecordsOfAssignedCoursesByHead")
  @UmsLogMessage(message = "Get Records Of Assigned Courses By Head")
  public JsonObject getRecordsOfAssignedCoursesByHead(@Context Request pRequest) {
    return mHelper.getRecordsOfAssignedCoursesByHead(pRequest, mUriInfo);
  }

  @GET
  @Path("/getAllFacultyMembers")
  @UmsLogMessage(message = "Get All Faculty Members")
  public JsonObject getFacultyMembers(@Context Request pRequest) {
    return mHelper.getAllFacultyMembers(pRequest, mUriInfo);
  }

  @GET
  @Path("/getEligibleFacultyMembers/semesterId/{semester-id}/deptId/{dept-id}")
  @UmsLogMessage(message = "Get Eligible Faculty Members For Evaluation")
  public JsonObject getEligibleFacultyMembers(@Context Request pRequest, @PathParam("semester-id") Integer pSemesterId,
      @PathParam("dept-id") String pDeptId) {
    return mHelper.getEligibleFacultyMembers(pSemesterId, pDeptId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getAssignedCoursesForReview/teacherId/{teacher-id}/semesterId/{semester-id}")
  @UmsLogMessage(message = "Get Assigned Courses For Review")
  public JsonObject getAssignedCoursesForReview(@Context Request pRequest, @PathParam("teacher-id") String pTeacherId,
      @PathParam("semester-id") Integer pSemesterId) {
    return mHelper.getAssignedReviewableCoursesList(pTeacherId, pSemesterId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getComparisionResult/deptId/{dept-id}/semesterId/{semester-id}")
  @UmsLogMessage(message = "Get Assigned Courses For Review")
  public List<ComparisonReport> getAssignedCou(@Context Request pRequest, @PathParam("dept-id") String pDeptId,
      @PathParam("semester-id") Integer pSemesterId) {
    List<ComparisonReport> report = mHelper.getComparisonResult(pDeptId, pSemesterId, pRequest, mUriInfo);
    return report;
  }

  // getStudentSubmitDeadLine

  @GET
  @Path("/getStudentSubmitDeadLineInfo")
  @UmsLogMessage(message = "Get Student Submit DeadLine Info")
  public JsonObject getStudentSubmitDateInfo(@Context Request pRequest) {
    return mHelper.getStudentSubmitDateInfo(pRequest, mUriInfo);
  }

  @GET
  @Path("/getAssignedCourses/facultyId/{faculty-id}")
  @UmsLogMessage(message = "Get Assigned Courses For Specific Faculty")
  public JsonObject getAssignedCourses(@Context Request pRequest, @PathParam("faculty-id") String pFacultyId) {
    return mHelper.getAssignedCourses(pFacultyId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getReviewEligibleCourses/courseType/{course-type}")
  @UmsLogMessage(message = "Get Review Eligible Courses")
  public JsonObject getReviewEligibleCourses(@Context Request pRequest, @PathParam("course-type") String pCourseType) {
    return mHelper.getReviewEligibleCourses(pCourseType, pRequest, mUriInfo);
  }

  @GET
  @Path("/getFacultyInfo/courseId/{course-id}/courseType/{course-type}")
  @UmsLogMessage(message = "Get Faculty Info")
  public JsonObject getFacultyInfo(@Context Request pRequest, @PathParam("course-id") String pCourseId,
      @PathParam("course-type") String pCourseType) {
    return mHelper.getFacultyInfo(pCourseId, pCourseType, pRequest, mUriInfo);
  }

  @GET
  @Path("/getAllQuestions/courseId/{course-id}/teacherId/{teacher-id}")
  @UmsLogMessage(message = "Get Already Reviewed Courses")
  public JsonObject getAlreadyReviewedCourses(@Context Request pRequest, @PathParam("course-id") String pCourseId,
      @PathParam("teacher-id") String pTeacherId) {
    return mHelper.getAlreadyReviewedCoursesInfo(pCourseId, pTeacherId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getReport/courseId/{course-id}/teacherId/{teacher-id}/semesterId/{semester-id}")
  @Produces("application/pdf")
  @UmsLogMessage(message = "Generate Individual Evaluation Report PDF")
  public StreamingOutput createTesReport(@PathParam("course-id") String pCourseId,
      @PathParam("teacher-id") String pTeacherId, @PathParam("semester-id") Integer pSemesterId) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mTesGeneratorManager.createTesReport(pCourseId, pTeacherId, pSemesterId, output);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/getReportSuperAdmin/deptId/{dept-id}/semesterId/{semester-id}")
  @Produces("application/pdf")
  @UmsLogMessage(message = "Generate Comparison Report PDF")
  public StreamingOutput createTesReportSuperAdmin(@PathParam("dept-id") String pDeptId,
      @PathParam("semester-id") Integer pSemesterId) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mTesGeneratorManager.createTesReportSuperAdmin(pDeptId, pSemesterId, output);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/getQuestionWisePDFReport/deptId/{dept-id}/year/{year}/semester/{semester}/semesterId/{semester-id}/questionId/{question-id}")
  @Produces("application/pdf")
  @UmsLogMessage(message = "Generate Question Wise Report PDF")
  public StreamingOutput createTesQuestionReportSuperAdmin(@PathParam("dept-id") String pDeptId,
      @PathParam("year") Integer pYear, @PathParam("semester") Integer pSemester,
      @PathParam("semester-id") Integer pSemesterId, @PathParam("question-id") Long pQuestionId) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mTesGeneratorManager.getQuestionWiseReports(pDeptId, pYear, pSemester, pSemesterId, pQuestionId, output);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

}
