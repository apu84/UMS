package org.ums.manager;

import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.mutable.MutableCourse;

import java.util.List;

public interface CourseManager extends ContentManager<Course, MutableCourse, String> {
  public List<Course> getBySyllabus(final String pSyllabusId);

  public List<Course> getBySemesterProgram(final String pSemesterId, final String pProgramId);

  public List<Course> getByYearSemester(final String pSemesterId, final String pProgramId,
      final int year, final int semester);

  public List<Course> getOptionalCourseList(final String pSyllabusId, final Integer pYear,
      final Integer pSemester);

  public List<Course> getOfferedCourseList(final Integer pSemesterId, final Integer pProgramId,
      final Integer pYear, final Integer pSemester);

  public List<Course> getCallForApplicationCourseList(final Integer pSemesterId,
      final Integer pProgramId, final Integer pYear, final Integer pSemester);

  public List<Course> getApprovedCourseList(final Integer pSemesterId, final Integer pProgramId,
      final Integer pYear, final Integer pSemester);

  public List<Course> getApprovedCallForApplicationCourseList(final Integer pSemesterId,
      final Integer pProgramId, final Integer pYear, final Integer pSemester);

  public List<Course> getMandatoryCourses(final String pSyllabusId, final Integer pYear,
      final Integer pSemester);

  public List<Course> getMandatoryTheoryCourses(final String pSyllabusId, final Integer pYear,
      final Integer pSemester);

  public List<Course> getMandatorySesssionalCourses(final String pSyllabusId, final Integer pYear,
      final Integer pSemester);

  Course getByCourseNo(final String pCourseName, final String pSyllabusId);

}
