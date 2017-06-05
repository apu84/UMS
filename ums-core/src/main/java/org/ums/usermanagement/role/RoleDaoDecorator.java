package org.ums.usermanagement.role;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.usermanagement.role.MutableRole;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.role.RoleManager;

import java.util.List;
import java.util.Set;

public class RoleDaoDecorator extends ContentDaoDecorator<Role, MutableRole, Integer, RoleManager> implements
    RoleManager {
  @Override
  public List<Role> getRolesByPermission(Set<String> pPermissions) {
    return getManager().getRolesByPermission(pPermissions);
  }
}
