package org.ums.domain.model.mutable.accounts;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
public interface MutableAccountBalance extends AccountBalance, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {

  void setFinStartDate(Date pFinStartDate);

  void setFinEndDate(Date pFinEndDate);

  void setAccountCode(Long pAccountCode);

  void setYearOpenBalance(BigDecimal pYearOpenBalance);

  void setYearOpenBalanceType(BalanceType pYearOpenBalanceType);

  void setTotMonthDbBal01(BigDecimal pTotMonthDbBal01);

  void setTotMonthDbBal02(BigDecimal pTotMonthDbBal02);

  void setTotMonthDbBal03(BigDecimal pTotMonthDbBal03);

  void setTotMonthDbBal04(BigDecimal pTotMonthDbBal04);

  void setTotMonthDbBal05(BigDecimal pTotMonthDbBal05);

  void setTotMonthDbBal06(BigDecimal pTotMonthDbBal06);

  void setTotMonthDbBal07(BigDecimal pTotMonthDbBal07);

  void setTotMonthDbBal08(BigDecimal pTotMonthDbBal08);

  void setTotMonthDbBal09(BigDecimal pTotMonthDbBal09);

  void setTotMonthDbBal10(BigDecimal pTotMonthDbBal10);

  void setTotMonthDbBal11(BigDecimal pTotMonthDbBal11);

  void setTotMonthDbBal12(BigDecimal pTotMonthDbBal12);

  void setTotDebitTrans(BigDecimal pTotDebitTrans);

  void setTotCreditTrans(BigDecimal pTotCreditTrans);

  void setStatFlag(String pStatFlag);

  void setStatUpFlag(String pStatUpFlag);

  void setModifiedDate(Date pModifiedDate);

  void setModifiedBy(String pModifiedBy);
}
