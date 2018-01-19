package org.ums.decorator;

import java.util.List;

import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.manager.CourseManager;

public class CourseDaoDecorator extends ContentDaoDecorator<Course, MutableCourse, String, CourseManager> implements
    CourseManager {

  @Override
  public List<Course> getAllForPagination(final Integer pItemPerPage, final Integer pPage, final String pOrder) {
    return getManager().getAllForPagination(pItemPerPage, pPage, pOrder);
  }

  @Override
  public List<Course> getByYearSemester(String pSemesterId, String pProgramId, int year, int semester) {
    return getManager().getByYearSemester(pSemesterId, pProgramId, year, semester);
  }

  @Override
  public List<Course> getBySyllabus(String pSyllabusId) {
    return getManager().getBySyllabus(pSyllabusId);
  }

  public List<Course> getBySemesterProgram(String pSemesterId, String pProgramId) {
    return getManager().getBySemesterProgram(pSemesterId, pProgramId);
  }

  @Override
  public List<Course> getOptionalCourseList(String pSyllabusId, Integer pYear, Integer pSemester) {
    return getManager().getOptionalCourseList(pSyllabusId, pYear, pSemester);
  }

  @Override
  public List<Course> getOfferedCourseList(Integer pSemesterId, Integer pProgramId, Integer pYear, Integer pSemester) {
    return getManager().getOfferedCourseList(pSemesterId, pProgramId, pYear, pSemester);
  }

  @Override
  public List<Course> getCallForApplicationCourseList(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester) {
    return getManager().getCallForApplicationCourseList(pSemesterId, pProgramId, pYear, pSemester);
  }

  @Override
  public List<Course> getApprovedCourseList(Integer pSemesterId, Integer pProgramId, Integer pYear, Integer pSemester) {
    return getManager().getApprovedCourseList(pSemesterId, pProgramId, pYear, pSemester);
  }

  @Override
  public List<Course> getApprovedCallForApplicationCourseList(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester) {
    return getManager().getApprovedCallForApplicationCourseList(pSemesterId, pProgramId, pYear, pSemester);
  }

  @Override
  public List<Course> getMandatoryCourses(String pSyllabusId, final Integer pYear, final Integer pSemester) {
    return getManager().getMandatoryCourses(pSyllabusId, pYear, pSemester);
  }

  @Override
  public List<Course> getMandatoryTheoryCourses(String pSyllabusId, final Integer pYear, final Integer pSemester) {
    return getManager().getMandatoryTheoryCourses(pSyllabusId, pYear, pSemester);
  }

  @Override
  public List<Course> getMandatorySesssionalCourses(String pSyllabusId, final Integer pYear, final Integer pSemester) {
    return getManager().getMandatorySesssionalCourses(pSyllabusId, pYear, pSemester);
  }

  @Override
  public Course getByCourseNo(String pCourseName, String pSyllabusId) {
    return getManager().getByCourseNo(pCourseName, pSyllabusId);
  }

  @Override
  public List<Course> getByTeacher(String pTeacherId) {
    return getManager().getByTeacher(pTeacherId);
  }

  @Override
  public Integer getOfferedToProgram(Integer pSemesterId, String pCourseId) {
    return getManager().getOfferedToProgram(pSemesterId, pCourseId);
  }

  @Override
  public List<Course> getSemesterWiseCourseList(int pProgramId, int pSemesterId, int pYear, int pAcademicSemester) {
    return getManager().getSemesterWiseCourseList(pProgramId, pSemesterId, pYear, pAcademicSemester);
  }
}
