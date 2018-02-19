package org.ums.cache.accounts;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.accounts.Month;
import org.ums.domain.model.mutable.accounts.MutableMonth;
import org.ums.manager.CacheManager;
import org.ums.manager.accounts.MonthManager;

/**
 * Created by Monjur-E-Morshed on 11-Jan-18.
 */
public class MonthCache extends ContentCache<Month, MutableMonth, Long, MonthManager> implements MonthManager {

  CacheManager<Month, Long> mMonthLongCacheManager;

  public MonthCache(CacheManager<Month, Long> pMonthLongCacheManager) {
    mMonthLongCacheManager = pMonthLongCacheManager;
  }

  @Override
  protected CacheManager<Month, Long> getCacheManager() {
    return mMonthLongCacheManager;
  }
}
