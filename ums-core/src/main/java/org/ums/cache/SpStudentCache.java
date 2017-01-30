package org.ums.cache;

import org.ums.domain.model.immutable.SpStudent;
import org.ums.domain.model.mutable.MutableSpStudent;
import org.ums.manager.CacheManager;
import org.ums.manager.SpStudentManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by My Pc on 4/27/2016.
 */
public class SpStudentCache extends
    ContentCache<SpStudent, MutableSpStudent, String, SpStudentManager> implements SpStudentManager {
  CacheManager<SpStudent, String> mCacheManager;

  public SpStudentCache(CacheManager<SpStudent, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<SpStudent, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<SpStudent> getStudentByProgramYearSemesterStatus(int pProgramId, int pYear,
      int pSemester, int pStatus) {
    return getManager()
        .getStudentByProgramYearSemesterStatus(pProgramId, pYear, pSemester, pStatus);
  }

  @Override
  public List<SpStudent> getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(String pCourseId,
      Integer pSemesterId) {
    return getManager().getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(pCourseId, pSemesterId);
  }

  @Override
  public List<SpStudent> getStudentBySemesterIdAndExamDateForCCI(Integer pSemesterId,
      String pExamDate) {
    return getManager().getStudentBySemesterIdAndExamDateForCCI(pSemesterId, pExamDate);
  }
}
