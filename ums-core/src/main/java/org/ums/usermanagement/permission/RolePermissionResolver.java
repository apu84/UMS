package org.ums.usermanagement.permission;

import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.realm.AuthorizingRealm;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.role.RoleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:apu@escenic.com">Md. Moshlehuddin Mazumder</a>
 * @author last modified by $Author$
 * @version $Revision$ $Date$
 */
public class RolePermissionResolver extends PermissionDaoDecorator {
  private AuthorizingRealm mAuthorizingRealm;
  private RoleManager mRoleManager;

  public RolePermissionResolver(AuthorizingRealm pAuthorizingRealm, RoleManager pRoleManager) {
    mAuthorizingRealm = pAuthorizingRealm;
    mRoleManager = pRoleManager;
  }

  @Override
  public List<Role> getRolesByPermissions(Set<String> pPermissions) {
    List<Role> roles = mRoleManager.getAll();
    PermissionResolver permissionResolver = mAuthorizingRealm.getPermissionResolver();
    List<Role> returnList = new ArrayList<>();

    roles.forEach((role) -> {
      Set<String> permissions = role.getPermissions();
      permissions.forEach((permission) -> {
        org.apache.shiro.authz.Permission rolePermission = permissionResolver.resolvePermission(permission);
        pPermissions.forEach((pPermission) -> {
          org.apache.shiro.authz.Permission matchPermission = permissionResolver.resolvePermission(pPermission);
          if (matchPermission.implies(rolePermission)) {
            returnList.add(role);
          }
        });
      });
    });
    return returnList;
  }
}
