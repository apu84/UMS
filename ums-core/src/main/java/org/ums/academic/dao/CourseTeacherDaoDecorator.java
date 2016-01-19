package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.regular.CourseTeacher;
import org.ums.manager.CourseTeacherManager;

import java.util.List;

public class CourseTeacherDaoDecorator extends ContentDaoDecorator<CourseTeacher, MutableCourseTeacher, String, CourseTeacherManager> implements CourseTeacherManager {
  @Override
  public List<CourseTeacher> getCourseTeachers(String pCourseId, String pSemesterId) {
    return getManager().getCourseTeachers(pCourseId, pSemesterId);
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(String pCourseId, String pSemesterId, Integer pYear, int pSemester) {
    return getManager().getCourseTeachers(pCourseId, pSemesterId, pYear, pSemester);
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(Integer pSemesterId, String pSyllabusId) {
    return getManager().getCourseTeachers(pSemesterId, pSyllabusId);
  }

  @Override
  public List<CourseTeacher> getCourseTeachers(Integer pSemesterId, String pSyllabusId, Integer pYear, Integer pSemester) {
    return getManager().getCourseTeachers(pSemesterId, pSyllabusId, pYear, pSemester);
  }
}
