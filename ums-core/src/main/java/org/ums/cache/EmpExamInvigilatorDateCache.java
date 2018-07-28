package org.ums.cache;

import org.ums.domain.model.immutable.EmpExamInvigilatorDate;
import org.ums.domain.model.mutable.MutableEmpExamInvigilatorDate;
import org.ums.manager.CacheManager;
import org.ums.manager.EmpExamInvigilatorDateManager;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class EmpExamInvigilatorDateCache extends
    ContentCache<EmpExamInvigilatorDate, MutableEmpExamInvigilatorDate, Long, EmpExamInvigilatorDateManager> implements
    EmpExamInvigilatorDateManager {
  CacheManager<EmpExamInvigilatorDate, Long> mCacheManager;

  @Override
  protected CacheManager<EmpExamInvigilatorDate, Long> getCacheManager() {
    return mCacheManager;
  }

  public EmpExamInvigilatorDateCache(CacheManager<EmpExamInvigilatorDate, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }
}
