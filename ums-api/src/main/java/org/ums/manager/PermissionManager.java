package org.ums.manager;


import org.ums.domain.model.mutable.MutablePermission;
import org.ums.domain.model.immutable.Permission;
import org.ums.domain.model.immutable.Role;

import java.util.List;

public interface PermissionManager extends ContentManager<Permission, MutablePermission, Integer> {
  List<Permission> getPermissionByRole(final Role pRole) throws Exception;
}
