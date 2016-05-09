package org.ums.cache;

import org.ums.manager.CacheManager;

public class CacheFactoryImpl implements CacheFactory {
  private CacheManager mCacheManager;

  @Override
  public CacheManager getCacheManager() {
    return mCacheManager;
  }

  public void setCacheManager(CacheManager pCacheManager) {
    mCacheManager = pCacheManager;
  }
}
