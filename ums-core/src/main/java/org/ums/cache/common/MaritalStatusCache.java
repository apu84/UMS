package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.MaritalStatus;
import org.ums.domain.model.mutable.common.MutableMaritalStatus;
import org.ums.manager.CacheManager;
import org.ums.manager.common.MaritalStatusManager;

public class MaritalStatusCache extends
    ContentCache<MaritalStatus, MutableMaritalStatus, Integer, MaritalStatusManager> implements MaritalStatusManager {

  CacheManager<MaritalStatus, Integer> mCacheManager;

  public MaritalStatusCache(CacheManager<MaritalStatus, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<MaritalStatus, Integer> getCacheManager() {
    return mCacheManager;
  }
}
