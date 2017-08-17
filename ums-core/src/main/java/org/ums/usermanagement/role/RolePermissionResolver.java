package org.ums.usermanagement.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.permission.PermissionResolver;

public class RolePermissionResolver extends RoleDaoDecorator {
  private PermissionResolver mPerPermissionResolver;

  public RolePermissionResolver(PermissionResolver pPermissionResolver) {
    mPerPermissionResolver = pPermissionResolver;
  }

  @Override
  public List<Role> getRolesByPermission(Set<String> pPermissions) {
    List<Role> roles = getAll();
    List<Role> returnList = new ArrayList<>();

    roles.forEach((role) -> {
      Set<String> permissions = role.getPermissions();
      if(permissions != null) {
        permissions.forEach((permission) -> {
          org.apache.shiro.authz.Permission rolePermission = mPerPermissionResolver.resolvePermission(permission);
          pPermissions.forEach((pPermission) -> {
            org.apache.shiro.authz.Permission matchPermission = mPerPermissionResolver.resolvePermission(pPermission);
            if(matchPermission.implies(rolePermission)) {
              returnList.add(role);
            }
          });
        });
      }
    });
    return returnList;
  }
}
