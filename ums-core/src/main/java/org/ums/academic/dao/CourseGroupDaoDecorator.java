package org.ums.academic.dao;


import org.ums.domain.model.regular.CourseGroup;
import org.ums.domain.model.mutable.MutableCourseGroup;
import org.ums.manager.CourseGroupManager;

public class CourseGroupDaoDecorator extends ContentDaoDecorator<CourseGroup, MutableCourseGroup, Integer>
    implements CourseGroupManager {
  private CourseGroupManager mManager;

  @Override
  public CourseGroupManager getManager() {
    return mManager;
  }

  @Override
  public CourseGroup getBySyllabus(Integer pCourseGroupId, String pSyllabusId) throws Exception{
    return getManager().getBySyllabus(pCourseGroupId, pSyllabusId);
  }
}
