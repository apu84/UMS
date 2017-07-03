package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.Nationality;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableNationality extends Nationality, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {

  void setNationality(final String pNationality);
}
