package org.ums.punishment.penalty;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

public class PenaltyCache extends ContentCache<Penalty, MutablePenalty, Long, PenaltyManager> implements PenaltyManager {

  CacheManager<Penalty, Long> mCacheManager;

  public PenaltyCache(CacheManager<Penalty, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Penalty, Long> getCacheManager() {
    return mCacheManager;
  }
}
