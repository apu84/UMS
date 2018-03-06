package org.ums.cache;

import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.manager.ApplicationCCIManager;
import org.ums.manager.ApplicationTESManager;
import org.ums.manager.CacheManager;

import java.util.List;

/**
 * Created by rumi on 2/20/2018.
 */
public class ApplicationTESCache extends
    ContentCache<ApplicationTES, MutableApplicationTES, Long, ApplicationTESManager> implements ApplicationTESManager {
  @Override
  public List<ApplicationTES> getAssignedCourses(String pFacultyId, Integer pSemesterId) {
    return getManager().getAssignedCourses(pFacultyId, pSemesterId);
  }

  @Override
  public List<ApplicationTES> getFacultyMembers(String pDeptId) {
    return getManager().getFacultyMembers(pDeptId);
  }

  @Override
  public List<ApplicationTES> getAlreadyReviewdCourses(String pStudentId, Integer pSemesterId) {
    return getManager().getAlreadyReviewdCourses(pStudentId, pSemesterId);
  }

  CacheManager<ApplicationTES, Long> mCacheManager;

  @Override
  public List<ApplicationTES> getTeachersInfo(String pCourseId, Integer pSemesterId, String pSection) {
    return getManager().getTeachersInfo(pCourseId, pSemesterId, pSection);
  }

  @Override
  public List<ApplicationTES> getAllQuestions(Integer pSemesterId) {
    return getManager().getAllQuestions(pSemesterId);
  }

  @Override
  public List<ApplicationTES> getReviewEligibleCourses(String pStudentId, Integer pSemesterId, String pCourseType) {
    return getManager().getReviewEligibleCourses(pStudentId, pSemesterId, pCourseType);
  }

  @Override
  public String getSemesterName(Integer pCurrentSemester) {
    return getManager().getSemesterName(pCurrentSemester);
  }

  @Override
  protected CacheManager<ApplicationTES, Long> getCacheManager() {
    return mCacheManager;
  }

  public ApplicationTESCache(CacheManager<ApplicationTES, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }
}
