package org.ums.academic.resource.teacher.assigned;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Editable;
import org.ums.enums.CourseCategory;
import org.ums.manager.AssignedTeacherManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public abstract class AbstractAssignedTeacherResourceHelper<R extends EditType<M>, M extends Editable<I>, I, C extends AssignedTeacherManager<R, M, I>>
    extends ResourceHelper<R, M, I> {

  protected abstract void modifyContent(JsonObject pJsonObject);

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    modifyContent(pJsonObject);
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final String pOfferedBy,
      final UriInfo pUriInfo) {

    List<R> assignedTeachers = getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pOfferedBy);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
      final String pOfferedBy, final UriInfo pUriInfo) {
    List<R> assignedTeachers = getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pOfferedBy);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
      final Integer pSemester, final String pOfferedBy, final UriInfo pUriInfo) {
    List<R> assignedTeachers =
        getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester, pOfferedBy);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId,
      final CourseCategory pCourseCategory, final String pOfferedBy, final UriInfo pUriInfo) {
    List<R> assignedTeachers =
        getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pCourseCategory, pOfferedBy);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final String pCourseId,
      final String pOfferedBy, final UriInfo pUriInfo) {
    List<R> assignedTeachers = getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pCourseId, pOfferedBy);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
      final CourseCategory pCourseCategory, final String pOfferedBy, final UriInfo pUriInfo) {
    List<R> assignedTeachers =
        getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pCourseCategory, pOfferedBy);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
      final Integer pSemester, final CourseCategory pCourseCategory, final String pOfferedBy, final UriInfo pUriInfo) {
    List<R> assignedTeachers =
        getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester, pCourseCategory, pOfferedBy);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedCourses(final Integer pSemesterId, final String pTeacherId, final UriInfo pUriInfo) {
    List<R> assignedTeachers = getContentManager().getAssignedCourses(pSemesterId, pTeacherId);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  @Override
  protected abstract C getContentManager();
}
