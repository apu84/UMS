package org.ums.usermanagement.user;

import org.ums.cache.ContentCache;
import org.ums.enums.common.RoleType;
import org.ums.manager.CacheManager;
import org.ums.usermanagement.role.Role;

import java.util.List;
import java.util.Optional;

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

  @Override
  public List<User> getUsers(List<Role> pRoles) {
    return getManager().getUsers(pRoles);
  }

  @Override
  public Optional<User> getByEmail(String pEmail) {
    return getManager().getByEmail(pEmail);
  }

  @Override
  public List<User> getUsers(RoleType pRoleType) {
    return getManager().getUsers(pRoleType);
  }
}
