package org.ums.manager;


import org.ums.domain.model.CourseGroup;
import org.ums.domain.model.MutableCourseGroup;

public interface CourseGroupManager extends ContentManager<CourseGroup, MutableCourseGroup, Integer> {
  CourseGroup getBySyllabus(final Integer pCourseGroupId, final String pSyllabusId) throws Exception;
}
