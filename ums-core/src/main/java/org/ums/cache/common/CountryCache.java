package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.mutable.common.MutableCountry;
import org.ums.manager.CacheManager;
import org.ums.manager.common.CountryManager;

/**
 * Created by Ifti on 31-Jan-17.
 */
public class CountryCache extends ContentCache<Country, MutableCountry, Integer, CountryManager> implements
    CountryManager {

  CacheManager<Country, Integer> mCacheManager;

  public CountryCache(CacheManager<Country, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Country, Integer> getCacheManager() {
    return mCacheManager;
  }
}
