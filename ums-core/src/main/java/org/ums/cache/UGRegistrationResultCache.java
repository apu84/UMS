package org.ums.cache;

import java.util.List;

import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.manager.CacheManager;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.util.CacheUtil;

public class UGRegistrationResultCache extends
    ContentCache<UGRegistrationResult, MutableUGRegistrationResult, Long, UGRegistrationResultManager> implements
    UGRegistrationResultManager {

  CacheManager<UGRegistrationResult, Long> mCacheManager;

  public UGRegistrationResultCache(final CacheManager<UGRegistrationResult, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<UGRegistrationResult, Long> getCacheManager() {
    return mCacheManager;
  }

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
    String cacheKey = getCacheKey(UGRegistrationResult.class.toString(), pStudentId, pSemesterId);
    return cachedList(cacheKey, () -> getManager().getResults(pStudentId, pSemesterId));
  }

  @Override
  public List<UGRegistrationResult> getResults(Integer pProgramId, Integer pSemesterId) {
    return getManager().getResults(pProgramId, pSemesterId);
  }
}
