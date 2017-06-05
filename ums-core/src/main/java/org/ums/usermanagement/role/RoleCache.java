package org.ums.usermanagement.role;

import org.ums.cache.ContentCache;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.role.MutableRole;
import org.ums.manager.CacheManager;
import org.ums.usermanagement.role.RoleManager;

import java.util.List;
import java.util.Set;

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
}
