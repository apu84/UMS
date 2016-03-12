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
public class OptionalCourseApplicationResource extends Resource {

  @Autowired
  OptionalCourseApplicationResourceHelper mResourceHelper;

  @GET
  @Path("/stat/semester-id/{semester-id}/program/{program-id}")
  public JsonObject getStatistics(final @Context Request pRequest,
                                   final @PathParam("semester-id") Integer pSemesterId,
                                   final @PathParam("program-id") Integer pExamTypeId) throws Exception {
    return mResourceHelper.getApplicationStatistics(pSemesterId, pExamTypeId);
  }

  @PUT
  @Path("/settings/semester-id/{semester-id}/program/{program-id}/year/{year}/semester/{semester}")
  public Response saveApprovedAndCallForApplicationCourses(final @Context Request pRequest,
                                  final @PathParam("semester-id") Integer pSemesterId,
                                  final @PathParam("program-id") Integer pProgramId,
                                  final @PathParam("year") Integer pYear,
                                  final @PathParam("semester") Integer pSemester,
                                  final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.saveApprovedAndApplicationCourses(pSemesterId, pProgramId, pYear, pSemester, pJsonObject);
  }


  @GET
  @Path("/students/semester-id/{semester-id}/course-id/{course-id}/status/{status}")
  public JsonObject getStudentList(final @Context Request pRequest,
                                   final @PathParam("semester-id") Integer pSemesterId,
                                   final @PathParam("course-id") String pCourseId,
                                   final @PathParam("status") String pStatus) throws Exception {
    return mResourceHelper.getStudentList(pSemesterId,pCourseId,pStatus);
  }



}
