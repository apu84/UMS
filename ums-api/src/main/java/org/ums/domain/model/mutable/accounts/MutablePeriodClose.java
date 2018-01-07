package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.PeriodClose;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.accounts.definitions.MonthType;
import org.ums.enums.accounts.definitions.OpenCloseFlag;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
public interface MutablePeriodClose extends PeriodClose, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
  void setFinAccountYearId(Long pFinAccountYearId);

  void setCloseMonth(MonthType pCloseMonth);

  void setCloseYear(Integer pCloseYear);

  void setPeriodClosingFlag(OpenCloseFlag pPeriodClosingFlag);

  void setStatFlag(String pStatFlag);

  void setStatUpFlag(String pStatUpFlag);

  void setModifiedDate(Date pModifiedDate);

  void setModifiedBy(String pModifiedBy);
}
