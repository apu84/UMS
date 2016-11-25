package org.ums.manager;

import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.enums.CourseRegType;

import java.util.List;

public interface UGRegistrationResultManager extends
    ContentManager<UGRegistrationResult, MutableUGRegistrationResult, Integer> {
  List<UGRegistrationResult> getBySemesterAndExamTYpeAndGrade(int pSemesterId, int pExamType,
      String pGrade);

  List<UGRegistrationResult> getBySemesterAndExamTypeAndGradeAndStudent(int pSemesterId,
      int pExamType, String pGrade, String pStudentId);

  List<UGRegistrationResult> getImprovementCoursesBySemesterAndStudent(int pSemesterId,
      String pStudentId);

  List<UGRegistrationResult> getCarryCoursesBySemesterAndStudent(int pSemesterId, String pStudentId);

  List<UGRegistrationResult> getCarryClearanceImprovementCoursesByStudent(int pSemesterId,
      String pStudentId);

  List<UGRegistrationResult> getByCourseSemester(int semesterId, String courseId,
      CourseRegType pCourseRegType);

  List<UGRegistrationResult> getRegisteredCourseByStudent(int pSemesterId, String pStudentId,
      CourseRegType pCourseRegType);

  List<UGRegistrationResult> getRegisteredCoursesWithResult(String pStudentId) throws Exception;

  List<UGRegistrationResult> getResults(Integer pProgramId, Integer pSemesterId) throws Exception;
}
