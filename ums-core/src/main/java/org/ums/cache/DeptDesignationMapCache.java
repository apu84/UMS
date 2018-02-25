package org.ums.cache;

import org.ums.domain.model.immutable.DeptDesignationMap;
import org.ums.domain.model.mutable.MutableDeptDesignationMap;
import org.ums.manager.CacheManager;
import org.ums.manager.DeptDesignationMapManager;

public class DeptDesignationMapCache extends
    ContentCache<DeptDesignationMap, MutableDeptDesignationMap, Integer, DeptDesignationMapManager> implements
    DeptDesignationMapManager {

  private CacheManager<DeptDesignationMap, Integer> mCacheManager;

  public DeptDesignationMapCache(final CacheManager<DeptDesignationMap, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<DeptDesignationMap, Integer> getCacheManager() {
    return mCacheManager;
  }
}
