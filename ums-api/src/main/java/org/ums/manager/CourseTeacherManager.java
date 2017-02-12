package org.ums.manager;

import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.mutable.MutableCourseTeacher;

import java.util.List;

public interface CourseTeacherManager extends
    AssignedTeacherManager<CourseTeacher, MutableCourseTeacher, Long> {
  List<CourseTeacher> getAssignedSections(final Integer pSemesterId, final String pCourseId,
      final String pTeacherId);

  List<CourseTeacher> getCourseTeacher(final int pSemesterId, final String pCourseId);

  List<CourseTeacher> getCourseTeacher(final int pSemesterId);
}
