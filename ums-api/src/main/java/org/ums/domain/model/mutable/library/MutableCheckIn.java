package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.library.CheckIn;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

public interface MutableCheckIn extends CheckIn, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setCheckoutId(final Long pCheckoutId);

  void setReturnDate(final Date pReturnDate);
}
