package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Role;

import java.util.Set;

public interface MutableRole extends Role, Editable<Integer>, MutableIdentifier<Integer>, MutableLastModifier {
  void setName(final String pName);

  void setPermissions(final Set<String> pPermissions);
}
