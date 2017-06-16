package org.ums.domain.model.mutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.EmploymentType;

public interface MutableEmploymentType extends EmploymentType, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {
  void setType(final String pType);
}
