package org.ums.usermanagement.role;

import org.ums.manager.ContentManager;
import org.ums.usermanagement.role.MutableRole;
import org.ums.usermanagement.role.Role;

import java.util.List;
import java.util.Set;

public interface RoleManager extends ContentManager<Role, MutableRole, Integer> {
  List<Role> getRolesByPermission(Set<String> pPermissions);

  Role getByRoleRoleName(String pRoleName);
}
