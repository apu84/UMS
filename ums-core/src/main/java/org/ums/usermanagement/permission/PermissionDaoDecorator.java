package org.ums.usermanagement.permission;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.usermanagement.permission.MutablePermission;
import org.ums.usermanagement.permission.Permission;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.permission.PermissionManager;

import java.util.List;
import java.util.Set;

public class PermissionDaoDecorator extends ContentDaoDecorator<Permission, MutablePermission, Long, PermissionManager>
    implements PermissionManager {
  @Override
  public List<Permission> getPermissionByRole(Role pRole) {
    return getManager().getPermissionByRole(pRole);
  }

  @Override
  public List<Role> getRolesByPermissions(final Set<String> pPermissions) {
    return getManager().getRolesByPermissions(pPermissions);
  }
}
