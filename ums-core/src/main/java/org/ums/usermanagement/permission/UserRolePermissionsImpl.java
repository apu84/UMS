package org.ums.usermanagement.permission;

import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRolePermissionsImpl implements UserRolePermissions {
  private AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;
  private PermissionManager mPermissionManager;
  private UserManager mUserManager;

  public UserRolePermissionsImpl(AdditionalRolePermissionsManager pAdditionalRolePermissionsManager,
                                 PermissionManager pPermissionManager, UserManager pUserManager) {
    mAdditionalRolePermissionsManager = pAdditionalRolePermissionsManager;
    mPermissionManager = pPermissionManager;
    mUserManager = pUserManager;
  }

  @Override
  public Set<String> getUserRolePermissions(String pUserId) {
    User user = mUserManager.get(pUserId);
    List<Permission> primaryPermissions = mPermissionManager.getPermissionByRole(user.getPrimaryRole());
    Set<String> primaryPermissionsString = primaryPermissions.get(0).getPermissions();
    List<AdditionalRolePermissions> additionalRolePermissions =
        mAdditionalRolePermissionsManager.getPermissionsByUser(user.getId());
    Set<String> allPermissions = new HashSet<>();
    allPermissions.addAll(primaryPermissionsString);
    if (additionalRolePermissions != null && !additionalRolePermissions.isEmpty()) {
      for (AdditionalRolePermissions additionalRolePermission : additionalRolePermissions) {
        if (additionalRolePermission.getRole() != null) {
          allPermissions.addAll(mPermissionManager.getPermissionByRole(user.getPrimaryRole()).get(0).getPermissions());
        }
      }
    }
    return allPermissions;
  }

  @Override
  public Set<String> getUserAdditionalPermissions(String pUserId) {
    User user = mUserManager.get(pUserId);
    List<AdditionalRolePermissions> additionalRolePermissions =
        mAdditionalRolePermissionsManager.getPermissionsByUser(user.getId());
    Set<String> allPermissions = new HashSet<>();
    if (additionalRolePermissions != null && !additionalRolePermissions.isEmpty()) {
      for (AdditionalRolePermissions additionalRolePermission : additionalRolePermissions) {
        if (additionalRolePermission.getPermission() != null) {
          allPermissions.addAll(additionalRolePermission.getPermission());
        }
      }
    }
    return allPermissions;
  }

  @Override
  public Set<Role> getUserRoles(String pUserId) {
    Set<Role> allRoles = new HashSet<>();
    User user = mUserManager.get(pUserId);
    allRoles.add(user.getPrimaryRole());

    List<AdditionalRolePermissions> additionalRolePermissions
        = mAdditionalRolePermissionsManager.getPermissionsByUser(user.getId());
    if (additionalRolePermissions != null) {
      allRoles.addAll(additionalRolePermissions.stream()
          .map(AdditionalRolePermissions::getRole)
          .collect(Collectors.toSet()));
    }
    return allRoles;
  }

  @Override
  public Set<String> getAllPermissions() {
    Set<String> allPermissions = new HashSet<>();
    mPermissionManager.getAll().forEach(pPermission -> allPermissions.addAll(pPermission.getPermissions()));
    mAdditionalRolePermissionsManager.getAll()
        .forEach(pAdditionalRolePermissions -> allPermissions.addAll(pAdditionalRolePermissions.getPermission()));
    return allPermissions;
  }
}
