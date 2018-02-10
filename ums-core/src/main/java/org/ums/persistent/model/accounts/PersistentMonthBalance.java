package org.ums.persistent.model.accounts;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.immutable.accounts.Month;
import org.ums.domain.model.mutable.accounts.MutableMonthBalance;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.MonthBalanceManager;
import org.ums.manager.accounts.MonthManager;

import java.math.BigDecimal;

/**
 * Created by Monjur-E-Morshed on 09-Feb-18.
 */
public class PersistentMonthBalance implements MutableMonthBalance {

  private static AccountBalanceManager sAccountBalanceManager;
  private static MonthManager sMonthManager;
  private static MonthBalanceManager sMonthBalanceManager;
  private Long mId;
  private AccountBalance mAccountBalance;
  private Long mAccountBalanceId;
  private Month mMonth;
  private Long mMonthId;
  private BigDecimal mTotalMonthDebitBalance;
  private BigDecimal mTotalMonthCreditBalance;
  private String mLastModified;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public AccountBalance getAccountBalance() {
    return mAccountBalance == null ? sAccountBalanceManager.get(mAccountBalanceId) : sAccountBalanceManager
        .validate(mAccountBalance);
  }

  @Override
  public void setAccountBalance(AccountBalance pAccountBalance) {
    this.mAccountBalance = pAccountBalance;
  }

  @Override
  public Long getAccountBalanceId() {
    return mAccountBalanceId;
  }

  @Override
  public void setAccountBalanceId(Long pAccountBalanceId) {
    this.mAccountBalanceId = pAccountBalanceId;
  }

  @Override
  public Month getMonth() {
    return mMonth == null ? sMonthManager.get(mMonthId) : sMonthManager.validate(mMonth);
  }

  @Override
  public void setMonth(Month pMonth) {
    this.mMonth = pMonth;
  }

  @Override
  public Long getMonthId() {
    return mMonthId;
  }

  @Override
  public void setMonthId(Long pMonthId) {
    this.mMonthId = pMonthId;
  }

  @Override
  public BigDecimal getTotalMonthDebitBalance() {
    return mTotalMonthDebitBalance;
  }

  @Override
  public void setTotalMonthDebitBalance(BigDecimal pTotalMonthDebitBalance) {
    this.mTotalMonthDebitBalance = pTotalMonthDebitBalance;
  }

  @Override
  public BigDecimal getTotalMonthCreditBalance() {
    return mTotalMonthCreditBalance;
  }

  @Override
  public void setTotalMonthCreditBalance(BigDecimal pTotalMonthCreditBalance) {
    this.mTotalMonthCreditBalance = pTotalMonthCreditBalance;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public Long create() {
    return sMonthBalanceManager.create(this);
  }

  @Override
  public void update() {
    sMonthBalanceManager.update(this);
  }

  @Override
  public MutableMonthBalance edit() {
    return new PersistentMonthBalance(this);
  }

  @Override
  public void delete() {
    sMonthBalanceManager.delete(this);
  }

  public PersistentMonthBalance() {}

  public PersistentMonthBalance(MutableMonthBalance pMonthBalance) {
    setId(pMonthBalance.getId());
    setAccountBalance(pMonthBalance.getAccountBalance());
    setAccountBalanceId(pMonthBalance.getAccountBalanceId());
    setMonth(pMonthBalance.getMonth());
    setMonthId(pMonthBalance.getMonthId());
    setTotalMonthDebitBalance(pMonthBalance.getTotalMonthDebitBalance());
    setTotalMonthCreditBalance(pMonthBalance.getTotalMonthCreditBalance());
    setLastModified(pMonthBalance.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAccountBalanceManager = applicationContext.getBean("accountBalanceManager", AccountBalanceManager.class);
    sMonthManager = applicationContext.getBean("monthManager", MonthManager.class);
    sMonthBalanceManager = applicationContext.getBean("monthBalanceManager", MonthBalanceManager.class);
  }
}
