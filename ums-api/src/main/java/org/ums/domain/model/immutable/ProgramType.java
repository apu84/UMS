package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableProgramType;

import java.io.Serializable;

public interface ProgramType extends Serializable, EditType<MutableProgramType>,
    Identifier<Integer>, LastModifier {
  String getName();
}
