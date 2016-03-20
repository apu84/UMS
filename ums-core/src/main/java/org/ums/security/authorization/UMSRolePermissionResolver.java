package org.ums.security.authorization;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;

import java.util.Collection;
import java.util.Collections;

public class UMSRolePermissionResolver implements RolePermissionResolver {
  private PermissionResolver permissionResolver = new WildcardPermissionResolver();

  public void setPermissionResolver(PermissionResolver permissionResolver) {
    this.permissionResolver = permissionResolver;
  }

  @Override
  public Collection<Permission> resolvePermissionsInRole(String roleString) {
    return Collections.<Permission>singleton(permissionResolver.resolvePermission(roleString));
  }
}
