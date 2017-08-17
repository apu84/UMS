package org.ums.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.ums.decorator.NavigationDaoDecorator;
import org.ums.domain.model.immutable.Navigation;

public class NavigationByPermissionResolver extends NavigationDaoDecorator {
  private PermissionResolver mPermissionResolver;

  public NavigationByPermissionResolver(final PermissionResolver pPermissionResolver) {
    mPermissionResolver = pPermissionResolver;
  }

  @Override
  public List<Navigation> getByPermissions(Set<String> pPermissions) {
    List<Navigation> navigationList = getManager().getAll();
    List<Navigation> permittedNavigation = null;

    if(navigationList != null && navigationList.size() > 0) {
      permittedNavigation = new ArrayList<>();

      if(pPermissions != null) {
        for(String permissionString : pPermissions) {
          Permission permission = mPermissionResolver.resolvePermission(permissionString);

          for(Navigation navigation : navigationList) {
            String navigationPermissionString = navigation.getPermission();
            Permission navigationPermission = mPermissionResolver.resolvePermission(navigationPermissionString);
            if(permission.implies(navigationPermission)) {
              permittedNavigation.add(navigation);
            }
          }
        }
      }
    }
    return permittedNavigation;
  }

  @Override
  public List<Navigation> getByPermissionsId(Set<Integer> pPermissionIds) {
    return getManager().getByPermissionsId(pPermissionIds);
  }
}
