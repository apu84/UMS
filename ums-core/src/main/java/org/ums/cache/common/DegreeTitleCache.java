package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.DegreeTitle;
import org.ums.domain.model.mutable.common.MutableDegreeTitle;
import org.ums.manager.CacheManager;
import org.ums.manager.common.DegreeTitleManager;

public class DegreeTitleCache extends ContentCache<DegreeTitle, MutableDegreeTitle, Integer, DegreeTitleManager>
    implements DegreeTitleManager {

  CacheManager<DegreeTitle, Integer> mCacheManager;

  public DegreeTitleCache(CacheManager<DegreeTitle, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<DegreeTitle, Integer> getCacheManager() {
    return mCacheManager;
  }
}
