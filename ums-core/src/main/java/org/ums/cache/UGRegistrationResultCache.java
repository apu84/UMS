package org.ums.cache;

import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.manager.CacheManager;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by My Pc on 7/12/2016.
 */
public class UGRegistrationResultCache
    extends
    ContentCache<UGRegistrationResult, MutableUGRegistrationResult, Integer, UGRegistrationResultManager>
    implements UGRegistrationResultManager {

  CacheManager<UGRegistrationResult, Integer> mCacheManager;

  public UGRegistrationResultCache(final CacheManager<UGRegistrationResult, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<UGRegistrationResult, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(UGRegistrationResult.class, pId);
  }

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
}
