package org.ums.persistent.model.accounts;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.mutable.accounts.MutableDebtorLedger;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountTransactionManager;
import org.ums.manager.accounts.DebtorLedgerManager;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public class PersistentDebtorLedger implements MutableDebtorLedger {

  private static CompanyManager sCompanyManager;
  private static AccountTransactionManager sAccountTransactionManager;
  private static DebtorLedgerManager sDebtorLedgerManager;
  private Long mId;
  private Company mCompany;
  private String mCompanyId;
  private String mDivisionCode;
  private String mCustomerCode;
  private AccountTransaction mAccountTransaction;
  private Long mAccountTransactionId;
  private String mVoucherNo;
  private Date mVoucherDate;
  private Integer mSerialNo;
  private String mInvoiceNo;
  private Date mInvoiceDate;
  private BigDecimal mAmount;
  private BigDecimal mPaidAmount;
  private Date mDueDate;
  private BalanceType mBalanceType;
  private String mInvoiceClosingFlag;
  private String mCurrencyCode;
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
  public String getDivisionCode() {
    return mDivisionCode;
  }

  @Override
  public void setDivisionCode(String pDivisionCode) {
    this.mDivisionCode = pDivisionCode;
  }

  @Override
  public String getCustomerCode() {
    return mCustomerCode;
  }

  @Override
  public void setCustomerCode(String pCustomerCode) {
    this.mCustomerCode = pCustomerCode;
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
  public String getVoucherNo() {
    return mVoucherNo;
  }

  @Override
  public void setVoucherNo(String pVoucherNo) {
    this.mVoucherNo = pVoucherNo;
  }

  @Override
  public Date getVoucherDate() {
    return mVoucherDate;
  }

  @Override
  public void setVoucherDate(Date pVoucherDate) {
    this.mVoucherDate = pVoucherDate;
  }

  @Override
  public Integer getSerialNo() {
    return mSerialNo;
  }

  @Override
  public void setSerialNo(Integer pSerialNo) {
    this.mSerialNo = pSerialNo;
  }

  @Override
  public String getInvoiceNo() {
    return mInvoiceNo;
  }

  @Override
  public void setInvoiceNo(String pInvoiceNo) {
    this.mInvoiceNo = pInvoiceNo;
  }

  @Override
  public Date getInvoiceDate() {
    return mInvoiceDate;
  }

  @Override
  public void setInvoiceDate(Date pInvoiceDate) {
    this.mInvoiceDate = pInvoiceDate;
  }

  @Override
  public BigDecimal getAmount() {
    return mAmount;
  }

  @Override
  public void setAmount(BigDecimal pAmount) {
    this.mAmount = pAmount;
  }

  @Override
  public BigDecimal getPaidAmount() {
    return mPaidAmount;
  }

  @Override
  public void setPaidAmount(BigDecimal pPaidAmount) {
    this.mPaidAmount = pPaidAmount;
  }

  @Override
  public Date getDueDate() {
    return mDueDate;
  }

  @Override
  public void setDueDate(Date pDueDate) {
    this.mDueDate = pDueDate;
  }

  @Override
  public BalanceType getBalanceType() {
    return mBalanceType;
  }

  @Override
  public void setBalanceType(BalanceType pBalanceType) {
    this.mBalanceType = pBalanceType;
  }

  @Override
  public String getInvoiceClosingFlag() {
    return mInvoiceClosingFlag;
  }

  @Override
  public void setInvoiceClosingFlag(String pInvoiceClosingFlag) {
    this.mInvoiceClosingFlag = pInvoiceClosingFlag;
  }

  @Override
  public String getCurrencyCode() {
    return mCurrencyCode;
  }

  @Override
  public void setCurrencyCode(String pCurrencyCode) {
    this.mCurrencyCode = pCurrencyCode;
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
    return sDebtorLedgerManager.create(this);
  }

  @Override
  public void update() {
    sDebtorLedgerManager.update(this);
  }

  @Override
  public MutableDebtorLedger edit() {
    return new PersistentDebtorLedger(this);
  }

  @Override
  public void delete() {
    sDebtorLedgerManager.delete(this);
  }

  public PersistentDebtorLedger() {}

  public PersistentDebtorLedger(MutableDebtorLedger pDebtorLedger) {
    setId(pDebtorLedger.getId());
    setCompany(pDebtorLedger.getCompany());
    setCompanyId(pDebtorLedger.getCompanyId());
    setDivisionCode(pDebtorLedger.getDivisionCode());
    setCustomerCode(pDebtorLedger.getCustomerCode());
    setAccountTransaction(pDebtorLedger.getAccountTransaction());
    setAccountTransactionId(pDebtorLedger.getAccountTransactionId());
    setVoucherNo(pDebtorLedger.getVoucherNo());
    setVoucherDate(pDebtorLedger.getVoucherDate());
    setSerialNo(pDebtorLedger.getSerialNo());
    setInvoiceNo(pDebtorLedger.getInvoiceNo());
    setInvoiceDate(pDebtorLedger.getInvoiceDate());
    setAmount(pDebtorLedger.getAmount());
    setPaidAmount(pDebtorLedger.getPaidAmount());
    setDueDate(pDebtorLedger.getDueDate());
    setBalanceType(pDebtorLedger.getBalanceType());
    setInvoiceClosingFlag(pDebtorLedger.getInvoiceClosingFlag());
    setCurrencyCode(pDebtorLedger.getCurrencyCode());
    setStatFlag(pDebtorLedger.getStatFlag());
    setStatUpFlag(pDebtorLedger.getStatUpFlag());
    setModificationDate(pDebtorLedger.getModificationDate());
    setModifiedBy(pDebtorLedger.getModifiedBy());
    setLastModified(pDebtorLedger.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);
    sAccountTransactionManager =
        applicationContext.getBean("accountTransactionManager", AccountTransactionManager.class);
    sDebtorLedgerManager = applicationContext.getBean("debtorLedgerManager", DebtorLedgerManager.class);
  }
}
