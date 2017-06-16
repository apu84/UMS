package org.ums.cache;

import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.mutable.MutableDesignation;
import org.ums.manager.CacheManager;
import org.ums.manager.DesignationManager;

public class DesignationCache extends ContentCache<Designation, MutableDesignation, Integer, DesignationManager>
    implements DesignationManager {

  private CacheManager<Designation, Integer> mCacheManager;

  public DesignationCache(final CacheManager<Designation, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Designation, Integer> getCacheManager() {
    return mCacheManager;
  }
}
