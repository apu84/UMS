package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.District;
import org.ums.domain.model.mutable.common.MutableDistrict;
import org.ums.manager.CacheManager;
import org.ums.manager.common.DistrictManager;

public class DistrictCache extends ContentCache<District, MutableDistrict, Integer, DistrictManager> implements
    DistrictManager {

  CacheManager<District, Integer> mCacheManager;

  public DistrictCache(CacheManager<District, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<District, Integer> getCacheManager() {
    return mCacheManager;
  }
}
