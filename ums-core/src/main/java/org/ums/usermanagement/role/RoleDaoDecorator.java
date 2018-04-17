package org.ums.usermanagement.role;

import java.util.List;
import java.util.Set;

import org.ums.decorator.ContentDaoDecorator;

public class RoleDaoDecorator extends ContentDaoDecorator<Role, MutableRole, Integer, RoleManager> implements
    RoleManager {
  @Override
  public List<Role> getRolesByPermission(Set<String> pPermissions) {
    return getManager().getRolesByPermission(pPermissions);
  }

  @Override
  public Role getByRoleRoleName(String pRoleName) {
    return getManager().getByRoleRoleName(pRoleName);
  }
}
