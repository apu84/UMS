package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.immutable.accounts.Month;
import org.ums.domain.model.immutable.accounts.MonthBalance;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.math.BigDecimal;

/**
 * Created by Monjur-E-Morshed on 09-Feb-18.
 */
public interface MutableMonthBalance extends MonthBalance, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

  void setAccountBalance(AccountBalance pAccountBalance);

  void setAccountBalanceId(Long pAccountBalanceId);

  void setMonth(Month pMonth);

  void setMonthId(Long pMonthId);

  void setTotalMonthDebitBalance(BigDecimal pTotalMonthDebitBalance);

  void setTotalMonthCreditBalance(BigDecimal pTotalMonthCreditBalance);
}
