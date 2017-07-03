package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.BloodGroup;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableBloodGroup extends BloodGroup, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {

  void setBloodGroup(final String pBloodGroup);
}
