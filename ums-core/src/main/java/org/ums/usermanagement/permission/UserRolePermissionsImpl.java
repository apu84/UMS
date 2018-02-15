package org.ums.usermanagement.permission;

import org.apache.commons.lang.mutable.Mutable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.ums.usermanagement.role.MutableRole;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.role.RoleManager;
import org.ums.usermanagement.user.MutableUser;
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
  private RoleManager mRoleManager;

  public UserRolePermissionsImpl(final AdditionalRolePermissionsManager pAdditionalRolePermissionsManager,
      final PermissionManager pPermissionManager, final UserManager pUserManager, final RoleManager pRoleManager) {
    mAdditionalRolePermissionsManager = pAdditionalRolePermissionsManager;
    mPermissionManager = pPermissionManager;
    mUserManager = pUserManager;
    mRoleManager = pRoleManager;
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
    if(additionalRolePermissions != null && !additionalRolePermissions.isEmpty()) {
      for(AdditionalRolePermissions additionalRolePermission : additionalRolePermissions) {
        if(additionalRolePermission.getRole() != null) {
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
    if(additionalRolePermissions != null && !additionalRolePermissions.isEmpty()) {
      for(AdditionalRolePermissions additionalRolePermission : additionalRolePermissions) {
        if(additionalRolePermission.getPermission() != null) {
          allPermissions.addAll(additionalRolePermission.getPermission());
        }
      }
    }
    return allPermissions;
  }

  @Override
  public List<Role> getUserRoles(String pUserId) {
    List<Role> allRoles = new ArrayList<>();
    User user = mUserManager.get(pUserId);
    allRoles.add(user.getPrimaryRole());

    List<AdditionalRolePermissions> additionalRolePermissions
        = mAdditionalRolePermissionsManager.getPermissionsByUser(user.getId());
    if (additionalRolePermissions != null) {
      allRoles.addAll(additionalRolePermissions.stream()
          .map(AdditionalRolePermissions::getRole)
          .collect(Collectors.toList()));
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

  @Override
  @Transactional
  public void updateUserRoles(final String pUserId, final List<Role> pRoles) {
    Assert.notEmpty(pRoles, "At least one role is required");
    MutableUser user = mUserManager.get(pUserId).edit();
    user.setPrimaryRole(mRoleManager.get(pRoles.get(0).getId()));
    user.update();
    if(pRoles.size() > 1) {
      mAdditionalRolePermissionsManager.removeExistingAdditionalRoles(pUserId);
      for(Role role : pRoles.subList(1, pRoles.size())) {
        mAdditionalRolePermissionsManager.addRole(pUserId, role);
      }
    }
  }

  @Override
  @Transactional
  public void updateUserPermissions(String pUserId, Set<String> pPermissions) {
    Assert.notEmpty(pPermissions, "At least one permission is required");
    mAdditionalRolePermissionsManager.removeExistingAdditionalPermissions(pUserId);
    mAdditionalRolePermissionsManager.addPermissions(pUserId, pPermissions);
  }

  @Override
  public void updateRolePermissions(int pRoleId, Set<String> pPermissions) {
    MutablePermission permission = mPermissionManager.getPermissionByRole(mRoleManager.get(pRoleId)).get(0).edit();
    permission.setPermissions(pPermissions);
    permission.update();
  }
}
