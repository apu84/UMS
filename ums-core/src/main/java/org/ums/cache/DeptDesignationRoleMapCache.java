package org.ums.cache;

import org.ums.domain.model.immutable.DeptDesignationRoleMap;
import org.ums.domain.model.mutable.MutableDeptDesignationRoleMap;
import org.ums.manager.CacheManager;
import org.ums.manager.DeptDesignationRoleMapManager;

import java.util.List;

public class DeptDesignationRoleMapCache extends
    ContentCache<DeptDesignationRoleMap, MutableDeptDesignationRoleMap, Integer, DeptDesignationRoleMapManager>
    implements DeptDesignationRoleMapManager {

  private CacheManager<DeptDesignationRoleMap, Integer> mCacheManager;

  public DeptDesignationRoleMapCache(final CacheManager<DeptDesignationRoleMap, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<DeptDesignationRoleMap, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<DeptDesignationRoleMap> getDeptDesignationMap(String departmentId, int pEmployeeId) {
    return getManager().getDeptDesignationMap(departmentId, pEmployeeId);
  }

  @Override
  public List<DeptDesignationRoleMap> getDeptDesignationMap(String departmentId) {
    return getManager().getDeptDesignationMap(departmentId);
  }
}
