package org.ums.persistent.model.accounts;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.immutable.accounts.Receipt;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableTransaction;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class PersistentTransaction implements MutableTransaction {

  private static CompanyManager sCompanyManager;
  private static AccountManager sAccountManager;
  private static VoucherManager sVoucherManager;
  private static CurrencyManager sCurrencyManager;
  private static ReceiptManager sReceiptManager;
  private static TransactionManager sTransactionManager;
  private Long mId;
  private Company mCompany;
  private String mCompanyId;
  private String mDivisionCode;
  private String mVoucherNo;
  private Date mVoucherDate;
  private Integer mSerialNo;
  private Account mAccount;
  private Long mAccountId;
  private Voucher mVoucher;
  private Long mVoucherId;
  private BigDecimal mAmount;
  private BalanceType mBalanceType;
  private String mNarration;
  private BigDecimal mForeignCurrency;
  private Currency mCurrency;
  private Long mCurrencyId;
  private BigDecimal mConversionFactor;
  private String mProjNo;
  private Company mDefaultCompany;
  private String mDefaultCompanyId;
  private String mStatFlag;
  private String mStatUpFlag;
  private Receipt mReceipt;
  private Long mReceiptId;
  private Date mPostDate;
  private Date mModifiedDate;
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
    return mCompany == null
        ? sCompanyManager.get(mCompanyId)
        : sCompanyManager.validate(mCompany);
  }

  @Override
  public void setCompany(Company pCompany) {
    this.mCompany = pCompany;
  }

  @Override
  public String getDefaultCompanyId() {
    return mDefaultCompanyId;
  }

  @Override
  public void setDefaultCompanyId(String pDefaultCompanyId) {
    mDefaultCompanyId = pDefaultCompanyId;
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
  public Account getAccount() {
    return mAccount == null
        ? sAccountManager.get(mAccountId)
        : sAccountManager.validate(mAccount);
  }

  @Override
  public void setAccount(Account pAccount) {
    this.mAccount = pAccount;
  }

  @Override
  public Long getAccountId() {
    return mAccountId;
  }

  @Override
  public void setAccountId(Long pAccountId) {
    this.mAccountId = pAccountId;
  }

  @Override
  public Voucher getVoucher() {
    return mVoucher == null
        ? sVoucherManager.get(mVoucherId)
        : sVoucherManager.validate(mVoucher);
  }

  @Override
  public void setVoucher(Voucher pVoucher) {
    this.mVoucher = pVoucher;
  }

  @Override
  public Long getVoucherId() {
    return mVoucherId;
  }

  @Override
  public void setVoucherId(Long pVoucherId) {
    this.mVoucherId = pVoucherId;
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
  public BalanceType getBalanceType() {
    return mBalanceType;
  }

  @Override
  public void setBalanceType(BalanceType pBalanceType) {
    this.mBalanceType = pBalanceType;
  }

  @Override
  public String getNarration() {
    return mNarration;
  }

  @Override
  public void setNarration(String pNarration) {
    this.mNarration = pNarration;
  }

  @Override
  public BigDecimal getForeignCurrency() {
    return mForeignCurrency;
  }

  @Override
  public void setForeignCurrency(BigDecimal pForeignCurrency) {
    this.mForeignCurrency = pForeignCurrency;
  }

  @Override
  public Currency getCurrency() {
    return mCurrency == null
        ? sCurrencyManager.get(mCurrencyId)
        : sCurrencyManager.validate(mCurrency);
  }

  @Override
  public void setCurrency(Currency pCurrency) {
    this.mCurrency = pCurrency;
  }

  @Override
  public Long getCurrencyId() {
    return mCurrencyId;
  }

  @Override
  public void setCurrencyId(Long pCurrencyId) {
    this.mCurrencyId = pCurrencyId;
  }

  @Override
  public BigDecimal getConversionFactor() {
    return mConversionFactor;
  }

  @Override
  public void setConversionFactor(BigDecimal pConversionFactor) {
    this.mConversionFactor = pConversionFactor;
  }

  @Override
  public String getProjNo() {
    return mProjNo;
  }

  @Override
  public void setProjNo(String pProjNo) {
    this.mProjNo = pProjNo;
  }

  @Override
  public Company getDefaultCompany() {
    return mDefaultCompany==null? sCompanyManager.get(mDefaultCompanyId):mDefaultCompany;
  }

  @Override
  public void setDefaultCompany(Company pDefaultCompany) {
    this.mDefaultCompany = pDefaultCompany;
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
  public Receipt getReceipt() {
    return mReceipt == null
        ? sReceiptManager.get(mReceiptId)
        : sReceiptManager.validate(mReceipt);
  }

  @Override
  public void setReceipt(Receipt pReceipt) {
    this.mReceipt = pReceipt;
  }

  @Override
  public Long getReceiptId() {
    return mReceiptId;
  }

  @Override
  public void setReceiptId(Long pReceiptId) {
    this.mReceiptId = pReceiptId;
  }

  @Override
  public Date getPostDate() {
    return mPostDate;
  }

  @Override
  public void setPostDate(Date pPostDate) {
    this.mPostDate = pPostDate;
  }

  @Override
  public Date getModifiedDate() {
    return mModifiedDate;
  }

  @Override
  public void setModifiedDate(Date pModifiedDate) {
    this.mModifiedDate = pModifiedDate;
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
    return sTransactionManager.create(this);
  }

  @Override
  public void update() {
    sTransactionManager.update(this);
  }

  @Override
  public MutableTransaction edit() {
    return new PersistentTransaction(this);
  }

  @Override
  public void delete() {
    sTransactionManager.delete(this);
  }

  public PersistentTransaction() {
  }

  public PersistentTransaction(MutableTransaction pTransaction) {
    setId(pTransaction.getId());
    setCompany(pTransaction.getCompany());
    setCompanyId(pTransaction.getCompanyId());
    setDivisionCode(pTransaction.getDivisionCode());
    setVoucherNo(pTransaction.getVoucherNo());
    setVoucherDate(pTransaction.getVoucherDate());
    setSerialNo(pTransaction.getSerialNo());
    setAccount(pTransaction.getAccount());
    setAccountId(pTransaction.getAccountId());
    setVoucher(pTransaction.getVoucher());
    setVoucherId(pTransaction.getVoucherId());
    setAmount(pTransaction.getAmount());
    setBalanceType(pTransaction.getBalanceType());
    setNarration(pTransaction.getNarration());
    setForeignCurrency(pTransaction.getForeignCurrency());
    setCurrency(pTransaction.getCurrency());
    setCurrencyId(pTransaction.getCurrencyId());
    setConversionFactor(pTransaction.getConversionFactor());
    setProjNo(pTransaction.getProjNo());
    setDefaultCompany(pTransaction.getDefaultCompany());
    setStatFlag(pTransaction.getStatFlag());
    setStatUpFlag(pTransaction.getStatUpFlag());
    setReceipt(pTransaction.getReceipt());
    setReceiptId(pTransaction.getReceiptId());
    setPostDate(pTransaction.getPostDate());
    setModifiedDate(pTransaction.getModifiedDate());
    setModifiedBy(pTransaction.getModifiedBy());
    setLastModified(pTransaction.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext
        .getApplicationContext();
    sCompanyManager = applicationContext.getBean("companyManager",
        CompanyManager.class);
    sAccountManager = applicationContext.getBean("accountManager",
        AccountManager.class);
    sVoucherManager = applicationContext.getBean("voucherManager",
        VoucherManager.class);
    sCurrencyManager = applicationContext.getBean("currencyManager",
        CurrencyManager.class);
    sReceiptManager = applicationContext.getBean("receiptManager",
        ReceiptManager.class);
    sTransactionManager = applicationContext.getBean("transactionManager",
        TransactionManager.class);
  }
}