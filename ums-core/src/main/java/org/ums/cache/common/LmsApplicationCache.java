package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.manager.CacheManager;
import org.ums.manager.common.LmsApplicationManager;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public class LmsApplicationCache extends
    ContentCache<LmsApplication, MutableLmsApplication, Integer, LmsApplicationManager> implements
    LmsApplicationManager {

  CacheManager<LmsApplication, Integer> mCacheManager;

  public LmsApplicationCache(CacheManager<LmsApplication, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<LmsApplication, Integer> getCacheManager() {
    return mCacheManager;
  }
}
