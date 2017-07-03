package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.Religion;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableReligion extends Religion, Editable<Integer>, MutableLastModifier, MutableIdentifier<Integer> {

  void setReligion(final String pReligion);
}
