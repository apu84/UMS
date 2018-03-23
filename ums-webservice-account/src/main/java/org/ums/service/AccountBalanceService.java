package org.ums.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.enums.accounts.definitions.MonthType;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.CurrencyManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 23-Mar-18.
 */
@Service
public class AccountBalanceService {
  @Autowired
  private AccountBalanceManager accountBalanceManager;
  @Autowired
  private CurrencyManager currencyManager;

  public BigDecimal getTillLastMonthBalance(final Account pAccount, final FinancialAccountYear pFinancialAccountYear,
      final Date pDate) {
    AccountBalance accountBalance =
        accountBalanceManager.getAccountBalance(pFinancialAccountYear.getCurrentStartDate(),
            pFinancialAccountYear.getCurrentEndDate(), pAccount);
    BigDecimal balance = accountBalance.getYearOpenBalance();
    LocalDate localDate = pDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    for(int i = 1; i <= localDate.getMonth().getValue(); i++) {
      balance = getMonthTotalBalance(MonthType.get(i), balance, accountBalance);
    }

    return balance;
  }

  private BigDecimal getMonthTotalBalance(final MonthType pMonthType, BigDecimal pBalance,
      final AccountBalance pAccountBalance) {
    if(pMonthType.equals(MonthType.JANUARY)) {
      pBalance = (pAccountBalance.getTotMonthDbBal01().subtract(pAccountBalance.getTotMonthCrBal01()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.FEBRUARY)) {
      pBalance = (pAccountBalance.getTotMonthDbBal02().subtract(pAccountBalance.getTotMonthCrBal02()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.MARCH)) {
      pBalance = (pAccountBalance.getTotMonthDbBal03().subtract(pAccountBalance.getTotMonthCrBal03()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.APRIL)) {
      pBalance = (pAccountBalance.getTotMonthDbBal04().subtract(pAccountBalance.getTotMonthCrBal04()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.MAY)) {
      pBalance = (pAccountBalance.getTotMonthDbBal05().subtract(pAccountBalance.getTotMonthCrBal05()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.JUNE)) {
      pBalance = (pAccountBalance.getTotMonthDbBal06().subtract(pAccountBalance.getTotMonthCrBal06()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.JULY)) {
      pBalance = (pAccountBalance.getTotMonthDbBal07().subtract(pAccountBalance.getTotMonthCrBal07()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.AUGUST)) {
      pBalance = (pAccountBalance.getTotMonthDbBal08().subtract(pAccountBalance.getTotMonthCrBal08()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.SEPTEMBER)) {
      pBalance = (pAccountBalance.getTotMonthDbBal09().subtract(pAccountBalance.getTotMonthCrBal09()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.OCTOBER)) {
      pBalance = (pAccountBalance.getTotMonthDbBal10().subtract(pAccountBalance.getTotMonthCrBal10()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.NOVEMBER)) {
      pBalance = (pAccountBalance.getTotMonthDbBal11().subtract(pAccountBalance.getTotMonthCrBal11()).add(pBalance));
    }
    else if(pMonthType.equals(MonthType.DECEMBER)) {
      pBalance = (pAccountBalance.getTotMonthDbBal12().subtract(pAccountBalance.getTotMonthCrBal12()).add(pBalance));
    }
    else {
      return pBalance;
    }

    return pBalance;
  }

}
