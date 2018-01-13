package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

public interface MutableFine extends Fine, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setCirculationId(final Long pCheckInId);

  void setFineCategory(final int pFineCategory);

  void setFineAppliedDate(final Date pFineAppliedDate);

  void setFineAppliedBy(final String pFineAppliedBy);

  void setFineForgivenBy(final String pFineForgivenBy);

  void setFinePaymentDate(final Date pFinePaymentDate);

  void setDescription(final String pDescription);

  void setAmount(final double pAmount);

  void setPatronId(final String pPatronId);
}
