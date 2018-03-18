package org.ums.manager;

import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.enums.CourseCategory;

import java.util.List;

public interface AssignedTeacherManager<R, M, I> extends ContentManager<R, M, I> {

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, String pOfferedBy);

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId,
      final CourseCategory pCourseCategory, String pOfferedBy);

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
      String pOfferedBy);

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
      final CourseCategory pCourseCategory, String pOfferedBy);

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
      final Integer pSemester, String pOfferedBy);

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final Integer pYear,
      final Integer pSemester, final CourseCategory pCourseCategory, String pOfferedBy);

  List<R> getAssignedTeachers(final Integer pProgramId, final Integer pSemesterId, final String pCourseId,
      String pOfferedBy);

  List<R> getAssignedCourses(final Integer pSemesterId, final String pTeacherId);

  R getAssignedCourse(final Integer pSemesterId, final String pTeacherId, final String pCourseId,
      final String pSectionId);
}
