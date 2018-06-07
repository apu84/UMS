package org.ums.cache;

import org.ums.domain.model.immutable.DeptDesignationMap;
import org.ums.domain.model.mutable.MutableDeptDesignationMap;
import org.ums.manager.CacheManager;
import org.ums.manager.DeptDesignationMapManager;

import java.util.List;

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

  @Override
  public List<DeptDesignationMap> getDeptDesignationMap(String departmentId, int pEmployeeId) {
    return getManager().getDeptDesignationMap(departmentId, pEmployeeId);
  }

  @Override
  public List<DeptDesignationMap> getDeptDesignationMap(String departmentId) {
    return getManager().getDeptDesignationMap(departmentId);
  }
}
