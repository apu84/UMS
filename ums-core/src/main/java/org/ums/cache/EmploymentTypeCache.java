package org.ums.cache;

import org.ums.domain.model.immutable.EmploymentType;
import org.ums.domain.model.mutable.MutableEmploymentType;
import org.ums.manager.CacheManager;
import org.ums.manager.EmploymentTypeManager;

public class EmploymentTypeCache extends
    ContentCache<EmploymentType, MutableEmploymentType, Integer, EmploymentTypeManager> implements
    EmploymentTypeManager {

  private CacheManager<EmploymentType, Integer> mCacheManager;

  public EmploymentTypeCache(CacheManager<EmploymentType, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<EmploymentType, Integer> getCacheManager() {
    return mCacheManager;
  }
}
