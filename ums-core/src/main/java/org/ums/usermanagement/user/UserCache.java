package org.ums.usermanagement.user;

import org.ums.cache.ContentCache;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.MutableUser;
import org.ums.manager.CacheManager;
import org.ums.usermanagement.user.UserManager;

import java.util.List;

public class UserCache extends ContentCache<User, MutableUser, String, UserManager> implements UserManager {
  private CacheManager<User, String> mCacheManager;

  public UserCache(final CacheManager<User, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<User, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public User getByEmployeeId(String pEmployeeId) {
    return getManager().getByEmployeeId(pEmployeeId);
  }

  @Override
  public int setPasswordResetToken(String pToken, String pUserId) {
    int modified = getManager().setPasswordResetToken(pToken, pUserId);
    if(modified > 0) {
      getCacheManager().invalidate(getCacheKey(pUserId));
    }
    return modified;
  }

  @Override
  public int updatePassword(String pUserId, String pPassword) {
    int modified = getManager().updatePassword(pUserId, pPassword);
    if(modified >= 1) {
      getCacheManager().invalidate(getCacheKey(pUserId));
    }
    return modified;
  }

  @Override
  public int clearPasswordResetToken(String pUserId) {
    int modified = getManager().clearPasswordResetToken(pUserId);
    if(modified >= 1) {
      getCacheManager().invalidate(getCacheKey(pUserId));
    }
    return modified;
  }

  @Override
  public List<User> getUsers() {
    return getManager().getUsers();
  }
}
