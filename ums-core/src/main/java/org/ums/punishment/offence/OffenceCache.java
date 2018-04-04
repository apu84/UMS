package org.ums.punishment.offence;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

public class OffenceCache extends ContentCache<Offence, MutableOffence, Long, OffenceManager> implements OffenceManager {

  CacheManager<Offence, Long> mCacheManager;

  public OffenceCache(CacheManager<Offence, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Offence, Long> getCacheManager() {
    return mCacheManager;
  }
}
