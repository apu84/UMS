package org.ums.cache;

import org.ums.domain.model.immutable.AreaOfInterest;
import org.ums.domain.model.immutable.EmploymentType;
import org.ums.domain.model.mutable.MutableAreaOfInterest;
import org.ums.manager.AreaOfInterestManager;
import org.ums.manager.CacheManager;

import java.awt.geom.Area;

public class AreaOfInterestCache extends
    ContentCache<AreaOfInterest, MutableAreaOfInterest, Integer, AreaOfInterestManager> implements
    AreaOfInterestManager {

  private CacheManager<AreaOfInterest, Integer> mCacheManager;

  public AreaOfInterestCache(CacheManager<AreaOfInterest, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AreaOfInterest, Integer> getCacheManager() {
    return mCacheManager;
  }
}
