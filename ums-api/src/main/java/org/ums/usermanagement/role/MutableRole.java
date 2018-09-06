package org.ums.usermanagement.role;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Set;

public interface MutableRole extends Role, Editable<Integer>, MutableIdentifier<Integer>, MutableLastModifier {
  void setName(final String pName);

  void setLabel(final String pLabel);

  void setPermissions(final Set<String> pPermissions);
}
