package org.ums.usermanagement.permission;

import org.ums.manager.ContentManager;
import org.ums.usermanagement.role.Role;

import java.util.List;
import java.util.Set;

public interface PermissionManager extends ContentManager<Permission, MutablePermission, Long> {
  List<Permission> getPermissionByRole(Role pRole);

  List<Role> getRolesByPermissions(final Set<String> pPermissions);
}
