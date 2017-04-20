package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.Division;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableDivision extends Division, Editable<String>, MutableLastModifier, MutableIdentifier<String> {

  void setDivisionId(final String pDivisionId);

  void setDivisionName(final String pDivisionName);
}
