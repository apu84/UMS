package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.readOnly.Course;
import org.ums.manager.CourseManager;

import java.util.List;

public class CourseDaoDecorator extends ContentDaoDecorator<Course, MutableCourse, String, CourseManager> implements CourseManager {
  @Override
  public List<Course> getBySyllabus(String pSyllabusId) {
    return getManager().getBySyllabus(pSyllabusId);
  }
  public List<Course> getBySemesterProgram(String pSemesterId,String pProgramId) {
    return getManager().getBySemesterProgram(pSemesterId,pProgramId);
  }

  @Override
  public List<Course> getOptionalCourseList(String pSyllabusId, Integer pYear, Integer pSemester) {
    return getManager().getOptionalCourseList(pSyllabusId, pYear, pSemester);
  }

  @Override
  public List<Course> getOfferedCourseList(Integer pSemesterId,Integer pProgramId, Integer pYear, Integer pSemester) {
    return getManager().getOfferedCourseList(pSemesterId,pProgramId, pYear, pSemester);
  }


  @Override
  public List<Course> getCallForApplicationCourseList(Integer pSemesterId,Integer pProgramId, Integer pYear, Integer pSemester) {
    return getManager().getCallForApplicationCourseList(pSemesterId,pProgramId, pYear, pSemester);
  }


  @Override
  public List<Course> getApprovedCourseList(Integer pSemesterId,Integer pProgramId,Integer pYear, Integer pSemester) {
    return getManager().getApprovedCourseList(pSemesterId, pProgramId,pYear, pSemester);
  }

}
