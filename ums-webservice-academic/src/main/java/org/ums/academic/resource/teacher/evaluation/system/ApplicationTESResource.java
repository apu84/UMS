package org.ums.academic.resource.teacher.evaluation.system;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.teacher.evaluation.system.helper.Report;
import org.ums.academic.resource.teacher.evaluation.system.helper.StudentComment;
import org.ums.manager.ApplicationTESManager;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
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
  @Autowired
  ApplicationTESManager mApplicationTESManager;

  @GET
  @Path("/getAllQuestions")
  public JsonObject getAllQuestions(@Context Request pRequest) {
    return mHelper.getAllQuestions(pRequest, mUriInfo);
  }

  @GET
  @Path("/getResult")
  public List<Report> getResult(@Context Request pRequest) {
    return mHelper.getResult(pRequest, mUriInfo);
  }

  @GET
  @Path("/getComment")
  public List<StudentComment> getComment(@Context Request pRequest) {
    return mHelper.getComments(pRequest, mUriInfo);
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

}
