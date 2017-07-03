package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.Religion;
import org.ums.domain.model.mutable.common.MutableReligion;
import org.ums.manager.CacheManager;
import org.ums.manager.common.ReligionManager;

public class ReligionCache extends ContentCache<Religion, MutableReligion, Integer, ReligionManager> implements
    ReligionManager {

  CacheManager<Religion, Integer> mCacheManager;

  public ReligionCache(CacheManager<Religion, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Religion, Integer> getCacheManager() {
    return mCacheManager;
  }
}
