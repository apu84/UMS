package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.immutable.SemesterWithdrawalLog;

public interface MutableSemesterWithdrawalLog extends SemesterWithdrawalLog, Editable<Long>,
    MutableLastModifier, MutableIdentifier<Long> {
  void setSemesterWithdrawal(final SemesterWithdrawal pSemesterWithdrawal);

  void setEmployeeId(final String pEmployeeId);

  void setAction(final int pAction);

  void setEventDate(final String mDate);
}
