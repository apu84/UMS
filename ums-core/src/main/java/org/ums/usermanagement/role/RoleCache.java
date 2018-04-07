package org.ums.usermanagement.role;

import java.util.List;
import java.util.Set;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

public class RoleCache extends ContentCache<Role, MutableRole, Integer, RoleManager> implements RoleManager {
  private CacheManager<Role, Integer> mCacheManager;

  public RoleCache(final CacheManager<Role, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Role, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<Role> getRolesByPermission(Set<String> pPermissions) {
    return getManager().getRolesByPermission(pPermissions);
  }

  @Override
  public Role getByRoleRoleName(String pRoleName) {
    String cacheKey = getCacheKey(Role.class.toString(), pRoleName);
    return cachedEntity(cacheKey, () -> getManager().getByRoleRoleName(pRoleName));
  }
}
