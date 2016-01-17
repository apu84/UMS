package org.ums.academic.builder;

import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.regular.CourseTeacher;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

public class CourseTeacherBuilder implements Builder<CourseTeacher, MutableCourseTeacher> {
  @Override
  public void build(JsonObjectBuilder pBuilder, CourseTeacher pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {

  }

  @Override
  public void build(MutableCourseTeacher pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {

  }
}
