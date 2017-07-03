package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.domain.model.immutable.common.RelationType;

public interface MutableRelationType extends RelationType, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {

  void setRelationType(final String pRelationType);
}
