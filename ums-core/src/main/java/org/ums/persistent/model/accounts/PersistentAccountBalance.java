package org.ums.persistent.model.accounts;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.manager.accounts.AccountBalanceManager;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
public class PersistentAccountBalance implements MutableAccountBalance {
  private static AccountBalanceManager sAccountBalanceManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAccountBalanceManager = applicationContext.getBean("accountBalanceManager", AccountBalanceManager.class);
  }

  private Long mId;
  private Date mFinStartDate;
  private Date mFinEndDate;
  private Long mAccountCode;
  private BigDecimal mYearOpenBalance;
  private BalanceType mYearOpenBalanceType;
  private BigDecimal mTotMonthDbBal01;
  private BigDecimal mTotMonthDbBal02;
  private BigDecimal mTotMonthDbBal03;
  private BigDecimal mTotMonthDbBal04;
  private BigDecimal mTotMonthDbBal05;
  private BigDecimal mTotMonthDbBal06;
  private BigDecimal mTotMonthDbBal07;
  private BigDecimal mTotMonthDbBal08;
  private BigDecimal mTotMonthDbBal09;
  private BigDecimal mTotMonthDbBal10;
  private BigDecimal mTotMonthDbBal11;
  private BigDecimal mTotMonthDbBal12;
  private BigDecimal mTotCreditTrans;
  private BigDecimal mTotDebitTrans;
  private String mStatFlag;
  private String mStatUpFlag;
  private Date mModifiedDate;
  private String mModifiedBy;

  public PersistentAccountBalance() {}

  @Override
  public BigDecimal getTotCreditTrans() {
    return mTotCreditTrans;
  }

  @Override
  public void setTotCreditTrans(BigDecimal pTotCreditTrans) {
    mTotCreditTrans = pTotCreditTrans;
  }

  @Override
  public BigDecimal getTotDebitTrans() {
    return mTotDebitTrans;
  }

  @Override
  public void setTotDebitTrans(BigDecimal pTotDebitTrans) {
    mTotDebitTrans = pTotDebitTrans;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  public Date getFinStartDate() {
    return mFinStartDate;
  }

  public void setFinStartDate(Date pFinStarDate) {
    mFinStartDate = pFinStarDate;
  }

  @Override
  public Date getFinEndDate() {
    return mFinEndDate;
  }

  @Override
  public void setFinEndDate(Date pFinEndDate) {
    mFinEndDate = pFinEndDate;
  }

  @Override
  public Long getAccountCode() {
    return mAccountCode;
  }

  @Override
  public void setAccountCode(Long pAccountCode) {
    mAccountCode = pAccountCode;
  }

  @Override
  public BigDecimal getYearOpenBalance() {
    return mYearOpenBalance;
  }

  @Override
  public void setYearOpenBalance(BigDecimal pYearOpenBalance) {
    mYearOpenBalance = pYearOpenBalance;
  }

  @Override
  public BalanceType getYearOpenBalanceType() {
    return mYearOpenBalanceType;
  }

  @Override
  public void setYearOpenBalanceType(BalanceType pYearOpenBalanceType) {
    mYearOpenBalanceType = pYearOpenBalanceType;
  }

  @Override
  public BigDecimal getTotMonthDbBal01() {
    return mTotMonthDbBal01;
  }

  @Override
  public void setTotMonthDbBal01(BigDecimal pTotMonthDbBal01) {
    mTotMonthDbBal01 = pTotMonthDbBal01;
  }

  @Override
  public BigDecimal getTotMonthDbBal02() {
    return mTotMonthDbBal02;
  }

  @Override
  public void setTotMonthDbBal02(BigDecimal pTotMonthDbBal02) {
    mTotMonthDbBal02 = pTotMonthDbBal02;
  }

  @Override
  public BigDecimal getTotMonthDbBal03() {
    return mTotMonthDbBal03;
  }

  @Override
  public void setTotMonthDbBal03(BigDecimal pTotMonthDbBal03) {
    mTotMonthDbBal03 = pTotMonthDbBal03;
  }

  @Override
  public BigDecimal getTotMonthDbBal04() {
    return mTotMonthDbBal04;
  }

  @Override
  public void setTotMonthDbBal04(BigDecimal pTotMonthDbBal04) {
    mTotMonthDbBal04 = pTotMonthDbBal04;
  }

  @Override
  public BigDecimal getTotMonthDbBal05() {
    return mTotMonthDbBal05;
  }

  @Override
  public void setTotMonthDbBal05(BigDecimal pTotMonthDbBal05) {
    mTotMonthDbBal05 = pTotMonthDbBal05;
  }

  @Override
  public BigDecimal getTotMonthDbBal06() {
    return mTotMonthDbBal06;
  }

  @Override
  public void setTotMonthDbBal06(BigDecimal pTotMonthDbBal06) {
    mTotMonthDbBal06 = pTotMonthDbBal06;
  }

  @Override
  public BigDecimal getTotMonthDbBal07() {
    return mTotMonthDbBal07;
  }

  @Override
  public void setTotMonthDbBal07(BigDecimal pTotMonthDbBal07) {
    mTotMonthDbBal07 = pTotMonthDbBal07;
  }

  @Override
  public BigDecimal getTotMonthDbBal08() {
    return mTotMonthDbBal08;
  }

  @Override
  public void setTotMonthDbBal08(BigDecimal pTotMonthDbBal08) {
    mTotMonthDbBal08 = pTotMonthDbBal08;
  }

  @Override
  public BigDecimal getTotMonthDbBal09() {
    return mTotMonthDbBal09;
  }

  @Override
  public void setTotMonthDbBal09(BigDecimal pTotMonthDbBal09) {
    mTotMonthDbBal09 = pTotMonthDbBal09;
  }

  @Override
  public BigDecimal getTotMonthDbBal10() {
    return mTotMonthDbBal10;
  }

  @Override
  public void setTotMonthDbBal10(BigDecimal pTotMonthDbBal10) {
    mTotMonthDbBal10 = pTotMonthDbBal10;
  }

  @Override
  public BigDecimal getTotMonthDbBal11() {
    return mTotMonthDbBal11;
  }

  @Override
  public void setTotMonthDbBal11(BigDecimal pTotMonthDbBal11) {
    mTotMonthDbBal11 = pTotMonthDbBal11;
  }

  @Override
  public BigDecimal getTotMonthDbBal12() {
    return mTotMonthDbBal12;
  }

  @Override
  public void setTotMonthDbBal12(BigDecimal pTotMonthDbBal12) {
    mTotMonthDbBal12 = pTotMonthDbBal12;
  }

  @Override
  public String getStatFlag() {
    return mStatFlag;
  }

  @Override
  public void setStatFlag(String pStatFlag) {
    mStatFlag = pStatFlag;
  }

  @Override
  public String getStatUpFlag() {
    return mStatUpFlag;
  }

  @Override
  public void setStatUpFlag(String pStatUpFlag) {
    mStatUpFlag = pStatUpFlag;
  }

  @Override
  public Date getModifiedDate() {
    return mModifiedDate;
  }

  @Override
  public void setModifiedDate(Date pModifiedDate) {
    mModifiedDate = pModifiedDate;
  }

  @Override
  public String getModifiedBy() {
    return mModifiedBy;
  }

  @Override
  public void setModifiedBy(String pModifiedBy) {
    mModifiedBy = pModifiedBy;
  }

  @Override
  public MutableAccountBalance edit() {
    return null;
  }

  @Override
  public Long create() {
    return null;
  }

  @Override
  public void update() {

  }

  @Override
  public void delete() {

  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
