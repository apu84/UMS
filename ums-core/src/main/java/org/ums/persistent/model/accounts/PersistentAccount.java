package org.ums.persistent.model.accounts;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.manager.accounts.AccountManager;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class PersistentAccount implements MutableAccount {

  private static AccountManager sAccountManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAccountManager = applicationContext.getBean("accountManager", AccountManager.class);
  }

  private Long mId;
  private String mStringId;
  private String mAccountcode;
  private String mAccountName;
  private String mAccGroupCode;
  private Boolean mReserved;
  private BigDecimal mTaxLimit;
  private String mTaxCode;
  private String mDefaultComp;
  private String mStatFlag;
  private String mStatUpFlag;
  private Date mModifiedDate;
  private String mModifiedBy;

  public PersistentAccount() {}

  public PersistentAccount(final PersistentAccount pPersistentAccount) {
    mId = pPersistentAccount.getId();
    mStringId = pPersistentAccount.getStringId();
    mAccountcode = pPersistentAccount.getAccountCode();
    mAccountName = pPersistentAccount.getAccountName();
    mAccGroupCode = pPersistentAccount.getAccGroupCode();
    mReserved = pPersistentAccount.getReserved();
    mTaxLimit = pPersistentAccount.getTaxLimit();
    mTaxCode = pPersistentAccount.getTaxCode();
    mDefaultComp = pPersistentAccount.getDefaultComp();
    mStatFlag = pPersistentAccount.getStatFlag();
    mStatUpFlag = pPersistentAccount.getStatUpFlag();
    mModifiedDate = pPersistentAccount.getModifiedDate();
    mModifiedBy = pPersistentAccount.getModifiedBy();
  }

  @Override
  public void setStringId(Long pId) {
    mStringId = pId.toString();
  }

  @Override
  public void setAccountCode(String pAccountCode) {
    mAccountcode = pAccountCode;
  }

  @Override
  public void setAccountName(String pAccountName) {
    mAccountName = pAccountName;
  }

  @Override
  public void setAccGroupCode(String pAccGroupCode) {
    mAccGroupCode = pAccGroupCode;
  }

  @Override
  public void setReserved(Boolean pReserved) {
    mReserved = pReserved;
  }

  @Override
  public void setTaxLimit(BigDecimal pTaxLimit) {
    mTaxLimit = pTaxLimit;
  }

  @Override
  public void setTaxCode(String pTaxCode) {
    mTaxCode = pTaxCode;
  }

  @Override
  public void setDefaultComp(String pDefaultComp) {
    mDefaultComp = pDefaultComp;
  }

  @Override
  public void setStatFlag(String pStatFlag) {
    mStatFlag = pStatFlag;
  }

  @Override
  public void setStatUpFlag(String pStatUpFlag) {
    mStatUpFlag = pStatUpFlag;
  }

  @Override
  public void setModifiedDate(Date pModifiedDate) {
    mModifiedDate = pModifiedDate;
  }

  @Override
  public void setModifiedBy(String pModifiedBy) {
    mModifiedBy = pModifiedBy;
  }

  @Override
  public MutableAccount edit() {
    return new PersistentAccount(this);
  }

  @Override
  public Long create() {
    return sAccountManager.create(this);
  }

  @Override
  public void update() {
    sAccountManager.update(this);
  }

  @Override
  public void delete() {
    sAccountManager.delete(this);
  }

  @Override
  public Long getId() {
    return mId == null ? Long.parseLong(getStringId()) : mId;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public String getStringId() {
    return mStringId;
  }

  @Override
  public String getAccountCode() {
    return mAccountcode;
  }

  @Override
  public String getAccountName() {
    return mAccountName;
  }

  @Override
  public String getAccGroupCode() {
    return mAccGroupCode;
  }

  @Override
  public Boolean getReserved() {
    return mReserved;
  }

  @Override
  public BigDecimal getTaxLimit() {
    return mTaxLimit;
  }

  @Override
  public String getTaxCode() {
    return mTaxCode;
  }

  @Override
  public String getDefaultComp() {
    return mDefaultComp;
  }

  @Override
  public String getStatFlag() {
    return mStatFlag;
  }

  @Override
  public String getStatUpFlag() {
    return mStatUpFlag;
  }

  @Override
  public Date getModifiedDate() {
    return mModifiedDate;
  }

  @Override
  public String getModifiedBy() {
    return mModifiedBy;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
