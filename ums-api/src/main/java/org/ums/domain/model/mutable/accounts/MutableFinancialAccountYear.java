package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.accounts.definitions.financial.account.year.BookClosingFlagType;
import org.ums.enums.accounts.definitions.financial.account.year.YearClosingFlagType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public interface MutableFinancialAccountYear extends FinancialAccountYear, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setStringId(Long pId);

  void setCurrentStartDate(Date pCurrentStartDate);

  void setCurrentEndDate(Date pCurrentEndDate);

  void setPreviousStartDate(Date pPreviousStartDate);

  void setPreviousEndDate(Date pPreviousEndDate);

  void setBookClosingFlag(BookClosingFlagType pBookClosingFlag);

  void setItLimit(BigDecimal pItLimit);

  void setYearClosingFlag(YearClosingFlagType pYearClosingFlag);

  void setStatFlag(String pStatFlag);

  void setStatUpFlag(String pStatUpFlag);

  void setModifiedDate(Date pModifiedDate);

  void setModifiedBy(String pModifiedBy);
}
