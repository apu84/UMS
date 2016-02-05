package org.ums.academic.dao;


import org.ums.domain.model.mutable.MutableCourseGroup;
import org.ums.domain.model.readOnly.CourseGroup;
import org.ums.manager.CourseGroupManager;

public class CourseGroupDaoDecorator extends ContentDaoDecorator<CourseGroup, MutableCourseGroup, Integer, CourseGroupManager>
    implements CourseGroupManager {
  @Override
  public CourseGroup getBySyllabus(Integer pCourseGroupId, String pSyllabusId) throws Exception{
    return getManager().getBySyllabus(pCourseGroupId, pSyllabusId);
  }
}
