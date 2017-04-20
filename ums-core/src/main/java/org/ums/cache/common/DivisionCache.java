package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.Division;
import org.ums.domain.model.mutable.common.MutableDivision;
import org.ums.manager.CacheManager;
import org.ums.manager.common.DivisionManager;

public class DivisionCache extends ContentCache<Division, MutableDivision, String, DivisionManager> implements
    DivisionManager {

  CacheManager<Division, String> mCacheManager;

  public DivisionCache(CacheManager<Division, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Division, String> getCacheManager() {
    return mCacheManager;
  }
}
