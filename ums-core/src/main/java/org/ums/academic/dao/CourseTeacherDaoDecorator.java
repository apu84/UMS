package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.regular.CourseTeacher;
import org.ums.manager.CourseTeacherManager;

import java.util.List;

public class CourseTeacherDaoDecorator extends ContentDaoDecorator<CourseTeacher, MutableCourseTeacher, String, CourseTeacherManager> implements CourseTeacherManager {
  @Override
  public List<CourseTeacher> getCourseTeacher(String pCourseId, String pSemesterId) {
    return getManager().getCourseTeacher(pCourseId, pSemesterId);
  }

  @Override
  public List<CourseTeacher> getCourseTeacher(String pCourseId, String pSemesterId, Integer pYear, int pSemester) {
    return getManager().getCourseTeacher(pCourseId, pSemesterId, pYear, pSemester);
  }
}
