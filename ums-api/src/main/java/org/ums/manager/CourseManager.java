package org.ums.manager;

import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.mutable.MutableCourse;

import java.util.List;

public interface CourseManager extends ContentManager<Course, MutableCourse, String> {
  List<Course> getBySyllabus(final String pSyllabusId);

  List<Course> getAllForPagination(final Integer pItemPerPage, final Integer pPage, final String pOrder);

  List<Course> getBySemesterProgram(final String pSemesterId, final String pProgramId);

  List<Course> getByYearSemester(final String pSemesterId, final String pProgramId, final int year, final int semester);

  List<Course> getOptionalCourseList(final String pSyllabusId, final Integer pYear, final Integer pSemester);

  List<Course> getOfferedCourseList(final Integer pSemesterId, final Integer pProgramId, final Integer pYear,
      final Integer pSemester);

  List<Course> getCallForApplicationCourseList(final Integer pSemesterId, final Integer pProgramId,
      final Integer pYear, final Integer pSemester);

  List<Course> getApprovedCourseList(final Integer pSemesterId, final Integer pProgramId, final Integer pYear,
      final Integer pSemester);

  List<Course> getApprovedCallForApplicationCourseList(final Integer pSemesterId, final Integer pProgramId,
      final Integer pYear, final Integer pSemester);

  List<Course> getMandatoryCourses(final String pSyllabusId, final Integer pYear, final Integer pSemester);

  List<Course> getMandatoryTheoryCourses(final String pSyllabusId, final Integer pYear, final Integer pSemester);

  List<Course> getMandatorySesssionalCourses(final String pSyllabusId, final Integer pYear, final Integer pSemester);

  Course getByCourseNo(final String pCourseName, final String pSyllabusId);

  public List<Course> getByTeacher(final String pTeacherId);

  Integer getOfferedToProgram(final Integer pSemesterId, final String pCourseId);

}
