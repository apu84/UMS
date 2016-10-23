package org.ums.cache;

import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.mutable.MutableRole;
import org.ums.manager.CacheManager;
import org.ums.manager.RoleManager;
import org.ums.util.CacheUtil;

public class RoleCache extends ContentCache<Role, MutableRole, Integer, RoleManager> implements
    RoleManager {
  private CacheManager<Role, Integer> mCacheManager;

  public RoleCache(final CacheManager<Role, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Role, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(Role.class, pId);
  }
}
