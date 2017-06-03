package org.ums.usermanagement.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.realm.AuthorizingRealm;

/**
 * @author <a href="mailto:apu@escenic.com">Md. Moshlehuddin Mazumder</a>
 * @author last modified by $Author$
 * @version $Revision$ $Date$
 */
public class RolePermissionResolver extends RoleDaoDecorator {
  private AuthorizingRealm mAuthorizingRealm;

  public RolePermissionResolver(AuthorizingRealm pAuthorizingRealm) {
    mAuthorizingRealm = pAuthorizingRealm;
  }

  @Override
  public List<Role> getRolesByPermission(Set<String> pPermissions) {
    List<Role> roles = getAll();
    PermissionResolver permissionResolver = mAuthorizingRealm.getPermissionResolver();
    List<Role> returnList = new ArrayList<>();

    roles.forEach((role) -> {
      Set<String> permissions = role.getPermissions();
      if(permissions != null) {
        permissions.forEach((permission) -> {
          org.apache.shiro.authz.Permission rolePermission = permissionResolver.resolvePermission(permission);
          pPermissions.forEach((pPermission) -> {
            org.apache.shiro.authz.Permission matchPermission = permissionResolver.resolvePermission(pPermission);
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
