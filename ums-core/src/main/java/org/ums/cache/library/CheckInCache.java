package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.library.CheckIn;
import org.ums.domain.model.mutable.library.MutableCheckIn;
import org.ums.manager.CacheManager;
import org.ums.manager.library.CheckInManager;

import java.util.List;

public class CheckInCache extends ContentCache<CheckIn, MutableCheckIn, Long, CheckInManager> implements CheckInManager {

  CacheManager<CheckIn, Long> mCacheManager;

  public CheckInCache(CacheManager<CheckIn, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<CheckIn, Long> getCacheManager() {
    return mCacheManager;
  }

}
