package org.ums.domain.model.mutable;

import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;

public interface MutableDepartment extends Mutable, Department, MutableLastModifier,
    MutableIdentifier<String> {
  void setLongName(final String pLongName) throws Exception;

  void setShortName(final String pShortName) throws Exception;

  void setType(final int pType) throws Exception;
}
