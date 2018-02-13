package org.ums.authorization;

import org.ums.usermanagement.permission.AdditionalRolePermissions;
import org.ums.usermanagement.permission.AdditionalRolePermissionsManager;
import org.ums.usermanagement.permission.Permission;
import org.ums.usermanagement.permission.PermissionManager;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRolePermissionsImpl implements UserRolePermissions {
  private AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;
  private PermissionManager mPermissionManager;
  private UserManager mUserManager;

  @Override
  public Set<String> getUserPermissions(String pUserId) {
    User user = mUserManager.get(pUserId);
    List<Permission> primaryPermissions = mPermissionManager.getPermissionByRole(user.getPrimaryRole());
    Set<String> primaryPermissionsString = primaryPermissions.get(0).getPermissions();
    List<AdditionalRolePermissions> additionalRolePermissions =
        mAdditionalRolePermissionsManager.getPermissionsByUser(user.getId());
    Set<String> allPermissions = new HashSet<>();
    allPermissions.addAll(primaryPermissionsString);
    if(additionalRolePermissions != null && !additionalRolePermissions.isEmpty()) {
      for(AdditionalRolePermissions additionalRolePermission : additionalRolePermissions) {
        if(additionalRolePermission.getPermission() != null && !additionalRolePermission.getPermission().isEmpty()) {
          allPermissions.addAll(additionalRolePermission.getPermission());
        }
      }
    }
    return allPermissions;
  }

  @Override
  public Set<String> getUserRoles(String pUserId) {
    Set<String> allRoles = new HashSet<>();
    User user = mUserManager.get(pUserId);
    allRoles.add(user.getPrimaryRole().getName());

    List<AdditionalRolePermissions> additionalRolePermissions
        = mAdditionalRolePermissionsManager.getPermissionsByUser(user.getId());
    if (additionalRolePermissions != null) {
      allRoles.addAll(additionalRolePermissions.stream()
          .map((pAdditionalRolePermissions -> pAdditionalRolePermissions.getRole().getName()))
          .collect(Collectors.toSet()));
    }
    return allRoles;
  }

  public void setAdditionalRolePermissionsManager(AdditionalRolePermissionsManager pAdditionalRolePermissionsManager) {
    mAdditionalRolePermissionsManager = pAdditionalRolePermissionsManager;
  }

  public void setPermissionManager(PermissionManager pPermissionManager) {
    mPermissionManager = pPermissionManager;
  }

  public void setUserManager(UserManager pUserManager) {
    mUserManager = pUserManager;
  }
}
