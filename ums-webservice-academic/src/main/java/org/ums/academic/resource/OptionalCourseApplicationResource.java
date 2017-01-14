package org.ums.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("/academic/optional/application")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class OptionalCourseApplicationResource extends MutableOptionalCourseApplicationResource {

  @GET
  @Path("/stat/semester-id/{semester-id}/program/{program-id}")
  public JsonObject getStatistics(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("program-id") Integer pProgramId) {
    return mResourceHelper.getApplicationStatistics(pSemesterId, pProgramId);
  }

  @GET
  @Path("/CrHr/semester-id/{semester-id}/program/{program-id}/year/{year}/semester/{semester}")
  public JsonObject getCrHrInfo(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester") Integer pSemester, final @PathParam("year") Integer pYear) {
    return mResourceHelper.getSemesterWiseCrHrInfo(pSemesterId, pProgramId, pYear, pSemester);
  }

  @GET
  @Path("/students/semester-id/{semester-id}/course/{course-id}/status/{status}")
  public JsonObject getStudentList(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("course-id") String pCourseId, final @PathParam("status") String pStatus) {
    return mResourceHelper.getStudentList(pSemesterId, pCourseId, pStatus);
  }

  @GET
  @Path("/non-assigned-section/students/semester-id/{semester-id}/program/{program-id}/course/{course-id}")
  public JsonObject getNonAssignedSectionStudentList(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("program-id") Integer pProgramId,
      final @PathParam("course-id") String pCourseId) {
    return mResourceHelper.getNonAssignedSectionStudentList(pSemesterId, pProgramId, pCourseId);
  }

  @GET
  @Path("/assigned-section/students/semester-id/{semester-id}/program/{program-id}/course/{course-id}")
  public JsonObject getOptionalSectionListWithStudents(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("program-id") Integer pProgramId,
      final @PathParam("course-id") String pCourseId) {
    return mResourceHelper.getOptionalSectionListWithStudents(pSemesterId, pProgramId, pCourseId);
  }

  @GET
  @Path("/status/student-id/{student-id}/semester-id/{semester-id}/program/{program-id}")
  public JsonObject getApplicationStatus(final @Context Request pRequest,
      final @PathParam("student-id") String pStudentId,
      final @PathParam("semester-id") Integer pSemesterId) {
    return mResourceHelper.getApplicationStatus(pStudentId, pSemesterId);
  }

  @GET
  @Path("/applied-courses/student-id/{student-id}/semester-id/{semester-id}/program/{program-id}")
  public JsonObject getAppliedCoursesByStudent(final @Context Request pRequest,
      final @PathParam("student-id") String pStudentId,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("program-id") Integer pProgramId) {
    return mResourceHelper.getAppliedCoursesByStudent(pStudentId, pSemesterId, pProgramId);
  }

  /*** ------------Student's Part------------ ****/

  @GET
  @Path("/student")
  public JsonObject getDataForStudent(final @Context Request pRequest) {
    return mResourceHelper.getDataForStudent(pRequest, mUriInfo);
  }

}
