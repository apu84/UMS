package org.ums.common.academic.resource.helper;


import org.ums.cache.LocalCache;
import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Mutable;
import org.ums.enums.CourseCategory;
import org.ums.manager.AssignedTeacherManager;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public abstract class AbstractAssignedTeacherResourceHelper<R extends EditType<M>, M extends Mutable, I, C extends AssignedTeacherManager<R, M, I>>
    extends ResourceHelper<R, M, I> {

  protected abstract void modifyContent(JsonObject pJsonObject) throws Exception;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    modifyContent(pJsonObject);
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final UriInfo pUriInfo) throws Exception {
    List<R> assignedTeachers = getContentManager().getAssignedTeachers(pProgramId, pSemesterId);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId,
                                        final Integer pYear, final UriInfo pUriInfo) throws Exception {
    List<R> assignedTeachers = getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pYear);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId,
                                        final Integer pYear, final Integer pSemester, final UriInfo pUriInfo) throws Exception {
    List<R> assignedTeachers = getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId,
                                        final CourseCategory pCourseCategory, final UriInfo pUriInfo) throws Exception {
    List<R> assignedTeachers = getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pCourseCategory);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId,
                                        final String pCourseId, final UriInfo pUriInfo) throws Exception {
    List<R> assignedTeachers = getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pCourseId);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
                                        final CourseCategory pCourseCategory, final UriInfo pUriInfo) throws Exception {
    List<R> assignedTeachers = getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pCourseCategory);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
                                        final Integer pSemester, final CourseCategory pCourseCategory,
                                        final UriInfo pUriInfo) throws Exception {
    List<R> assignedTeachers = getContentManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester, pCourseCategory);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  public JsonObject getAssignedCourses(final Integer pSemesterId, final String pTeacherId,
                                       final UriInfo pUriInfo) throws Exception {
    List<R> assignedTeachers = getContentManager().getAssignedCourses(pSemesterId, pTeacherId);
    return buildJsonResponse(assignedTeachers, pUriInfo);
  }

  protected JsonObject buildJsonResponse(final List<R> pAssignedTeachers, final UriInfo pUriInfo) throws Exception {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (R assignedTeacher : pAssignedTeachers) {
      children.add(toJson(assignedTeacher, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  @Override
  protected abstract C getContentManager();
}
