package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
public interface AccountBalance extends Serializable, LastModifier, EditType<MutableAccountBalance>, Identifier<Long> {

  Date getFinStartDate();

  Date getFinEndDate();

  Integer getAccountCode();

  BigDecimal getYearOpenBalance();

  BalanceType getYearOpenBalanceType();

  BigDecimal getTotMonthDbBal01();

  BigDecimal getTotMonthDbBal02();

  BigDecimal getTotMonthDbBal03();

  BigDecimal getTotMonthDbBal04();

  BigDecimal getTotMonthDbBal05();

  BigDecimal getTotMonthDbBal06();

  BigDecimal getTotMonthDbBal07();

  BigDecimal getTotMonthDbBal08();

  BigDecimal getTotMonthDbBal09();

  BigDecimal getTotMonthDbBal10();

  BigDecimal getTotMonthDbBal11();

  BigDecimal getTotMonthDbBal12();

  BigDecimal getTotDebitTrans();

  BigDecimal getTotCreditTrans();

  String getStatFlag();

  String getStatUpFlag();

  Date getModifiedDate();

  String getModifiedBy();
}
