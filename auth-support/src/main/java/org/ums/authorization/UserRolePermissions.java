package org.ums.authorization;

import java.util.Set;

public interface UserRolePermissions {
  Set<String> getUserPermissions(final String pUserId);

  Set<String> getUserRoles(final String pUserId);
}
