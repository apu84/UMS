package org.ums.authorization;

import java.util.Collection;
import java.util.Collections;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;

public class PermissionResolver implements RolePermissionResolver {
  private org.apache.shiro.authz.permission.PermissionResolver permissionResolver = new WildcardPermissionResolver();

  public void setPermissionResolver(org.apache.shiro.authz.permission.PermissionResolver permissionResolver) {
    this.permissionResolver = permissionResolver;
  }

  @Override
  public Collection<Permission> resolvePermissionsInRole(String roleString) {
    return Collections.<Permission>singleton(permissionResolver.resolvePermission(roleString));
  }
}
