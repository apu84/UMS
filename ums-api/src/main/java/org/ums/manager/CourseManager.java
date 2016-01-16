package org.ums.manager;

import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.regular.Course;

import java.util.List;

public interface CourseManager extends ContentManager<Course, MutableCourse, String> {
  public List<Course> getBySyllabus(final String pSyllabusId);
}
