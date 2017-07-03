package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.Nationality;
import org.ums.domain.model.mutable.common.MutableNationality;
import org.ums.manager.CacheManager;
import org.ums.manager.common.NationalityManager;

public class NationalityCache extends ContentCache<Nationality, MutableNationality, Integer, NationalityManager>
    implements NationalityManager {

  CacheManager<Nationality, Integer> mCacheManger;

  public NationalityCache(CacheManager<Nationality, Integer> pCacheManager) {
    mCacheManger = pCacheManager;
  }

  @Override
  protected CacheManager<Nationality, Integer> getCacheManager() {
    return mCacheManger;
  }
}
