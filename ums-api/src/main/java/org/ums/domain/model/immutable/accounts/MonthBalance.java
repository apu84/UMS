package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.accounts.MutableMonthBalance;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Monjur-E-Morshed on 09-Feb-18.
 */
public interface MonthBalance extends Serializable, EditType<MutableMonthBalance>, LastModifier, Identifier<Long> {

  AccountBalance getAccountBalance();

  Long getAccountBalanceId();

  Month getMonth();

  Long getMonthId();

  BigDecimal getTotalMonthDebitBalance();

  BigDecimal getTotalMonthCreditBalance();
}
