package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.District;
import org.ums.domain.model.mutable.common.MutableDistrict;
import org.ums.manager.CacheManager;
import org.ums.manager.common.DistrictManager;

public class DistrictCache extends ContentCache<District, MutableDistrict, String, DistrictManager> implements
    DistrictManager {

  CacheManager<District, String> mCacheManager;

  public DistrictCache(CacheManager<District, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<District, String> getCacheManager() {
    return mCacheManager;
  }
}
