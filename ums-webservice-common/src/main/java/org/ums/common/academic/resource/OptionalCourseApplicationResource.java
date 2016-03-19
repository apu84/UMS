package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.OptionalCourseApplicationResourceHelper;
import org.ums.common.academic.resource.helper.SemesterResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;


@Component
@Path("/academic/optional/application")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class OptionalCourseApplicationResource extends MutableOptionalCourseApplicationResource {


  @GET
  @Path("/stat/semester-id/{semester-id}/program/{program-id}")
  public JsonObject getStatistics(final @Context Request pRequest,
                                   final @PathParam("semester-id") Integer pSemesterId,
                                   final @PathParam("program-id") Integer pExamTypeId) throws Exception {
    return mResourceHelper.getApplicationStatistics(pSemesterId, pExamTypeId);
  }




  @GET
  @Path("/students/semester-id/{semester-id}/course-id/{course-id}/status/{status}")
  public JsonObject getStudentList(final @Context Request pRequest,
                                   final @PathParam("semester-id") Integer pSemesterId,
                                   final @PathParam("course-id") String pCourseId,
                                   final @PathParam("status") String pStatus) throws Exception {
    return mResourceHelper.getStudentList(pSemesterId,pCourseId,pStatus);
  }

  @GET
   @Path("/non-assigned-section/students/semester-id/{semester-id}/program/{program-id}/course-id/{course-id}")
   public JsonObject getNonAssignedSectionStudentList(final @Context Request pRequest,
                                                      final @PathParam("semester-id") Integer pSemesterId,
                                                      final @PathParam("program-id") Integer pProgramId,
                                                      final @PathParam("course-id") String pCourseId) throws Exception {
    return mResourceHelper.getNonAssignedSectionStudentList(pSemesterId, pProgramId, pCourseId);
  }

  @GET
  @Path("/assigned-section/students/semester-id/{semester-id}/program/{program-id}/course-id/{course-id}")
  public JsonObject getOptionalSectionListWithStudents(final @Context Request pRequest,
                                                       final @PathParam("semester-id") Integer pSemesterId,
                                                       final @PathParam("program-id") Integer pProgramId,
                                                       final @PathParam("course-id") String pCourseId) throws Exception {
    return mResourceHelper.getOptionalSectionListWithStudents(pSemesterId, pProgramId, pCourseId);
  }

  @GET
  @Path("/status/student-id/{student-id}/semester-id/{semester-id}/program/{program-id}")
  public JsonObject getApplicationStatus(final @Context Request pRequest,
                                                       final @PathParam("student-id") Integer pStudentId,
                                                       final @PathParam("semester-id") Integer pSemesterId) throws Exception {
    return mResourceHelper.getApplicationStatus(pStudentId, pSemesterId);
  }

  @GET
  @Path("/applied-courses/student-id/{student-id}/semester-id/{semester-id}/program/{program-id}")
  public JsonObject getAppliedCoursesByStudent(final @Context Request pRequest,
                                                      final @PathParam("student-id") Integer pStudentId,
                                                      final @PathParam("semester-id") Integer pSemesterId,
                                                      final @PathParam("program-id") Integer pProgramId) throws Exception {
    return mResourceHelper.getAppliedCoursesByStudent(pStudentId, pSemesterId, pProgramId);
  }


}
