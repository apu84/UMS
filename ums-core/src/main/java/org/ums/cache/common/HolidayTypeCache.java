package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.HolidayType;
import org.ums.domain.model.mutable.common.MutableHolidayType;
import org.ums.manager.CacheManager;
import org.ums.manager.common.HolidayTypeManager;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public class HolidayTypeCache extends ContentCache<HolidayType, MutableHolidayType, Long, HolidayTypeManager> implements
    HolidayTypeManager {

  private CacheManager<HolidayType, Long> mCacheManager;

  public HolidayTypeCache(CacheManager<HolidayType, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<HolidayType, Long> getCacheManager() {
    return mCacheManager;
  }
}
