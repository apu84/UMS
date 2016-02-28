package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableAdditionalRolePermission;
import org.ums.domain.model.readOnly.AdditionalRolePermissions;
import org.ums.domain.model.readOnly.Role;
import org.ums.manager.AdditionalRolePermissionsManager;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class AdditionalRolePermissionsDaoDecorator
    extends ContentDaoDecorator<AdditionalRolePermissions, MutableAdditionalRolePermission, Integer, AdditionalRolePermissionsManager>
    implements AdditionalRolePermissionsManager {
  @Override
  public List<AdditionalRolePermissions> getPermissionsByUser(String pUserId) {
    return getManager().getPermissionsByUser(pUserId);
  }

  @Override
  public int addRole(String pUserId, Role pRole, Date pFromDate, Date pToDate) {
    return getManager().addRole(pUserId, pRole, pFromDate, pToDate);
  }

  @Override
  public int addPermissions(String pUserId, Set<String> pPermissions, Date pFromDate, Date pToDate) {
    return getManager().addPermissions(pUserId, pPermissions, pFromDate, pToDate);
  }
}
