package org.ums.cache;

import org.ums.domain.model.immutable.SemesterWithdrawalLog;
import org.ums.domain.model.mutable.MutableSemesterWithdrawalLog;
import org.ums.manager.CacheManager;
import org.ums.manager.SemesterWithdrawalLogManager;
import org.ums.util.CacheUtil;

public class SemesterWithdrawalLogCache extends
    ContentCache<SemesterWithdrawalLog, MutableSemesterWithdrawalLog, Long, SemesterWithdrawalLogManager> implements
    SemesterWithdrawalLogManager {

  CacheManager<SemesterWithdrawalLog, Long> mCacheManager;

  public SemesterWithdrawalLogCache(CacheManager<SemesterWithdrawalLog, Long> pCachemanager) {
    mCacheManager = pCachemanager;
  }

  @Override
  public SemesterWithdrawalLog getBySemesterWithdrawalId(int pSemesterWithdrawalId) {
    return getManager().getBySemesterWithdrawalId(pSemesterWithdrawalId);
  }

  @Override
  protected CacheManager<SemesterWithdrawalLog, Long> getCacheManager() {
    return mCacheManager;
  }
}
