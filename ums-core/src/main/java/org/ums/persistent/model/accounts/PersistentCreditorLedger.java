package org.ums.persistent.model.accounts;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.mutable.accounts.MutableCreditorLedger;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountTransactionManager;
import org.ums.manager.accounts.CreditorLedgerManager;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public class PersistentCreditorLedger implements MutableCreditorLedger {

  private static CompanyManager sCompanyManager;
  private static AccountTransactionManager sAccountTransactionManager;
  private static CreditorLedgerManager sCreditorLedgerManager;
  private Long mId;
  private Company mCompany;
  private String mCompanyId;
  private String mDivisionCode;
  private String mSupplierCode;
  private AccountTransaction mAccountTransaction;
  private Long mAccountTransactionId;
  private String mVoucherNo;
  private Date mVoucherDate;
  private Integer mSerialNo;
  private String mBillNo;
  private Date mBillDate;
  private BigDecimal mAmount;
  private BigDecimal mPaidAmount;
  private Date mDueDate;
  private BalanceType mBalanceType;
  private String mBillClosingFlag;
  private String mCurrencyCode;
  private String mVatNo;
  private String mContCode;
  private String mOrderNo;
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
  public String getSupplierCode() {
    return mSupplierCode;
  }

  @Override
  public void setSupplierCode(String pSupplierCode) {
    this.mSupplierCode = pSupplierCode;
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
  public String getBillNo() {
    return mBillNo;
  }

  @Override
  public void setBillNo(String pBillNo) {
    this.mBillNo = pBillNo;
  }

  @Override
  public Date getBillDate() {
    return mBillDate;
  }

  @Override
  public void setBillDate(Date pBillDate) {
    this.mBillDate = pBillDate;
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
  public String getBillClosingFlag() {
    return mBillClosingFlag;
  }

  @Override
  public void setBillClosingFlag(String pBillClosingFlag) {
    this.mBillClosingFlag = pBillClosingFlag;
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
  public String getVatNo() {
    return mVatNo;
  }

  @Override
  public void setVatNo(String pVatNo) {
    this.mVatNo = pVatNo;
  }

  @Override
  public String getContCode() {
    return mContCode;
  }

  @Override
  public void setContCode(String pContCode) {
    this.mContCode = pContCode;
  }

  @Override
  public String getOrderNo() {
    return mOrderNo;
  }

  @Override
  public void setOrderNo(String pOrderNo) {
    this.mOrderNo = pOrderNo;
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
    return sCreditorLedgerManager.create(this);
  }

  @Override
  public void update() {
    sCreditorLedgerManager.update(this);
  }

  @Override
  public MutableCreditorLedger edit() {
    return new PersistentCreditorLedger(this);
  }

  @Override
  public void delete() {
    sCreditorLedgerManager.delete(this);
  }

  public PersistentCreditorLedger() {}

  public PersistentCreditorLedger(MutableCreditorLedger pCreditorLedger) {
    setId(pCreditorLedger.getId());
    setCompany(pCreditorLedger.getCompany());
    setCompanyId(pCreditorLedger.getCompanyId());
    setDivisionCode(pCreditorLedger.getDivisionCode());
    setSupplierCode(pCreditorLedger.getSupplierCode());
    setAccountTransaction(pCreditorLedger.getAccountTransaction());
    setAccountTransactionId(pCreditorLedger.getAccountTransactionId());
    setVoucherNo(pCreditorLedger.getVoucherNo());
    setVoucherDate(pCreditorLedger.getVoucherDate());
    setSerialNo(pCreditorLedger.getSerialNo());
    setBillNo(pCreditorLedger.getBillNo());
    setBillDate(pCreditorLedger.getBillDate());
    setAmount(pCreditorLedger.getAmount());
    setPaidAmount(pCreditorLedger.getPaidAmount());
    setDueDate(pCreditorLedger.getDueDate());
    setBalanceType(pCreditorLedger.getBalanceType());
    setBillClosingFlag(pCreditorLedger.getBillClosingFlag());
    setCurrencyCode(pCreditorLedger.getCurrencyCode());
    setVatNo(pCreditorLedger.getVatNo());
    setContCode(pCreditorLedger.getContCode());
    setOrderNo(pCreditorLedger.getOrderNo());
    setStatFlag(pCreditorLedger.getStatFlag());
    setStatUpFlag(pCreditorLedger.getStatUpFlag());
    setModificationDate(pCreditorLedger.getModificationDate());
    setModifiedBy(pCreditorLedger.getModifiedBy());
    setLastModified(pCreditorLedger.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);
    sAccountTransactionManager =
        applicationContext.getBean("accountTransactionManager", AccountTransactionManager.class);
    sCreditorLedgerManager = applicationContext.getBean("creditorLedgerManager", CreditorLedgerManager.class);
  }
}
