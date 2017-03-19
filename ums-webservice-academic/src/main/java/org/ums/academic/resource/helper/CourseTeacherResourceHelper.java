package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.builder.CourseTeacherBuilder;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.manager.AssignedTeacherManager;
import org.ums.manager.CourseTeacherManager;
import org.ums.persistent.model.PersistentCourseTeacher;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class CourseTeacherResourceHelper
    extends
    AbstractAssignedTeacherResourceHelper<CourseTeacher, MutableCourseTeacher, Long, CourseTeacherManager> {
  @Autowired
  @Qualifier("courseTeacherManager")
  CourseTeacherManager mCourseTeacherManager;

  @Autowired
  private CourseTeacherBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
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
  protected String getETag(CourseTeacher pReadonly) {
    return pReadonly.getLastModified();
  }

  protected void modifyContent(JsonObject pJsonObject) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    for(int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      String updateType = jsonObject.getString("updateType");
      MutableCourseTeacher mutableCourseTeacher = new PersistentCourseTeacher();
      getBuilder().build(mutableCourseTeacher, jsonObject, localCache);

      switch(updateType) {
        case "insert":
          mutableCourseTeacher.create();
          break;
        case "update":
          CourseTeacher courseTeacher = mCourseTeacherManager.get(mutableCourseTeacher.getId());
          MutableCourseTeacher updateCourseTeacher = courseTeacher.edit();
          updateCourseTeacher.setTeacher(mutableCourseTeacher.getTeacher());
          updateCourseTeacher.setSemester(mutableCourseTeacher.getSemester());
          updateCourseTeacher.setSection(mutableCourseTeacher.getSection());
          updateCourseTeacher.setCourse(mutableCourseTeacher.getCourse());
          updateCourseTeacher.update();
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
