package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Designation;

public interface MutableDesignation extends Editable<Integer>, Designation, MutableLastModifier,
    MutableIdentifier<Integer> {
  void setDesignationName(final String pDesignationName);

  void setEmployeeType(final String pEmployeeType);
}
