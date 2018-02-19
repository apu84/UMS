package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

public interface MutableCirculation extends Circulation, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setPatronId(final String pPatronId);

  void setMfn(final Long pMfn);

  void setIssueDate(final Date pIssueDate);

  void setDueDate(final Date pDueDate);

  void setReturnDate(final Date pReturnDate);

  void setFineStatus(final int pFineStatus);

  void setAccessionNumber(final String pAccessionNumber);
}
