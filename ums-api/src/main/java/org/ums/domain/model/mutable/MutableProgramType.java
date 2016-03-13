package org.ums.domain.model.mutable;

import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;

public interface MutableProgramType extends ProgramType, Mutable, MutableIdentifier<Integer> {
  void setName(final String pName);
}
