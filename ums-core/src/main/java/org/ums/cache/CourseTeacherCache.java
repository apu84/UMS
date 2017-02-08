package org.ums.cache;

import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.manager.CacheManager;
import org.ums.manager.CourseTeacherManager;

import java.util.List;

public class CourseTeacherCache extends
    AssignedTeacherCache<CourseTeacher, MutableCourseTeacher, Integer, CourseTeacherManager>
    implements CourseTeacherManager {

  public CourseTeacherCache(final CacheManager<CourseTeacher, Integer> pCacheManager) {
    super(pCacheManager);
  }

  @Override
  public List<CourseTeacher> getAssignedSections(Integer pSemesterId, String pCourseId,
      String pTeacherId) {
    return getManager().getAssignedSections(pSemesterId, pCourseId, pTeacherId);
  }

  @Override
  public List<CourseTeacher> getCourseTeacher(int pSemesterId, String pCourseId) {
    return getManager().getCourseTeacher(pSemesterId, pCourseId);
  }
}
