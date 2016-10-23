package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AdditionalRolePermissions;
import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.immutable.User;

import java.util.Date;
import java.util.Set;

public interface MutableAdditionalRolePermissions extends AdditionalRolePermissions, Mutable,
    MutableLastModifier, MutableIdentifier<Integer> {
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
