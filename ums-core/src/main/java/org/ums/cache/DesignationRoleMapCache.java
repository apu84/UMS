package org.ums.cache;

import org.ums.domain.model.immutable.DesignationRoleMap;
import org.ums.domain.model.mutable.MutableDesignationRoleMap;
import org.ums.manager.CacheManager;
import org.ums.manager.DesignationRoleMapManager;

public class DesignationRoleMapCache extends
    ContentCache<DesignationRoleMap, MutableDesignationRoleMap, Integer, DesignationRoleMapManager> implements
    DesignationRoleMapManager {

  private CacheManager<DesignationRoleMap, Integer> mCacheManager;

  public DesignationRoleMapCache(final CacheManager<DesignationRoleMap, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<DesignationRoleMap, Integer> getCacheManager() {
    return mCacheManager;
  }
}
