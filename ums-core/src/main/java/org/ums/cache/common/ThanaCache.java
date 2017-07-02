package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.Thana;
import org.ums.domain.model.mutable.common.MutableThana;
import org.ums.manager.CacheManager;
import org.ums.manager.common.ThanaManager;

public class ThanaCache extends ContentCache<Thana, MutableThana, Integer, ThanaManager> implements ThanaManager {

  CacheManager<Thana, Integer> mCacheManager;

  public ThanaCache(CacheManager<Thana, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Thana, Integer> getCacheManager() {
    return mCacheManager;
  }
}
