package org.ums.decorator;

import org.ums.enums.CourseCategory;
import org.ums.manager.AssignedTeacherManager;

import java.util.List;


public class AssignedTeacherDaoDecorator<R, M, I, C extends AssignedTeacherManager<R, M, I>> extends ContentDaoDecorator<R, M, I, C>
    implements AssignedTeacherManager<R, M, I> {

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pOfferedBy);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester, String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester, pOfferedBy);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pOfferedBy);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, CourseCategory pCourseCategory, String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pCourseCategory, pOfferedBy);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester, CourseCategory pCourseCategory, String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester, pCourseCategory, pOfferedBy);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, CourseCategory pCourseCategory, String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pCourseCategory, pOfferedBy);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, String pCourseId, String pOfferedBy) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pCourseId, pOfferedBy);
  }

  @Override
  public List<R> getAssignedCourses(Integer pSemesterId, String pTeacherId) {
    return getManager().getAssignedCourses(pSemesterId, pTeacherId);
  }
}
