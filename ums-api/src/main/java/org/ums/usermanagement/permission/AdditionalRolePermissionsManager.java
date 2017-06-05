package org.ums.usermanagement.permission;

import org.ums.manager.ContentManager;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface AdditionalRolePermissionsManager extends
    ContentManager<AdditionalRolePermissions, MutableAdditionalRolePermissions, Long> {
  List<AdditionalRolePermissions> getPermissionsByUser(final String pUserId);

  List<AdditionalRolePermissions> getUserPermissionsByAssignedUser(final String pUserId, final String pAssignedBy);

  int addRole(final String pUserId, final Role pRole, final User pAssignedBy, final Date pFromDate, final Date pToDate);

  int addPermissions(final String pUserId, final Set<String> pPermissions, final User pAssignedBy,
      final Date pFromDate, final Date pToDate);

  int removeExistingAdditionalRolePermissions(final String pUserId, final String pAssignedBy);

  List<AdditionalRolePermissions> getAdditionalRole(String pDepartmentId);
}
