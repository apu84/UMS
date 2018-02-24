package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.mutable.accounts.MutableChequeRegister;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountTransactionManager;
import org.ums.manager.accounts.ChequeRegisterManager;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 20-Feb-18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistentChequeRegister implements MutableChequeRegister {

  private static CompanyManager sCompanyManager;
  private static AccountTransactionManager sAccountTransactionManager;
  private static ChequeRegisterManager sCheckRegisterManager;
  private Long mId;
  private Company mCompany;
  private String mCompanyId;
  private AccountTransaction mAccountTransaction;
  private Long mAccountTransactionId;
  private String mCheckNo;
  private Date mChequeDate;
  private String mStatus;
  private Date mRealizationDate;
  private String mStatFlag;
  private String mStatUpFlag;
  private Date mModificationDate;
  private String mModifiedBy;
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
  public Company getCompany() {
    return mCompany == null ? sCompanyManager.get(mCompanyId) : sCompanyManager.validate(mCompany);
  }

  @Override
  public void setCompany(Company pCompany) {
    this.mCompany = pCompany;
  }

  @Override
  public String getCompanyId() {
    return mCompanyId;
  }

  @Override
  public void setCompanyId(String pCompanyId) {
    this.mCompanyId = pCompanyId;
  }

  @Override
  public AccountTransaction getAccountTransaction() {
    return mAccountTransaction == null ? sAccountTransactionManager.get(mAccountTransactionId)
        : sAccountTransactionManager.validate(mAccountTransaction);
  }

  @Override
  public void setAccountTransaction(AccountTransaction pAccountTransaction) {
    this.mAccountTransaction = pAccountTransaction;
  }

  @Override
  public Long getAccountTransactionId() {
    return mAccountTransactionId;
  }

  @Override
  public void setAccountTransactionId(Long pAccountTransactionId) {
    this.mAccountTransactionId = pAccountTransactionId;
  }

  @Override
  public String getCheckNo() {
    return mCheckNo;
  }

  @Override
  public void setCheckNo(String pCheckNo) {
    this.mCheckNo = pCheckNo;
  }

  @Override
  public Date getChequeDate() {
    return mChequeDate;
  }

  @Override
  public void setChequeDate(Date pChequeDate) {
    this.mChequeDate = pChequeDate;
  }

  @Override
  public String getStatus() {
    return mStatus;
  }

  @Override
  public void setStatus(String pStatus) {
    this.mStatus = pStatus;
  }

  @Override
  public Date getRealizationDate() {
    return mRealizationDate;
  }

  @Override
  public void setRealizationDate(Date pRealizationDate) {
    this.mRealizationDate = pRealizationDate;
  }

  @Override
  public String getStatFlag() {
    return mStatFlag;
  }

  @Override
  public void setStatFlag(String pStatFlag) {
    this.mStatFlag = pStatFlag;
  }

  @Override
  public String getStatUpFlag() {
    return mStatUpFlag;
  }

  @Override
  public void setStatUpFlag(String pStatUpFlag) {
    this.mStatUpFlag = pStatUpFlag;
  }

  @Override
  public Date getModificationDate() {
    return mModificationDate;
  }

  @Override
  public void setModificationDate(Date pModificationDate) {
    this.mModificationDate = pModificationDate;
  }

  @Override
  public String getModifiedBy() {
    return mModifiedBy;
  }

  @Override
  public void setModifiedBy(String pModifiedBy) {
    this.mModifiedBy = pModifiedBy;
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
    return sCheckRegisterManager.create(this);
  }

  @Override
  public void update() {
    sCheckRegisterManager.update(this);
  }

  @Override
  public MutableChequeRegister edit() {
    return new PersistentChequeRegister(this);
  }

  @Override
  public void delete() {
    sCheckRegisterManager.delete(this);
  }

  public PersistentChequeRegister() {
  }

  public PersistentChequeRegister(MutableChequeRegister pCheckRegister) {
    setId(pCheckRegister.getId());
    setCompany(pCheckRegister.getCompany());
    setCompanyId(pCheckRegister.getCompanyId());
    setAccountTransaction(pCheckRegister.getAccountTransaction());
    setAccountTransactionId(pCheckRegister.getAccountTransactionId());
    setCheckNo(pCheckRegister.getCheckNo());
    setChequeDate(pCheckRegister.getChequeDate());
    setStatus(pCheckRegister.getStatus());
    setRealizationDate(pCheckRegister.getRealizationDate());
    setStatFlag(pCheckRegister.getStatFlag());
    setStatUpFlag(pCheckRegister.getStatUpFlag());
    setModificationDate(pCheckRegister.getModificationDate());
    setModifiedBy(pCheckRegister.getModifiedBy());
    setLastModified(pCheckRegister.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);
    sAccountTransactionManager =
        applicationContext.getBean("accountTransactionManager", AccountTransactionManager.class);
    sCheckRegisterManager = applicationContext.getBean("chequeRegisterManager", ChequeRegisterManager.class);
  }
}
