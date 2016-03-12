package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.model.PersistentCourseTeacher;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.builder.CourseTeacherBuilder;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.readOnly.CourseTeacher;
import org.ums.enums.CourseCategory;
import org.ums.manager.CourseTeacherManager;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class CourseTeacherResourceHelper extends ResourceHelper<CourseTeacher, MutableCourseTeacher, String> {
  @Autowired
  CourseTeacherManager mCourseTeacherManager;

  @Autowired
  private CourseTeacherBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    modifyContent(pJsonObject);
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }

  @Override
  protected CourseTeacherManager getContentManager() {
    return mCourseTeacherManager;
  }

  @Override
  protected CourseTeacherBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(CourseTeacher pReadonly) {
    return pReadonly.getLastModified();
  }

  public JsonObject getCourseTeachers(final Integer pProgramId, final Integer pSemesterId, final UriInfo pUriInfo) throws Exception {
    List<CourseTeacher> courseTeachers = getContentManager().getCourseTeachers(pProgramId, pSemesterId);
    return buildJsonResponse(courseTeachers, pUriInfo);
  }

  public JsonObject getCourseTeachers(final Integer pProgramId, final Integer pSemesterId,
                                      final Integer pYear, final UriInfo pUriInfo) throws Exception {
    List<CourseTeacher> courseTeachers = getContentManager().getCourseTeachers(pProgramId, pSemesterId, pYear);
    return buildJsonResponse(courseTeachers, pUriInfo);
  }

  public JsonObject getCourseTeachers(final Integer pProgramId, final Integer pSemesterId,
                                      final Integer pYear, final Integer pSemester, final UriInfo pUriInfo) throws Exception {
    List<CourseTeacher> courseTeachers = getContentManager().getCourseTeachers(pProgramId, pSemesterId, pYear, pSemester);
    return buildJsonResponse(courseTeachers, pUriInfo);
  }

  public JsonObject getCourseTeachers(final Integer pProgramId, final Integer pSemesterId,
                                      final CourseCategory pCourseCategory, final UriInfo pUriInfo) throws Exception {
    List<CourseTeacher> courseTeachers = getContentManager().getCourseTeachers(pProgramId, pSemesterId, pCourseCategory);
    return buildJsonResponse(courseTeachers, pUriInfo);
  }

  public JsonObject getCourseTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
                                      final CourseCategory pCourseCategory, final UriInfo pUriInfo) throws Exception {
    List<CourseTeacher> courseTeachers = getContentManager().getCourseTeachers(pProgramId, pSemesterId, pYear, pCourseCategory);
    return buildJsonResponse(courseTeachers, pUriInfo);
  }

  public JsonObject getCourseTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
                                      final Integer pSemester, final CourseCategory pCourseCategory,
                                      final UriInfo pUriInfo) throws Exception {
    List<CourseTeacher> courseTeachers = getContentManager().getCourseTeachers(pProgramId, pSemesterId, pYear, pSemester, pCourseCategory);
    return buildJsonResponse(courseTeachers, pUriInfo);
  }

  protected JsonObject buildJsonResponse(final List<CourseTeacher> pCourseTeachers, final UriInfo pUriInfo) throws Exception {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (CourseTeacher courseTeacher : pCourseTeachers) {
      children.add(toJson(courseTeacher, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  protected void modifyContent(JsonObject pJsonObject) throws Exception {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    for (int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      String updateType = jsonObject.getString("updateType");
      MutableCourseTeacher mutableCourseTeacher = new PersistentCourseTeacher();
      getBuilder().build(mutableCourseTeacher, jsonObject, localCache);

      switch (updateType) {
        case "insert":
          mutableCourseTeacher.commit(false);
          break;
        case "update":
          CourseTeacher courseTeacher = mCourseTeacherManager.get(mutableCourseTeacher.getId());
          MutableCourseTeacher updateCourseTeacher = courseTeacher.edit();
          updateCourseTeacher.setTeacher(mutableCourseTeacher.getTeacher());
          updateCourseTeacher.setSemester(mutableCourseTeacher.getSemester());
          updateCourseTeacher.setSection(mutableCourseTeacher.getSection());
          updateCourseTeacher.setCourse(mutableCourseTeacher.getCourse());
          updateCourseTeacher.commit(true);
          break;
        case "delete":
          CourseTeacher teacher = mCourseTeacherManager.get(mutableCourseTeacher.getId());
          MutableCourseTeacher deleteCourseTeacher = teacher.edit();
          deleteCourseTeacher.delete();
          break;
      }
    }

  }
}
