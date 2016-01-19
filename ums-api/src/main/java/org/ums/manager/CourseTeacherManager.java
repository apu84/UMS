package org.ums.manager;

import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.regular.CourseTeacher;

import java.util.List;

public interface CourseTeacherManager extends ContentManager<CourseTeacher, MutableCourseTeacher, String> {
  List<CourseTeacher> getCourseTeachers(final String pCourseId, final String pSemesterId);

  List<CourseTeacher> getCourseTeachers(final String pCourseId, final String pSemesterId, final Integer pYear, final int pSemester);

  List<CourseTeacher> getCourseTeachers(final Integer pSemesterId, final String pSyllabusId);

  List<CourseTeacher> getCourseTeachers(final Integer pSemesterId, final String pSyllabusId, final Integer pYear, final Integer pSemester);
}
