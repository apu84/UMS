package org.ums.academic.resource.teacher.assigned;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.logs.GetLog;
import org.ums.response.type.GenericResponse;
import org.ums.usermanagement.user.User;
import org.ums.enums.CourseCategory;
import org.ums.manager.CourseTeacherManager;
import org.ums.manager.SemesterSyllabusMapManager;
import org.ums.usermanagement.user.UserManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
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
  CourseTeacherManager mCourseTeacherManager;

  @Autowired
  CourseTeacherResourceHelper mResourceHelper;

  @Autowired
  SemesterSyllabusMapManager mSemesterSyllabusMapManager;

  @Autowired
  UserManager mUserManager;

  @GET
  @Path("/programId/{program-id}/semesterId/{semester-id}")
  public JsonObject get(final @Context Request pRequest, final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester-id") Integer pSemesterId) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, user.getDepartment().getId(), mUriInfo);
  }

  @GET
  @Path("/programId/{program-id}/semesterId/{semester-id}/year/{year}")
  public JsonObject get(final @Context Request pRequest, final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("year") Integer pYear) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, pYear, user.getDepartment().getId(), mUriInfo);
  }

  @GET
  @Path("/programId/{program-id}/semesterId/{semester-id}/year/{year}/semester/{semester}")
  public JsonObject get(final @Context Request pRequest, final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("year") Integer pYear,
      final @PathParam("semester") Integer pSemester) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester, user.getDepartment().getId(),
        mUriInfo);
  }

  @GET
  @Path("/programId/{program-id}/semesterId/{semester-id}/category/{category}")
  public JsonObject get(final @Context Request pRequest, final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("category") String pCategory) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId,
        CourseCategory.get(Integer.parseInt(pCategory)), user.getDepartment().getId(), mUriInfo);
  }

  @GET
  @Path("/programId/{program-id}/semesterId/{semester-id}/year/{year}/category/{category}")
  public JsonObject get(final @Context Request pRequest, final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("year") Integer pYear,
      final @PathParam("category") String pCategory) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, pYear,
        CourseCategory.get(Integer.parseInt(pCategory)), user.getDepartment().getId(), mUriInfo);
  }

  @GET
  @Path("/programId/{program-id}/semesterId/{semester-id}/year/{year}/semester/{semester}/category/{category}")
  public JsonObject get(final @Context Request pRequest, final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("year") Integer pYear,
      final @PathParam("semester") Integer pSemester, final @PathParam("category") String pCategory) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester,
        CourseCategory.get(Integer.parseInt(pCategory)), user.getDepartment().getId(), mUriInfo);
  }

  @GET
  @Path("/programId/{program-id}/semesterId/{semester-id}/courseId/{courseId}")
  public JsonObject getByCourse(final @Context Request pRequest, final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("courseId") String pCourseId) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    return mResourceHelper.getAssignedTeachers(pProgramId, pSemesterId, pCourseId, user.getDepartment().getId(),
        mUriInfo);
  }

  @GET
  @Path("/{semester-id}/{teacher-id}/course")
  @GetLog(message = "Accessed assigned course list for a teacher of a semester")
  public JsonObject getByCourse(@Context HttpServletRequest pHttpServletRequest, final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("teacher-id") String pTeacherId) {
    return mResourceHelper.getAssignedCourses(pSemesterId, pTeacherId, mUriInfo);
  }

  @POST
  public Response post(final JsonObject pJsonObject) {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }
}
