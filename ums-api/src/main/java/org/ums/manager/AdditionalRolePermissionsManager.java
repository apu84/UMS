package org.ums.manager;

import org.ums.domain.model.mutable.MutableAdditionalRolePermission;
import org.ums.domain.model.readOnly.AdditionalRolePermissions;
import org.ums.domain.model.readOnly.Role;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface AdditionalRolePermissionsManager extends ContentManager<AdditionalRolePermissions, MutableAdditionalRolePermission, Integer> {
  List<AdditionalRolePermissions> getPermissionsByUser(final String pUserId);

  int addRole(final String pUserId, final Role pRole, final Date pFromDate, final Date pToDate);

  int addPermissions(final String pUserId, final Set<String> pPermissions, final Date pFromDate, final Date pToDate);
}
