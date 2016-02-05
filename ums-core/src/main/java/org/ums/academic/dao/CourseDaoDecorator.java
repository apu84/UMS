package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.readOnly.Course;
import org.ums.manager.CourseManager;

import java.util.List;

public class CourseDaoDecorator extends ContentDaoDecorator<Course, MutableCourse, String, CourseManager> implements CourseManager {
  @Override
  public List<Course> getBySyllabus(String pSyllabusId) {
    return getManager().getBySyllabus(pSyllabusId);
  }
}
