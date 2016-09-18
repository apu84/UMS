package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.CourseResourceHelper;
import org.ums.manager.CourseManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/academic/course")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CourseResource extends MutableCourseResource {
  @Autowired
  CourseResourceHelper mResourceHelper;

  @Autowired
  CourseManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }
  @GET
  @Path("/syllabus" + PATH_PARAM_OBJECT_ID)
  public JsonObject getBySyllabus(final @Context Request pRequest, final @PathParam("object-id") String pObjectId) throws Exception {
    return mResourceHelper.getBySyllabus(pObjectId, pRequest, mUriInfo);
  }

  @GET
  @Path("/semester/{semester-id}/program/{program-id}")
  public JsonObject getBySemesterProgram(final @Context Request pRequest, final @PathParam("semester-id") String pSemesterId, final @PathParam("program-id") String pProgramId) throws Exception {
    return mResourceHelper.getBySemesterProgram(pSemesterId,pProgramId, pRequest, mUriInfo);
  }

  @GET
  @Path("/semester/{semester-id}/programType/{program-type}")
  public JsonObject getBySemesterProgramType(final @Context Request pRequest, final @PathParam("semester-id") int pSemesterId, final @PathParam("program-type") int pProgramType) throws Exception {
    return mResourceHelper.getCourses(pSemesterId,pProgramType, pRequest, mUriInfo);
  }

  @GET
  @Path("/semester/{semester-id}/program/{program-id}/year/{year}/academicSemester/{semester}")
  public JsonObject getByYearSemester(final @Context Request pRequest, final @PathParam("semester-id") String pSemesterId, final @PathParam("program-id") String pProgramId,final @PathParam("year") int year,final @PathParam("semester") int semester) throws Exception {
    return mResourceHelper.getByYearSemester(pSemesterId,pProgramId,year,semester, pRequest, mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") String pObjectId) throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }

  @GET
  @Path("/optional/semester-id/{semester-id}/program/{program-id}/year/{year}/semester/{semester}")
  public JsonObject getOptionalCourses(final @Context Request pRequest, final @PathParam("semester-id") Integer pSemesterId, final @PathParam("program-id") Integer pProgramId
      , final @PathParam("year") Integer pYear, final @PathParam("semester") Integer pSemester) throws Exception {
    return mResourceHelper.getOptionalCourses(pSemesterId, pProgramId, pYear,pSemester,pRequest, mUriInfo);
  }

  @GET
  @Path("/offered/semester-id/{semester-id}/program/{program-id}/year/{year}/semester/{semester}")
  public JsonObject getOfferedCourses(final @Context Request pRequest, final @PathParam("semester-id") Integer pSemesterId, final @PathParam("program-id") Integer pProgramId
      , final @PathParam("year") Integer pYear, final @PathParam("semester") Integer pSemester) throws Exception {
    return mResourceHelper.getOfferedCourses(pSemesterId, pProgramId, pYear,pSemester,mUriInfo);
  }

  @GET
  @Path("/call4Application/semester-id/{semester-id}/program/{program-id}/year/{year}/semester/{semester}")
  public JsonObject getCallForApplicationCourses(final @Context Request pRequest, final @PathParam("semester-id") Integer pSemesterId, final @PathParam("program-id") Integer pProgramId
      , final @PathParam("year") Integer pYear, final @PathParam("semester") Integer pSemester) throws Exception {
    return mResourceHelper.getCallForApplicationCourses(pSemesterId, pProgramId, pYear, pSemester,mUriInfo);
  }

  @GET
  @Path("/approved/semester-id/{semester-id}/program/{program-id}/year/{year}/semester/{semester}")
  public JsonObject getApprovedCourses(final @Context Request pRequest, final @PathParam("semester-id") Integer pSemesterId, final @PathParam("program-id") Integer pProgramId
      , final @PathParam("year") Integer pYear, final @PathParam("semester") Integer pSemester) throws Exception {
    return mResourceHelper.getApprovedCourses(pSemesterId, pProgramId, pYear, pSemester,mUriInfo);
  }

  @GET
  @Path("/approved-call-for-application/semester-id/{semester-id}/program/{program-id}/year/{year}/semester/{semester}")
  public JsonObject getApprovedCallForApplicationCourseList(final @Context Request pRequest, final @PathParam("semester-id") Integer pSemesterId, final @PathParam("program-id") Integer pProgramId
      , final @PathParam("year") Integer pYear, final @PathParam("semester") Integer pSemester) throws Exception {
    return mResourceHelper.getApprovedCallForApplicationCourseList(pSemesterId, pProgramId, pYear, pSemester,mUriInfo);
  }




}
