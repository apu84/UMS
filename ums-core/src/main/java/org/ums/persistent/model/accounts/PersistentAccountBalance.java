package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersistentAccountBalance implements MutableAccountBalance {
  @JsonIgnore
  private static AccountBalanceManager sAccountBalanceManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAccountBalanceManager = applicationContext.getBean("accountBalanceManager", AccountBalanceManager.class);
  }

  private Long mId;
  @JsonIgnore
  private Date mFinStartDate;
  @JsonIgnore
  private Date mFinEndDate;
  private Long mAccountCode;
  private BigDecimal mYearOpenBalance;
  private BalanceType mYearOpenBalanceType;
  @JsonIgnore
  private BigDecimal mTotMonthDbBal01;
  @JsonIgnore
  private BigDecimal mTotMonthDbBal02;
  @JsonIgnore
  private BigDecimal mTotMonthDbBal03;
  @JsonIgnore
  private BigDecimal mTotMonthDbBal04;
  @JsonIgnore
  private BigDecimal mTotMonthDbBal05;
  @JsonIgnore
  private BigDecimal mTotMonthDbBal06;
  @JsonIgnore
  private BigDecimal mTotMonthDbBal07;
  @JsonIgnore
  private BigDecimal mTotMonthDbBal08;
  @JsonIgnore
  private BigDecimal mTotMonthDbBal09;
  @JsonIgnore
  private BigDecimal mTotMonthDbBal10;
  @JsonIgnore
  private BigDecimal mTotMonthDbBal11;
  @JsonIgnore
  private BigDecimal mTotMonthDbBal12;
  @JsonIgnore
  private BigDecimal mTotMonthCrBal01;
  @JsonIgnore
  private BigDecimal mTotMonthCrBal02;
  @JsonIgnore
  private BigDecimal mTotMonthCrBal03;
  @JsonIgnore
  private BigDecimal mTotMonthCrBal04;
  @JsonIgnore
  private BigDecimal mTotMonthCrBal05;
  @JsonIgnore
  private BigDecimal mTotMonthCrBal06;
  @JsonIgnore
  private BigDecimal mTotMonthCrBal07;
  @JsonIgnore
  private BigDecimal mTotMonthCrBal08;
  @JsonIgnore
  private BigDecimal mTotMonthCrBal09;
  @JsonIgnore
  private BigDecimal mTotMonthCrBal10;
  @JsonIgnore
  private BigDecimal mTotMonthCrBal11;
  @JsonIgnore
  private BigDecimal mTotMonthCrBal12;
  @JsonIgnore
  private BigDecimal mTotCreditTrans;
  @JsonIgnore
  private BigDecimal mTotDebitTrans;
  @JsonIgnore
  private String mStatFlag;
  @JsonIgnore
  private String mStatUpFlag;
  @JsonIgnore
  private Date mModifiedDate;
  @JsonIgnore
  private String mModifiedBy;
  @JsonIgnore
  private String mLastModified;

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
  @JsonSerialize(as = String.class)
  public Long getId() {
    return mId;
  }

  @Override
  @JsonDeserialize(as = Long.class)
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
  @JsonSerialize(as = String.class)
  public Long getAccountCode() {
    return mAccountCode;
  }

  @Override
  @JsonDeserialize(as = Long.class)
  public void setAccountCode(Long pAccountCode) {
    mAccountCode = pAccountCode;
  }

  @Override
  @JsonSerialize(as = Number.class)
  public BigDecimal getYearOpenBalance() {
    return mYearOpenBalance;
  }

  @Override
  @JsonDeserialize(as = BigDecimal.class)
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
  public BigDecimal getTotMonthCrBal01() {
    return mTotMonthCrBal01;
  }

  @Override
  public void setTotMonthCrBal01(BigDecimal pTotMonthCrBal01) {
    mTotMonthCrBal01 = pTotMonthCrBal01;
  }

  @Override
  public BigDecimal getTotMonthCrBal02() {
    return mTotMonthCrBal02;
  }

  @Override
  public void setTotMonthCrBal02(BigDecimal pTotMonthCrBal02) {
    mTotMonthCrBal02 = pTotMonthCrBal02;
  }

  @Override
  public BigDecimal getTotMonthCrBal03() {
    return mTotMonthCrBal03;
  }

  @Override
  public void setTotMonthCrBal03(BigDecimal pTotMonthCrBal03) {
    mTotMonthCrBal03 = pTotMonthCrBal03;
  }

  @Override
  public BigDecimal getTotMonthCrBal04() {
    return mTotMonthCrBal04;
  }

  @Override
  public void setTotMonthCrBal04(BigDecimal pTotMonthCrBal04) {
    mTotMonthCrBal04 = pTotMonthCrBal04;
  }

  @Override
  public BigDecimal getTotMonthCrBal05() {
    return mTotMonthCrBal05;
  }

  @Override
  public void setTotMonthCrBal05(BigDecimal pTotMonthCrBal05) {
    mTotMonthCrBal05 = pTotMonthCrBal05;
  }

  @Override
  public BigDecimal getTotMonthCrBal06() {
    return mTotMonthCrBal06;
  }

  @Override
  public void setTotMonthCrBal06(BigDecimal pTotMonthCrBal06) {
    mTotMonthCrBal06 = pTotMonthCrBal06;
  }

  @Override
  public BigDecimal getTotMonthCrBal07() {
    return mTotMonthCrBal07;
  }

  @Override
  public void setTotMonthCrBal07(BigDecimal pTotMonthCrBal07) {
    mTotMonthCrBal07 = pTotMonthCrBal07;
  }

  @Override
  public BigDecimal getTotMonthCrBal08() {
    return mTotMonthCrBal08;
  }

  @Override
  public void setTotMonthCrBal08(BigDecimal pTotMonthCrBal08) {
    mTotMonthCrBal08 = pTotMonthCrBal08;
  }

  @Override
  public BigDecimal getTotMonthCrBal09() {
    return mTotMonthCrBal09;
  }

  @Override
  public void setTotMonthCrBal09(BigDecimal pTotMonthCrBal09) {
    mTotMonthCrBal09 = pTotMonthCrBal09;
  }

  @Override
  public BigDecimal getTotMonthCrBal10() {
    return mTotMonthCrBal10;
  }

  @Override
  public void setTotMonthCrBal10(BigDecimal pTotMonthCrBal10) {
    mTotMonthCrBal10 = pTotMonthCrBal10;
  }

  @Override
  public BigDecimal getTotMonthCrBal11() {
    return mTotMonthCrBal11;
  }

  @Override
  public void setTotMonthCrBal11(BigDecimal pTotMonthCrBal11) {
    mTotMonthCrBal11 = pTotMonthCrBal11;
  }

  @Override
  public BigDecimal getTotMonthCrBal12() {
    return mTotMonthCrBal12;
  }

  @Override
  public void setTotMonthCrBal12(BigDecimal pTotMonthCrBal12) {
    mTotMonthCrBal12 = pTotMonthCrBal12;
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
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
