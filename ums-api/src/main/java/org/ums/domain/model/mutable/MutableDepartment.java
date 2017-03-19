package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;

public interface MutableDepartment extends Editable<String>, Department, MutableLastModifier, MutableIdentifier<String> {
  void setLongName(final String pLongName);

  void setShortName(final String pShortName);

  void setType(final int pType);
}
