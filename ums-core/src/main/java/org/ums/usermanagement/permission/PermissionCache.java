package org.ums.usermanagement.permission;

import org.ums.cache.ContentCache;
import org.ums.usermanagement.permission.Permission;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.permission.MutablePermission;
import org.ums.manager.CacheManager;
import org.ums.usermanagement.permission.PermissionManager;

import java.util.List;
import java.util.Set;

public class PermissionCache extends ContentCache<Permission, MutablePermission, Long, PermissionManager> implements
    PermissionManager {
  private CacheManager<Permission, Long> mCacheManager;

  public PermissionCache(final CacheManager<Permission, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Permission, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<Permission> getPermissionByRole(Role pRole){
    String cacheKey = getCacheKey(Permission.class.toString(), pRole.getId());
    return cachedList(cacheKey, () -> getManager().getPermissionByRole(pRole));
  }

  @Override
  public List<Role> getRolesByPermissions(Set<String> pPermissions) {
    return getManager().getRolesByPermissions(pPermissions);
  }

  @Override
  protected String getCachedListKey() {
    return "PermissionListCache";
  }
}
