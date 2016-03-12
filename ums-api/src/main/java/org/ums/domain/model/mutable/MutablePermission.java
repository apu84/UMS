package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Permission;
import org.ums.domain.model.immutable.Role;

import java.util.Set;

public interface MutablePermission extends Permission, Mutable, MutableIdentifier<Integer>, MutableLastModifier {
  void setRole(final Role pRole);

  void setRoleId(final Integer pRoleId);

  void setPermissions(final Set<String> pPermissions);

  void addPermission(final String pPermission);
}
