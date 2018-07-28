package org.ums.cache;

import org.ums.domain.model.immutable.EmpExamAttendance;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.manager.CacheManager;
import org.ums.manager.EmpExamAttendanceManager;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class EmpExamAttendanceCache extends
    ContentCache<EmpExamAttendance, MutableEmpExamAttendance, Long, EmpExamAttendanceManager> implements
    EmpExamAttendanceManager {
  CacheManager<EmpExamAttendance, Long> mCacheManager;

  public EmpExamAttendanceCache(CacheManager<EmpExamAttendance, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<EmpExamAttendance, Long> getCacheManager() {
    return mCacheManager;
  }
}
