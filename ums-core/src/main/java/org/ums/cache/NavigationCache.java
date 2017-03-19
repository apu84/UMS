package org.ums.cache;

import org.ums.domain.model.immutable.Navigation;
import org.ums.domain.model.immutable.Permission;
import org.ums.domain.model.mutable.MutableNavigation;
import org.ums.manager.CacheManager;
import org.ums.manager.NavigationManager;
import org.ums.util.CacheUtil;

import java.util.List;
import java.util.Set;

public class NavigationCache extends ContentCache<Navigation, MutableNavigation, Long, NavigationManager> implements
    NavigationManager {

  private CacheManager<Navigation, Long> mCacheManager;

  public NavigationCache(CacheManager<Navigation, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Navigation, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<Navigation> getByPermissions(Set<String> pPermissions) {
    return getManager().getByPermissions(pPermissions);
  }

  @Override
  public List<Navigation> getByPermissionsId(Set<Integer> pPermissionIds) {
    return getManager().getByPermissionsId(pPermissionIds);
  }

  @Override
  public List<Navigation> getAll() {
    String cacheKey = getCacheKey(Navigation.class.toString());
    return cachedList(cacheKey, () -> getManager().getAll());
  }

  @Override
  protected String getCachedListKey() {
    return "NavigationListKey";
  }
}
