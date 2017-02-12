package org.ums.cache;

import org.ums.domain.model.immutable.AdditionalRolePermissions;
import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableAdditionalRolePermissions;
import org.ums.manager.AdditionalRolePermissionsManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class AdditionalRolePermissionsCache
    extends
    ContentCache<AdditionalRolePermissions, MutableAdditionalRolePermissions, Long, AdditionalRolePermissionsManager>
    implements AdditionalRolePermissionsManager {
  private CacheManager<AdditionalRolePermissions, Long> mCacheManager;

  public AdditionalRolePermissionsCache(
      final CacheManager<AdditionalRolePermissions, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AdditionalRolePermissions, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<AdditionalRolePermissions> getPermissionsByUser(String pUserId) {
    return getManager().getPermissionsByUser(pUserId);
  }

  @Override
  public List<AdditionalRolePermissions> getUserPermissionsByAssignedUser(String pUserId,
      String pAssignedBy) {
    return getManager().getUserPermissionsByAssignedUser(pUserId, pAssignedBy);
  }

  @Override
  public int addRole(String pUserId, Role pRole, User pAssignedBy, Date pFromDate, Date pToDate) {
    return getManager().addRole(pUserId, pRole, pAssignedBy, pFromDate, pToDate);
  }

  @Override
  public int addPermissions(String pUserId, Set<String> pPermissions, User pAssignedBy,
      Date pFromDate, Date pToDate) {
    return getManager().addPermissions(pUserId, pPermissions, pAssignedBy, pFromDate, pToDate);
  }

  @Override
  public int removeExistingAdditionalRolePermissions(String pUserId, String pAssignedBy) {
    return getManager().removeExistingAdditionalRolePermissions(pUserId, pAssignedBy);
  }
}
