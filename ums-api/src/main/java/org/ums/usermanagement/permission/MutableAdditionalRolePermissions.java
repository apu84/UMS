package org.ums.usermanagement.permission;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.User;

import java.util.Date;
import java.util.Set;

public interface MutableAdditionalRolePermissions extends AdditionalRolePermissions, Editable<Long>,
    MutableLastModifier, MutableIdentifier<Long> {
  void setUser(final User pUser);

  void setUserId(final String pUserId);

  void setRole(final Role pRole);

  void setPermission(final Set<String> pPermission);

  void setRoleId(final Integer pRoleId);

  void setValidFrom(final Date pFromDate);

  void setValidTo(final Date pToDate);

  void setActive(final boolean pActive);

  void setAssignedBy(final User pUser);

  void setAssignedByUserId(final String pUserId);
}
