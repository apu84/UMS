package org.ums.usermanagement.permission;

import org.ums.usermanagement.role.Role;

import java.util.Set;

public interface UserRolePermissions {
  Set<String> getAllPermissions();

  Set<String> getUserRolePermissions(final String pUserId);

  Set<String> getUserAdditionalPermissions(final String pUserId);

  Set<Role> getUserRoles(final String pUserId);
}
