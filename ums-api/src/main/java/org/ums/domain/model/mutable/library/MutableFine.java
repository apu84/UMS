package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableFine extends Fine, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setCheckInId(final Long pCheckInId);

  void setFineCategory(final int pFineCategory);

  void setDescription(final String pDescription);

  void setAmount(final double pAmount);
}
