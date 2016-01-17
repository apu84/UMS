package org.ums.manager;

import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.regular.CourseTeacher;

import java.util.List;

public interface CourseTeacherManager extends ContentManager<CourseTeacher, MutableCourseTeacher, String> {
  List<CourseTeacher> getCourseTeacher(final String pCourseId, final String pSemesterId);

  List<CourseTeacher> getCourseTeacher(final String pCourseId, final String pSemesterId, final Integer pYear, final int pSemester);
}
