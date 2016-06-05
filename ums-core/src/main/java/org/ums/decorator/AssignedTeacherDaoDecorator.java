package org.ums.decorator;

import org.ums.enums.CourseCategory;
import org.ums.manager.AssignedTeacherManager;

import java.util.List;


public class AssignedTeacherDaoDecorator<R, M, I, C extends AssignedTeacherManager<R, M, I>> extends ContentDaoDecorator<R, M, I, C>
    implements AssignedTeacherManager<R, M, I> {

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, CourseCategory pCourseCategory) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pCourseCategory);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester, CourseCategory pCourseCategory) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pYear, pSemester, pCourseCategory);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, CourseCategory pCourseCategory) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pCourseCategory);
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, String pCourseId) {
    return getManager().getAssignedTeachers(pProgramId, pSemesterId, pCourseId);
  }
}
