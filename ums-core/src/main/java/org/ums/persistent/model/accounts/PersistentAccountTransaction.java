package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.immutable.accounts.Receipt;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.general.ledger.vouchers.AccountTransactionType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PersistentAccountTransaction implements MutableAccountTransaction {

  private static CompanyManager sCompanyManager;
  private static AccountManager sAccountManager;
  private static VoucherManager sVoucherManager;
  private static CurrencyManager sCurrencyManager;
  private static ReceiptManager sReceiptManager;
  private static AccountTransactionManager sTransactionManager;

  private Long id;
  private Company company;
  private String companyId;
  private String divisionCode;
  private String voucherNo;
  private Date voucherDate;
  private Integer serialNo;
  private Account account;
  private Long accountId;
  private Voucher voucher;
  private Long voucherId;
  private BigDecimal amount;
  private BalanceType balanceType;
  private String narration;
  private BigDecimal foreignCurrency;
  private Currency currency;
  private Long currencyId;
  private BigDecimal conversionFactory;
  private String projNo;
  private Company defaultCompany;
  private String defaultCompanyId;
  private String statFlag;
  private String statUpFlag;
  private Receipt receipt;
  private Long receiptId;
  private Date postDate;
  private AccountTransactionType accountTransactionType;
  private Date modifiedDate;
  private String modifiedBy;
  private String lastModified;

  @Override
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public AccountTransactionType getAccountTransactionType() {
    return accountTransactionType;
  }

  @Override
  public void setAccountTransactionType(AccountTransactionType pAccountTransactionType) {
    accountTransactionType = pAccountTransactionType;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Long getId() {
    return id;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public void setId(Long pId) {
    this.id = pId;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Company getCompany() {
    return company == null ? sCompanyManager.get(companyId) : sCompanyManager.validate(company);
  }

  @Override
  public void setCompany(Company pCompany) {
    this.company = pCompany;
  }

  @Override
  @JsonIgnore
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public String getDefaultCompanyId() {
    return defaultCompanyId;
  }

  @Override
  @JsonIgnore
  public void setDefaultCompanyId(String pDefaultCompanyId) {
    defaultCompanyId = pDefaultCompanyId;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public String getCompanyId() {
    return companyId;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public void setCompanyId(String pCompanyId) {
    this.companyId = pCompanyId;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public String getDivisionCode() {
    return divisionCode;
  }

  @Override
  @JsonIgnore
  public void setDivisionCode(String pDivisionCode) {
    this.divisionCode = pDivisionCode;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public String getVoucherNo() {
    return voucherNo;
  }

  @Override
  public void setVoucherNo(String pVoucherNo) {
    this.voucherNo = pVoucherNo;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "DD-MM-YYYY")
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Date getVoucherDate() {
    return voucherDate;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "DD-MM-YYYY")
  public void setVoucherDate(Date pVoucherDate) {
    this.voucherDate = pVoucherDate;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Integer getSerialNo() {
    return serialNo;
  }

  @Override
  public void setSerialNo(Integer pSerialNo) {
    this.serialNo = pSerialNo;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Account getAccount() {
    return account == null ? sAccountManager.get(accountId) : sAccountManager.validate(account);
  }

  @Override
  public void setAccount(Account pAccount) {
    this.account = pAccount;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Long getAccountId() {
    return accountId;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public void setAccountId(Long pAccountId) {
    this.accountId = pAccountId;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Voucher getVoucher() {
    return voucher == null ? sVoucherManager.get(voucherId) : sVoucherManager.validate(voucher);
  }

  @Override
  public void setVoucher(Voucher pVoucher) {
    this.voucher = pVoucher;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Long getVoucherId() {
    return voucherId;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public void setVoucherId(Long pVoucherId) {
    this.voucherId = pVoucherId;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public void setAmount(BigDecimal pAmount) {
    this.amount = pAmount;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public BalanceType getBalanceType() {
    return balanceType;
  }

  @Override
  public void setBalanceType(BalanceType pBalanceType) {
    this.balanceType = pBalanceType;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public String getNarration() {
    return narration;
  }

  @Override
  public void setNarration(String pNarration) {
    this.narration = pNarration;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public BigDecimal getForeignCurrency() {
    return foreignCurrency;
  }

  @Override
  public void setForeignCurrency(BigDecimal pForeignCurrency) {
    this.foreignCurrency = pForeignCurrency;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Currency getCurrency() {
    return currency == null ? sCurrencyManager.get(currencyId) : sCurrencyManager.validate(currency);
  }

  @Override
  public void setCurrency(Currency pCurrency) {
    this.currency = pCurrency;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Long getCurrencyId() {
    return currencyId;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public void setCurrencyId(Long pCurrencyId) {
    this.currencyId = pCurrencyId;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public BigDecimal getConversionFactor() {
    return conversionFactory;
  }

  @Override
  public void setConversionFactor(BigDecimal pConversionFactor) {
    this.conversionFactory = pConversionFactor;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public String getProjNo() {
    return projNo;
  }

  @Override
  public void setProjNo(String pProjNo) {
    this.projNo = pProjNo;
  }

  @Override
  @JsonIgnore
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Company getDefaultCompany() {
    return defaultCompany == null ? sCompanyManager.get(defaultCompanyId) : defaultCompany;
  }

  @Override
  @JsonIgnore
  public void setDefaultCompany(Company pDefaultCompany) {
    this.defaultCompany = pDefaultCompany;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public String getStatFlag() {
    return statFlag;
  }

  @Override
  public void setStatFlag(String pStatFlag) {
    this.statFlag = pStatFlag;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public String getStatUpFlag() {
    return statUpFlag;
  }

  @Override
  public void setStatUpFlag(String pStatUpFlag) {
    this.statUpFlag = pStatUpFlag;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public Receipt getReceipt() {
    return receipt == null ? sReceiptManager.get(receiptId) : null;
  }

  @Override
  @JsonIgnore
  public void setReceipt(Receipt pReceipt) {
    this.receipt = pReceipt;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public Long getReceiptId() {
    return receiptId;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public void setReceiptId(Long pReceiptId) {
    this.receiptId = pReceiptId;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Date getPostDate() {
    return postDate;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  public void setPostDate(Date pPostDate) {
    this.postDate = pPostDate;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "DD-MM-YYYY")
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public Date getModifiedDate() {
    return modifiedDate;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "DD-MM-YYYY")
  public void setModifiedDate(Date pModifiedDate) {
    this.modifiedDate = pModifiedDate;
  }

  @Override
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public String getModifiedBy() {
    return modifiedBy;
  }

  @Override
  public void setModifiedBy(String pModifiedBy) {
    this.modifiedBy = pModifiedBy;
  }

  @Override
  @JsonIgnore
  public String getLastModified() {
    return lastModified;
  }

  @Override
  @JsonIgnore
  public void setLastModified(String pLastModified) {
    this.lastModified = pLastModified;
  }

  @Override
  @JsonIgnore
  public Long create() {
    return sTransactionManager.create(this);
  }

  @Override
  @JsonIgnore
  public void update() {
    sTransactionManager.update(this);
  }

  @Override
  @JsonIgnore
  public MutableAccountTransaction edit() {
    return new PersistentAccountTransaction(this);
  }

  @Override
  @JsonIgnore
  public void delete() {
    sTransactionManager.delete(this);
  }

  public PersistentAccountTransaction() {

  }

  public PersistentAccountTransaction(MutableAccountTransaction pTransaction) {
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
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);
    sAccountManager = applicationContext.getBean("accountManager", AccountManager.class);
    sVoucherManager = applicationContext.getBean("voucherManager", VoucherManager.class);
    sCurrencyManager = applicationContext.getBean("currencyManager", CurrencyManager.class);
    sReceiptManager = applicationContext.getBean("receiptManager", ReceiptManager.class);
    sTransactionManager = applicationContext.getBean("accountTransactionManager", AccountTransactionManager.class);
  }
}
