package org.ums.usermanagement.application;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

public class ApplicationCache extends ContentCache<Application, MutableApplication, Long, ApplicationManager> implements
    ApplicationManager {

  private CacheManager<Application, Long> mCacheManager;

  public ApplicationCache(final CacheManager<Application, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Application, Long> getCacheManager() {
    return mCacheManager;
  }
}
