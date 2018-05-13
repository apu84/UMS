package org.ums.decorator;

import java.util.List;
import java.util.Map;

import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.tabulation.TabulationCourseModel;

public class UGRegistrationResultDaoDecorator extends
    ContentDaoDecorator<UGRegistrationResult, MutableUGRegistrationResult, Long, UGRegistrationResultManager> implements
    UGRegistrationResultManager {

  @Override
  public List<UGRegistrationResult> getBySemesterAndExamTYpeAndGrade(int pSemesterId, int pExamType, String pGrade) {
    return getManager().getBySemesterAndExamTYpeAndGrade(pSemesterId, pExamType, pGrade);
  }

  @Override
  public List<UGRegistrationResult> getBySemesterAndExamTypeAndGradeAndStudent(int pSemesterId, int pExamType,
      String pGrade, String pStudentId) {
    return getManager().getBySemesterAndExamTypeAndGradeAndStudent(pSemesterId, pExamType, pGrade, pStudentId);
  }

  @Override
  public List<UGRegistrationResult> getImprovementCoursesBySemesterAndStudent(int pSemesterId, String pStudentId) {
    return getManager().getImprovementCoursesBySemesterAndStudent(pSemesterId, pStudentId);
  }

  @Override
  public List<UGRegistrationResult> getCarryCoursesBySemesterAndStudent(int pSemesterId, String pStudentId) {
    return getManager().getCarryCoursesBySemesterAndStudent(pSemesterId, pStudentId);
  }

  @Override
  public List<UGRegistrationResult> getCarryClearanceImprovementCoursesByStudent(int pSemesterId, String pStudentId) {
    return getManager().getCarryClearanceImprovementCoursesByStudent(pSemesterId, pStudentId);
  }

  @Override
  public List<UGRegistrationResult> getByCourseSemester(int semesterId, String courseId, CourseRegType pCourseRegType) {
    return getManager().getByCourseSemester(semesterId, courseId, pCourseRegType);
  }

  @Override
  public List<UGRegistrationResult> getRegisteredCourseByStudent(int pSemesterId, String pStudentId,
      CourseRegType pCourseRegType) {
    return getManager().getRegisteredCourseByStudent(pSemesterId, pStudentId, pCourseRegType);
  }

  @Override
  public List<UGRegistrationResult> getResults(String pStudentId, Integer pSemesterId) {
    return getManager().getResults(pStudentId, pSemesterId);
  }

  @Override
  public List<UGRegistrationResult> getResults(Integer pProgramId, Integer pSemesterId) {
    return getManager().getResults(pProgramId, pSemesterId);
  }

  @Override
  public List<UGRegistrationResult> getCCI(int pSemesterId, String pExamDate) {
    return getManager().getCCI(pSemesterId, pExamDate);
  }

  @Override
  public List<UGRegistrationResult> getResults(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester) {
    return getManager().getResults(pProgramId, pSemesterId, pYear, pSemester);
  }

  @Override
  public Map<String, TabulationCourseModel> getResultForTabulation(Integer pProgramId, Integer pSemesterId,
      Integer pYear, Integer pSemester) {
    return getManager().getResultForTabulation(pProgramId, pSemesterId, pYear, pSemester);
  }

  @Override
  public List<UGRegistrationResult> getSemesterResult(String pStudentId, Integer pSemesterId) {
    return getManager().getSemesterResult(pStudentId, pSemesterId);
  }

  @Override
  public List<UGRegistrationResult> getResultUpToSemester(String pStudentId, Integer pSemesterId, Integer pProgramTypeId) {
    return getManager().getResultUpToSemester(pStudentId, pSemesterId, pProgramTypeId);
  }
}
