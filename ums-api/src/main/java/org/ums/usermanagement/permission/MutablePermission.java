package org.ums.usermanagement.permission;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.usermanagement.permission.Permission;
import org.ums.usermanagement.role.Role;

import java.util.Set;

public interface MutablePermission extends Permission, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {
  void setRole(final Role pRole);

  void setRoleId(final Integer pRoleId);

  void setPermissions(final Set<String> pPermissions);

  void addPermission(final String pPermission);
}
