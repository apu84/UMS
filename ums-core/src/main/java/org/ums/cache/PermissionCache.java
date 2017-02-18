package org.ums.cache;

import org.ums.domain.model.immutable.Permission;
import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.immutable.Teacher;
import org.ums.domain.model.mutable.MutablePermission;
import org.ums.manager.CacheManager;
import org.ums.manager.PermissionManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class PermissionCache extends
    ContentCache<Permission, MutablePermission, Long, PermissionManager> implements
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
  protected String getCachedListKey() {
    return "PermissionListCache";
  }
}
