package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.ProgramType;

public interface MutableProgramType extends ProgramType, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {
  void setName(final String pName);
}
