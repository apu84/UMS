package org.ums.cache;

import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.manager.CacheManager;

public class CourseTeacherCache
    extends AssignedTeacherCache<CourseTeacher, MutableCourseTeacher, Integer> {

  public CourseTeacherCache(final CacheManager<CourseTeacher> pCacheManager) {
    super(pCacheManager);
  }
}