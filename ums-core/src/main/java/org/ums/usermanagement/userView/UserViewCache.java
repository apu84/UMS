package org.ums.usermanagement.userView;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import java.util.List;
import java.util.Optional;

public class UserViewCache extends ContentCache<UserView, MutableUserView, String, UserViewManager> implements
    UserViewManager {
  private CacheManager<UserView, String> mCacheManager;

  public UserViewCache(final CacheManager<UserView, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<UserView, String> getCacheManager() {
    return mCacheManager;
  }
}
