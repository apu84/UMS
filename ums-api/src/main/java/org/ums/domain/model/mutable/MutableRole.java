package org.ums.domain.model.mutable;

import org.ums.domain.model.readOnly.Role;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;

import java.util.List;
import java.util.Set;

public interface MutableRole extends Role, Mutable, MutableIdentifier<Integer> {
  void setName(final String pName);

  void setPermissions(final Set<String> pPermissions);
}
