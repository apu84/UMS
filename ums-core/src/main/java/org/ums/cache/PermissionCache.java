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
    ContentCache<Permission, MutablePermission, Integer, PermissionManager> implements
    PermissionManager {
  private CacheManager<Permission, Integer> mCacheManager;

  public PermissionCache(final CacheManager<Permission, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Permission, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(Permission.class, pId);
  }

  @Override
  public List<Permission> getPermissionByRole(Role pRole) throws Exception{
    String cacheKey = getCacheKey(Permission.class.toString(), pRole.getId());
    return cachedList(cacheKey, () -> getManager().getPermissionByRole(pRole));
  }

  @Override
  protected String getCachedListKey() {
    return "PermissionListCache";
  }
}
