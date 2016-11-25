package org.ums.decorator;

import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.manager.UGRegistrationResultManager;

import java.util.List;

public class UGRegistrationResultDaoDecorator
    extends
    ContentDaoDecorator<UGRegistrationResult, MutableUGRegistrationResult, Integer, UGRegistrationResultManager>
    implements UGRegistrationResultManager {

  @Override
  public List<UGRegistrationResult> getBySemesterAndExamTYpeAndGrade(int pSemesterId,
      int pExamType, String pGrade) {
    return getManager().getBySemesterAndExamTYpeAndGrade(pSemesterId, pExamType, pGrade);
  }

  @Override
  public List<UGRegistrationResult> getBySemesterAndExamTypeAndGradeAndStudent(int pSemesterId,
      int pExamType, String pGrade, String pStudentId) {
    return getManager().getBySemesterAndExamTypeAndGradeAndStudent(pSemesterId, pExamType, pGrade,
        pStudentId);
  }

  @Override
  public List<UGRegistrationResult> getImprovementCoursesBySemesterAndStudent(int pSemesterId,
      String pStudentId) {
    return getManager().getImprovementCoursesBySemesterAndStudent(pSemesterId, pStudentId);
  }

  @Override
  public List<UGRegistrationResult> getCarryCoursesBySemesterAndStudent(int pSemesterId,
      String pStudentId) {
    return getManager().getCarryCoursesBySemesterAndStudent(pSemesterId, pStudentId);
  }

  @Override
  public List<UGRegistrationResult> getCarryClearanceImprovementCoursesByStudent(int pSemesterId,
      String pStudentId) {
    return getManager().getCarryClearanceImprovementCoursesByStudent(pSemesterId, pStudentId);
  }

  @Override
  public List<UGRegistrationResult> getByCourseSemester(int semesterId, String courseId,
      CourseRegType pCourseRegType) {
    return getManager().getByCourseSemester(semesterId, courseId, pCourseRegType);
  }

  @Override
  public List<UGRegistrationResult> getRegisteredCourseByStudent(int pSemesterId,
      String pStudentId, CourseRegType pCourseRegType) {
    return getManager().getRegisteredCourseByStudent(pSemesterId, pStudentId, pCourseRegType);
  }

  @Override
  public List<UGRegistrationResult> getRegisteredCoursesWithResult(String pStudentId)
      throws Exception {
    return getManager().getRegisteredCoursesWithResult(pStudentId);
  }

  @Override
  public List<UGRegistrationResult> getResults(Integer pProgramId, Integer pSemesterId)
      throws Exception {
    return getManager().getResults(pProgramId, pSemesterId);
  }
}
