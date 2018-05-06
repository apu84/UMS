package org.ums.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.MonthType;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.definitions.financial.account.year.YearClosingFlagType;
import org.ums.generator.IdGenerator;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.CurrencyManager;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.usermanagement.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 23-Mar-18.
 */
@Service
public class AccountBalanceService {
  @Autowired
  private AccountBalanceManager accountBalanceManager;
  @Autowired
  private CurrencyManager currencyManager;
  @Autowired
  private FinancialAccountYearManager mFinancialAccountYearManager;
  @Autowired
  private IdGenerator mIdGenerator;

  public BigDecimal getTillLastMonthBalance(final Account pAccount, final FinancialAccountYear pFinancialAccountYear,
      final Date pDate, final AccountBalance pAccountBalance) {
    AccountBalance accountBalance = pAccountBalance;
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

  public MutableAccountBalance setMonthAccountBalance(MutableAccountBalance pAccountBalance, Account pAccount) {
    LocalDate localDate = pAccountBalance.getModifiedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    Integer month = localDate.getMonthValue();
    if(month.equals(MonthType.JANUARY.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal01(pAccountBalance.getTotMonthCrBal01() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal01().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal01(pAccountBalance.getTotMonthDbBal01() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal01().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.FEBRUARY.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal02(pAccountBalance.getTotMonthCrBal02() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal02().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal02(pAccountBalance.getTotMonthDbBal02() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal02().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.MARCH.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal03(pAccountBalance.getTotMonthCrBal03() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal03().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal03(pAccountBalance.getTotMonthDbBal03() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal03().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.APRIL.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal04(pAccountBalance.getTotMonthCrBal04() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal04().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal04(pAccountBalance.getTotMonthDbBal04() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal04().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.MAY.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal05(pAccountBalance.getTotMonthCrBal05() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal05().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal05(pAccountBalance.getTotMonthDbBal05() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal05().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.JUNE.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal06(pAccountBalance.getTotMonthCrBal06() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal06().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal06(pAccountBalance.getTotMonthDbBal06() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal06().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.JULY.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal07(pAccountBalance.getTotMonthCrBal07() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal07().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal07(pAccountBalance.getTotMonthDbBal07() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal07().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.AUGUST.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal08(pAccountBalance.getTotMonthCrBal08() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal08().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal08(pAccountBalance.getTotMonthDbBal08() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal08().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.SEPTEMBER.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal09(pAccountBalance.getTotMonthCrBal09() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal09().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal09(pAccountBalance.getTotMonthDbBal09() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal09().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.OCTOBER.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal10(pAccountBalance.getTotMonthCrBal10() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal10().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal10(pAccountBalance.getTotMonthDbBal10() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal10().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.NOVEMBER.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal11(pAccountBalance.getTotMonthCrBal11() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal11().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal11(pAccountBalance.getTotMonthDbBal11() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal11().add(pAccountBalance.getYearOpenBalance()));
    }
    if(month.equals(MonthType.DECEMBER.getValue())) {
      if(pAccountBalance.getYearOpenBalanceType().equals(BalanceType.Cr))
        pAccountBalance.setTotMonthCrBal12(pAccountBalance.getTotMonthCrBal12() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthCrBal12().add(pAccountBalance.getYearOpenBalance()));
      else
        pAccountBalance.setTotMonthDbBal12(pAccountBalance.getTotMonthDbBal12() == null ? pAccountBalance
            .getYearOpenBalance() : pAccountBalance.getTotMonthDbBal12().add(pAccountBalance.getYearOpenBalance()));
    }

    return pAccountBalance;
  }

  public MutableAccountBalance createAccountBalance(MutableAccount account, User user, MutableAccountBalance accountBalance) {
    if (accountBalance != null) {
      FinancialAccountYear financialAccountYears = mFinancialAccountYearManager.getAll().stream().filter(f -> f.getYearClosingFlag().equals(YearClosingFlagType.OPEN)).collect(Collectors.toList()).get(0);
      accountBalance.setId(  mIdGenerator.getNumericId());
      accountBalance.setYearOpenBalanceType(BalanceType.Dr);
      accountBalance.setYearOpenBalance(accountBalance.getYearOpenBalance() == null ? new BigDecimal(0.00) : accountBalance.getYearOpenBalance());
      accountBalance.setFinStartDate(financialAccountYears.getCurrentStartDate());
      accountBalance.setFinEndDate(financialAccountYears.getCurrentEndDate());
      accountBalance.setAccountCode(((PersistentAccount) account).getId());
      accountBalance.setTotDebitTrans(accountBalance.getYearOpenBalance() == null ? new BigDecimal(0.00) : accountBalance.getYearOpenBalance());
      accountBalance.setTotCreditTrans(new BigDecimal(0.000));
      accountBalance.setModifiedBy(user.getEmployeeId());
      accountBalance.setModifiedDate(new Date());
      accountBalance = setMonthAccountBalance(accountBalance, account);
      accountBalanceManager.insertFromAccount(accountBalance);
    }
    return accountBalance;
  }

  public MutableAccountBalance updateAccountBalance(MutableAccount account, User user,
      MutableAccountBalance accountBalance) {

    return null;
  }

}
