package org.ums.manager;


import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.enums.CourseCategory;

import java.util.List;

public interface AssignedTeacherManager<R, M, I> extends ContentManager<R, M, I> {

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId);

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId,
                              final CourseCategory pCourseCategory);

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear);

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
                              final CourseCategory pCourseCategory);

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
                              final Integer pSemester);

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
                              final Integer pSemester, final CourseCategory pCourseCategory);

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final String pCourseId);

  List<R> getAssignedCourses(final Integer pSemesterId, final String pTeacherId);
}
