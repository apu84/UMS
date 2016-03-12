package org.ums.cache;

import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.MutableDepartment;
import org.ums.manager.CacheManager;
import org.ums.manager.DepartmentManager;
import org.ums.util.CacheUtil;

public class DepartmentCache extends ContentCache<Department, MutableDepartment, String, DepartmentManager> implements DepartmentManager {
  private CacheManager<Department> mCacheManager;

  public DepartmentCache(final CacheManager<Department> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(Department.class, pId);
  }
}