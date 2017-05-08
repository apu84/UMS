package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.manager.CacheManager;
import org.ums.manager.common.LmsAppStatusManager;
import org.ums.manager.common.LmsApplicationManager;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public class LmsAppStatusCache extends ContentCache<LmsAppStatus, MutableLmsAppStatus, Integer, LmsAppStatusManager>
    implements LmsAppStatusManager {

  private CacheManager<LmsAppStatus, Integer> mCacheManager;

  public LmsAppStatusCache(CacheManager<LmsAppStatus, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<LmsAppStatus, Integer> getCacheManager() {
    return mCacheManager;
  }
}
