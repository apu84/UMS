package org.ums.usermanagement.permission;

import org.ums.usermanagement.role.Role;

import java.util.List;
import java.util.Set;

public interface UserRolePermissions {
  Set<String> getAllPermissions();

  Set<String> getUserRolePermissions(final String pUserId);

  Set<String> getUserAdditionalPermissions(final String pUserId);

  List<Role> getUserRoles(final String pUserId);

  void updateUserRoles(final String pUserId, final List<Role> pRoles);

  void updateUserPermissions(final String pUserId, final Set<String> pPermissions);

  void updateRolePermissions(final int pRoleId, final Set<String> pPermissions);
}
