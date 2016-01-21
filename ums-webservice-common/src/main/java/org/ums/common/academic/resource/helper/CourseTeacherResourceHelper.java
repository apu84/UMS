package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.academic.model.PersistentCourseTeacher;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.regular.CourseTeacher;
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
  private List<Builder<CourseTeacher, MutableCourseTeacher>> mBuilders;

  @Override
  protected Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    modifyContent(pJsonObject);
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }

  @Override
  protected CourseTeacherManager getContentManager() {
    return mCourseTeacherManager;
  }

  @Override
  protected List<Builder<CourseTeacher, MutableCourseTeacher>> getBuilders() {
    return mBuilders;
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
                                      final Integer pYear, final Integer pSemester, final UriInfo pUriInfo) throws Exception {
    List<CourseTeacher> courseTeachers = getContentManager().getCourseTeachers(pProgramId, pSemesterId, pYear, pSemester);
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
    MutableCourseTeacher mutableCourseTeacher = new PersistentCourseTeacher();
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    for (int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      String updateType = jsonObject.getString("updateType");

      for (Builder<CourseTeacher, MutableCourseTeacher> builder : mBuilders) {
        builder.build(mutableCourseTeacher, pJsonObject, localCache);
      }

      switch (updateType) {
        case "create":
          mutableCourseTeacher.commit(false);
          break;
        case "update":
          mutableCourseTeacher.commit(true);
          break;
        case "delete":
          mutableCourseTeacher.delete();
          break;
      }
    }

  }
}
