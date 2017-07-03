package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.BloodGroup;
import org.ums.domain.model.mutable.common.MutableBloodGroup;
import org.ums.manager.CacheManager;
import org.ums.manager.common.BloodGroupManager;

public class BloodGroupCache extends ContentCache<BloodGroup, MutableBloodGroup, Integer, BloodGroupManager> implements
    BloodGroupManager {

  CacheManager<BloodGroup, Integer> mCacheManager;

  public BloodGroupCache(CacheManager<BloodGroup, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<BloodGroup, Integer> getCacheManager() {
    return mCacheManager;
  }
}
