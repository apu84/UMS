package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.Division;
import org.ums.domain.model.mutable.common.MutableDivision;
import org.ums.manager.CacheManager;
import org.ums.manager.common.DivisionManager;

public class DivisionCache extends ContentCache<Division, MutableDivision, Integer, DivisionManager> implements
    DivisionManager {

  CacheManager<Division, Integer> mCacheManager;

  public DivisionCache(CacheManager<Division, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Division, Integer> getCacheManager() {
    return mCacheManager;
  }
}
