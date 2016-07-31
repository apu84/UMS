package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.CourseTeacherResourceHelper;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.enums.CourseCategory;
import org.ums.manager.AssignedTeacherManager;
import org.ums.manager.SemesterSyllabusMapManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/academic/courseTeacher")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CourseTeacherResource extends Resource {
  @Autowired
  @Qualifier("courseTeacherManager")
  AssignedTeacherManager<CourseTeacher, MutableCourseTeacher, Integer> mCourseTeacherManager;

  @Autowired
  CourseTeacherResourceHelper mResourceHelper;

  @Autowired
  SemesterSyllabusMapManager mSemesterSyllabusMapManager;

  @GET
  @Path("/programId" + "/{program-id}" + "/semesterId" + "/{semester-id}")
  public JsonObject get(final @Context Request pRequest,
                        final @PathParam("program-id") Integer pProgramId,
                        final @PathParam("semester-id") Integer pSemesterId) throws Exception {
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, mUriInfo);
  }

  @GET
  @Path("/programId" + "/{program-id}" + "/semesterId" + "/{semester-id}" + "/year" + "/{year}")
  public JsonObject get(final @Context Request pRequest,
                        final @PathParam("program-id") Integer pProgramId,
                        final @PathParam("semester-id") Integer pSemesterId,
                        final @PathParam("year") Integer pYear) throws Exception {
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, pYear, mUriInfo);
  }

  @GET
  @Path("/programId" + "/{program-id}" + "/semesterId" + "/{semester-id}" + "/year" + "/{year}" + "/semester" + "/{semester}")
  public JsonObject get(final @Context Request pRequest,
                        final @PathParam("program-id") Integer pProgramId,
                        final @PathParam("semester-id") Integer pSemesterId,
                        final @PathParam("year") Integer pYear,
                        final @PathParam("semester") Integer pSemester) throws Exception {
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester, mUriInfo);
  }

  @GET
  @Path("/programId" + "/{program-id}" + "/semesterId" + "/{semester-id}" + "/category" + "/{category}")
  public JsonObject get(final @Context Request pRequest,
                        final @PathParam("program-id") Integer pProgramId,
                        final @PathParam("semester-id") Integer pSemesterId,
                        final @PathParam("category") String pCategory) throws Exception {

    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, CourseCategory.get(Integer.parseInt(pCategory)), mUriInfo);
  }

  @GET
  @Path("/programId" + "/{program-id}" + "/semesterId" + "/{semester-id}" + "/year" + "/{year}" + "/category" + "/{category}")
  public JsonObject get(final @Context Request pRequest,
                        final @PathParam("program-id") Integer pProgramId,
                        final @PathParam("semester-id") Integer pSemesterId,
                        final @PathParam("year") Integer pYear,
                        final @PathParam("category") String pCategory) throws Exception {
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, pYear, CourseCategory.get(Integer.parseInt(pCategory)), mUriInfo);
  }

  @GET
  @Path("/programId" + "/{program-id}" + "/semesterId" + "/{semester-id}" + "/year" + "/{year}" + "/semester" + "/{semester}" + "/category" + "/{category}")
  public JsonObject get(final @Context Request pRequest,
                        final @PathParam("program-id") Integer pProgramId,
                        final @PathParam("semester-id") Integer pSemesterId,
                        final @PathParam("year") Integer pYear,
                        final @PathParam("semester") Integer pSemester,
                        final @PathParam("category") String pCategory) throws Exception {
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester, CourseCategory.get(Integer.parseInt(pCategory)), mUriInfo);
  }

  @GET
  @Path("/programId" + "/{program-id}" + "/semesterId" + "/{semester-id}" + "/courseId" + "/{courseId}")
  public JsonObject getByCourse(final @Context Request pRequest,
                                final @PathParam("program-id") Integer pProgramId,
                                final @PathParam("semester-id") Integer pSemesterId,
                                final @PathParam("courseId") String pCourseId) throws Exception {
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, pCourseId, mUriInfo);
  }

  @GET
  @Path("/{semester-id}" + "/{teacher-id}" + "/course")
  public JsonObject getByCourse(final @Context Request pRequest,
                                final @PathParam("semester-id") Integer pSemesterId,
                                final @PathParam("teacher-id") String pTeacherId) throws Exception {
    return mResourceHelper.getAssignedCourses(pSemesterId, pTeacherId, mUriInfo);
  }

  @POST
  public Response post(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }
}
