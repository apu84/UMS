package org.ums.manager;

import org.ums.domain.model.mutable.MutableAdditionalRolePermissions;
import org.ums.domain.model.readOnly.AdditionalRolePermissions;
import org.ums.domain.model.readOnly.Role;
import org.ums.domain.model.readOnly.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface AdditionalRolePermissionsManager extends ContentManager<AdditionalRolePermissions, MutableAdditionalRolePermissions, Integer> {
  List<AdditionalRolePermissions> getPermissionsByUser(final String pUserId);

  List<AdditionalRolePermissions> getUserPermissionsByAssignedUser(final String pUserId, final String pAssignedBy);

  int addRole(final String pUserId, final Role pRole, final User pAssignedBy, final Date pFromDate, final Date pToDate);

  int addPermissions(final String pUserId, final Set<String> pPermissions, final User pAssignedBy, final Date pFromDate, final Date pToDate);
}
