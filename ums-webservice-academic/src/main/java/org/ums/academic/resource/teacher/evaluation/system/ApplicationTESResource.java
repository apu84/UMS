package org.ums.academic.resource.teacher.evaluation.system;

import javax.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.teacher.evaluation.system.helper.ComparisonReport;
import org.ums.academic.resource.teacher.evaluation.system.helper.Report;
import org.ums.academic.resource.teacher.evaluation.system.helper.StudentComment;
import org.ums.domain.model.immutable.ApplicationTES;
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
  public JsonObject getAllQuestions(@Context Request pRequest) {
    return mHelper.getAllQuestions(pRequest, mUriInfo);
  }

  @GET
  @Path("/getResult/courseId/{course-id}/teacherId/{teacher-id}/semesterId/{semester-id}")
  public List<Report> getResult(@Context Request pRequest, @PathParam("course-id") String pCourseId,
      @PathParam("teacher-id") String pTeacherId, @PathParam("semester-id") Integer pSemesterId) {
    return mHelper.getResult(pCourseId, pTeacherId, pSemesterId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getComment/courseId/{course-id}/teacherId/{teacher-id}/semesterId/{semester-id}")
  public List<StudentComment> getComment(@Context Request pRequest, @PathParam("course-id") String pCourseId,
      @PathParam("teacher-id") String pTeacherId, @PathParam("semester-id") Integer pSemesterId) {
    return mHelper.getComments(pCourseId, pTeacherId, pSemesterId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getRecordsOfAssignedCoursesByHead")
  public JsonObject getRecordsOfAssignedCoursesByHead(@Context Request pRequest) {
    return mHelper.getRecordsOfAssignedCoursesByHead(pRequest, mUriInfo);
  }

  @GET
  @Path("/getAllFacultyMembers")
  public JsonObject getFacultyMembers(@Context Request pRequest) {
    return mHelper.getAllFacultyMembers(pRequest, mUriInfo);
  }

  @GET
  @Path("/getEligibleFacultyMembers/semesterId/{semester-id}/deptId/{dept-id}")
  public JsonObject getEligibleFacultyMembers(@Context Request pRequest, @PathParam("semester-id") Integer pSemesterId,
      @PathParam("dept-id") String pDeptId) {
    return mHelper.getEligibleFacultyMembers(pSemesterId, pDeptId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getAssignedCoursesForReview/teacherId/{teacher-id}/semesterId/{semester-id}")
  public JsonObject getAssignedCoursesForReview(@Context Request pRequest, @PathParam("teacher-id") String pTeacherId,
      @PathParam("semester-id") Integer pSemesterId) {
    return mHelper.getAssignedReviewableCoursesList(pTeacherId, pSemesterId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getComparisionResult/deptId/{dept-id}/semesterId/{semester-id}")
  public List<ComparisonReport> getAssignedCou(@Context Request pRequest, @PathParam("dept-id") String pDeptId,
      @PathParam("semester-id") Integer pSemesterId) {
    List<ComparisonReport> report = mHelper.getComparisonResult(pDeptId, pSemesterId, pRequest, mUriInfo);
    return report;
  }

  // getStudentSubmitDeadLine

  @GET
  @Path("/getStudentSubmitDeadLineInfo")
  public JsonObject getStudentSubmitDateInfo(@Context Request pRequest) {
    return mHelper.getStudentSubmitDateInfo(pRequest, mUriInfo);
  }

  @GET
  @Path("/getSemesterNameList")
  public JsonObject getAllSemesterNameList(@Context Request pRequest) {
    return mHelper.getAllSemesterNameList(pRequest, mUriInfo);
  }

  @GET
  @Path("/getAssignedCourses/facultyId/{faculty-id}")
  public JsonObject getAssignedCourses(@Context Request pRequest, @PathParam("faculty-id") String pFacultyId) {
    return mHelper.getAssignedCourses(pFacultyId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getReviewEligibleCourses/courseType/{course-type}")
  public JsonObject getReviewEligibleCourses(@Context Request pRequest, @PathParam("course-type") String pCourseType) {
    return mHelper.getReviewEligibleCourses(pCourseType, pRequest, mUriInfo);
  }

  @GET
  @Path("/getFacultyInfo/courseId/{course-id}/courseType/{course-type}")
  public JsonObject getfacultyInfo(@Context Request pRequest, @PathParam("course-id") String pCourseId,
      @PathParam("course-type") String pCourseType) {
    return mHelper.getFacultyInfo(pCourseId, pCourseType, pRequest, mUriInfo);
  }

  @GET
  @Path("/getAllQuestions/courseId/{course-id}/teacherId/{teacher-id}")
  public JsonObject getAlreadyReviewedCourses(@Context Request pRequest, @PathParam("course-id") String pCourseId,
      @PathParam("teacher-id") String pTeacherId) {
    return mHelper.getAlreadyReviewedCoursesInfo(pCourseId, pTeacherId, pRequest, mUriInfo);
  }

  @GET
  @Path("/getReport/courseId/{course-id}/teacherId/{teacher-id}/semesterId/{semester-id}")
  @Produces("application/pdf")
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

}
