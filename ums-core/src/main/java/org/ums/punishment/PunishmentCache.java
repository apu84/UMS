package org.ums.punishment;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

public class PunishmentCache extends ContentCache<Punishment, MutablePunishment, Long, PunishmentManager> implements
    PunishmentManager {

  CacheManager<Punishment, Long> mCacheManager;

  public PunishmentCache(CacheManager<Punishment, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Punishment, Long> getCacheManager() {
    return mCacheManager;
  }
}
