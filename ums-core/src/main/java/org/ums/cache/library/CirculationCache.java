package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.manager.CacheManager;
import org.ums.manager.library.CirculationManager;

public class CirculationCache extends ContentCache<Circulation, MutableCirculation, Long, CirculationManager> implements
    CirculationManager {

  CacheManager<Circulation, Long> mCacheManager;

  public CirculationCache(CacheManager<Circulation, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Circulation, Long> getCacheManager() {
    return mCacheManager;
  }

}
