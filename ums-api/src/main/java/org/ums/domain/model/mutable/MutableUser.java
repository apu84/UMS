package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.immutable.User;

import java.util.List;

import java.util.Date;

public interface MutableUser extends User, Mutable, MutableIdentifier<String> {
  void setPassword(final char[] pPassword);

  void setTemporaryPassword(final char[] pPassword);

  void setRoleIds(final List<Integer> pRoleIds);

  void setRoles(final List<Role> pRoles);

  void setActive(final boolean pActive);

  void setPasswordResetToken(final String pPasswordResetToken);

  void setPasswordTokenGenerateDateTime(final Date pDateTime);

  void setPrimaryRoleId(final Integer pRoleId);

  void setPrimaryRole(final Role pPrimaryRole);

  void setAdditionalPermissions(List<String> pAdditionalPermissions);
}
