package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.OptionalCourseApplicationResourceHelper;
import org.ums.resource.Resource;
import org.ums.response.type.GenericResponse;

import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by Ifti on 17-Mar-16.
 */
public class MutableOptionalCourseApplicationResource extends Resource {

  @Autowired
  OptionalCourseApplicationResourceHelper mResourceHelper;

  @PUT
  @Path("/settings/semester-id/{semester-id}/program/{program-id}/year/{year}/semester/{semester}")
  public Response saveApprovedAndCallForApplicationCourses(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("program-id") Integer pProgramId, final @PathParam("year") Integer pYear,
      final @PathParam("semester") Integer pSemester, final JsonObject pJsonObject)
      throws Exception {
    return mResourceHelper.saveApprovedAndApplicationCourses(pSemesterId, pProgramId, pYear,
        pSemester, pJsonObject);
  }

  @DELETE
  @Path("/semester-id/{semester-id}/program/{program-id}/course/{course-id}/section/{section-name}")
  public Response deleteSection(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("program-id") Integer pProgramId,
      final @PathParam("course-id") String pCourseId,
      final @PathParam("section-name") String pSectionName, final JsonObject pJsonObject)
      throws Exception {
    return mResourceHelper.deleteSection(pSemesterId, pProgramId, pCourseId, pSectionName);
  }

  @PUT
  @Path("/semester-id/{semester-id}/program/{program-id}/course/{course-id}/section/{section-name}")
  public Response mergeSelection(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("program-id") Integer pProgramId,
      final @PathParam("course-id") String pCourseId,
      final @PathParam("section-name") String pSectionName, final JsonObject pJsonObject)
      throws Exception {
    return mResourceHelper.mergeSelection(pSemesterId, pProgramId, pCourseId, pSectionName,
        pJsonObject);
  }

  @PUT
  @Path("/status/semester-id/{semester-id}/course/{course-id}")
  public GenericResponse<Map> updateApplicationStatusByCourse(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("course-id") String pCourseId, final JsonObject pJsonObject)
      throws Exception {
    return mResourceHelper.updateApplicationStatusByCourse(pSemesterId, pCourseId, pJsonObject);
  }

  @PUT
  @Path("status/semester-id/{semester-id}/student/{student-id}")
  public Response updateApplicationStatusByStudent(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("student-id") String pStudentId, final JsonObject pJsonObject)
      throws Exception {
    return mResourceHelper.updateApplicationStatusByStudent(pSemesterId, pStudentId, pJsonObject);
  }

  @PUT
  @Path("shift/semester-id/{semester-id}/source-course/{source-course-id}/target-course/{target-course-id}")
  public Response shiftStudents(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("source-course-id") String pSourceCourseId,
      final @PathParam("target-course-id") String pTargetCourseId, final JsonObject pJsonObject)
      throws Exception {
    return mResourceHelper
        .shiftStudents(pSemesterId, pSourceCourseId, pTargetCourseId, pJsonObject);
  }

  /*** ------------Student's Part------------ ****/
  @PUT
  @Path("student/{STATUS-ID}")
  public Response saveStudentApplication(final @Context Request pRequest,
      final @PathParam("STATUS-ID") Integer status, final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.saveStudentApplication(status, pJsonObject);
  }

}
