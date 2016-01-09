package org.ums.domain.model.mutable;

import org.ums.domain.model.regular.Role;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;

public interface MutableRole extends Role, Mutable, MutableIdentifier<Integer> {
  void setName(final String pName);
}
