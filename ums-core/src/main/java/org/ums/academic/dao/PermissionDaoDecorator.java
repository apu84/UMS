package org.ums.academic.dao;


import org.ums.domain.model.mutable.MutablePermission;
import org.ums.domain.model.readOnly.Permission;
import org.ums.domain.model.readOnly.Role;
import org.ums.manager.PermissionManager;

import java.util.List;

public class PermissionDaoDecorator extends ContentDaoDecorator<Permission, MutablePermission, Integer, PermissionManager> implements PermissionManager {
  @Override
  public List<Permission> getPermissionByRole(Role pRole) {
    return getManager().getPermissionByRole(pRole);
  }
}
