package org.ums.cache;

import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.manager.CacheManager;
import org.ums.manager.UserManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class UserCache extends ContentCache<User, MutableUser, String, UserManager> implements UserManager {
  private CacheManager<User> mCacheManager;

  public UserCache(final CacheManager<User> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<User> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(User.class, pId);
  }

  @Override
  public int setPasswordResetToken(String pToken, String pUserId) throws Exception {
    int modified = getManager().setPasswordResetToken(pToken, pUserId);
    if (modified > 0) {
      getCacheManager().invalidate(getCacheKey(pUserId));
    }
    return modified;
  }

  @Override
  public int updatePassword(String pUserId, String pPassword) throws Exception {
    int modified = getManager().updatePassword(pUserId, pPassword);
    if (modified > 1) {
      getCacheManager().invalidate(getCacheKey(pUserId));
    }
    return modified;
  }

  @Override
  public int clearPasswordResetToken(String pUserId) throws Exception {
    int modified = getManager().clearPasswordResetToken(pUserId);
    if (modified > 1) {
      getCacheManager().invalidate(getCacheKey(pUserId));
    }
    return modified;
  }

  @Override
  public List<User> getUsers() throws Exception {
    return getManager().getUsers();
  }
}

