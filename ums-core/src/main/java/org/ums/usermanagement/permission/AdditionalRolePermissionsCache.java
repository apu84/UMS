package org.ums.usermanagement.permission;

import org.ums.cache.ContentCache;
import org.ums.usermanagement.permission.AdditionalRolePermissions;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.permission.MutableAdditionalRolePermissions;
import org.ums.usermanagement.permission.AdditionalRolePermissionsManager;
import org.ums.manager.CacheManager;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class AdditionalRolePermissionsCache extends
    ContentCache<AdditionalRolePermissions, MutableAdditionalRolePermissions, Long, AdditionalRolePermissionsManager>
    implements AdditionalRolePermissionsManager {
  private CacheManager<AdditionalRolePermissions, Long> mCacheManager;

  public AdditionalRolePermissionsCache(final CacheManager<AdditionalRolePermissions, Long> pCacheManager) {
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
  public List<AdditionalRolePermissions> getUserPermissionsByAssignedUser(String pUserId, String pAssignedBy) {
    return getManager().getUserPermissionsByAssignedUser(pUserId, pAssignedBy);
  }

  @Override
  public int addRole(String pUserId, Role pRole, User pAssignedBy, Date pFromDate, Date pToDate) {
    return getManager().addRole(pUserId, pRole, pAssignedBy, pFromDate, pToDate);
  }

  @Override
  public int addPermissions(String pUserId, Set<String> pPermissions, User pAssignedBy, Date pFromDate, Date pToDate) {
    return getManager().addPermissions(pUserId, pPermissions, pAssignedBy, pFromDate, pToDate);
  }

  @Override
  public int removeExistingAdditionalRolePermissions(String pUserId, String pAssignedBy) {
    return getManager().removeExistingAdditionalRolePermissions(pUserId, pAssignedBy);
  }

  @Override
  public List<AdditionalRolePermissions> getAdditionalRole(String pDepartmentId) {
    return getManager().getAdditionalRole(pDepartmentId);
  }

  @Override
  public int removeExistingAdditionalRoles(String pUserId) {
    return getManager().removeExistingAdditionalRoles(pUserId);
  }

  @Override
  public int removeExistingAdditionalPermissions(String pUserId) {
    return getManager().removeExistingAdditionalPermissions(pUserId);
  }

  @Override
  public int addRole(String pUserId, Role pRole) {
    return getManager().addRole(pUserId, pRole);
  }

  @Override
  public int addPermissions(String pUserId, Set<String> pPermissions) {
    return getManager().addPermissions(pUserId, pPermissions);
  }
}
