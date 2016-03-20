package org.ums.cache;

import org.ums.domain.model.immutable.Permission;
import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.mutable.MutablePermission;
import org.ums.manager.CacheManager;
import org.ums.manager.PermissionManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class PermissionCache extends ContentCache<Permission, MutablePermission, Integer, PermissionManager>
    implements PermissionManager {
  private CacheManager<Permission> mCacheManager;

  public PermissionCache(final CacheManager<Permission> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Permission> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(Permission.class, pId);
  }

  @Override
  public List<Permission> getPermissionByRole(Role pRole) {
    return getManager().getPermissionByRole(pRole);
  }
}