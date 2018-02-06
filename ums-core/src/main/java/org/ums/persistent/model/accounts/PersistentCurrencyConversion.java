package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.mutable.accounts.MutableCurrencyConversion;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.CurrencyConversionManager;
import org.ums.manager.accounts.CurrencyManager;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class PersistentCurrencyConversion implements MutableCurrencyConversion {

  @JsonIgnore
  private static CompanyManager sCompanyManager;
  @JsonIgnore
  private static CurrencyManager sCurrencyManager;
  @JsonIgnore
  private static CurrencyConversionManager sCurrencyConversionManager;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long mId;
  private Company mCompany;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private String mCompanyId;
  private Company mDefaultCompany;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private String mDefaultCompanyId;
  private Currency mCurrency;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long mCurrencyId;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private BigDecimal mConversionFactor;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private BigDecimal mReverseConversionFactor;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private BigDecimal mBaseConversionFactor;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private BigDecimal mReverseBaseConversionFactor;
  private String mStatFlag;
  private String mStatUpFlag;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "DD-MM-YYYY")
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
  public Company getDefaultCompany() {
    return mCompany == null ? sCompanyManager.get(mDefaultCompanyId) : sCompanyManager.validate(mDefaultCompany);
  }

  @Override
  public void setDefaultCompany(Company pCompany) {
    this.mCompany = pCompany;
  }

  @Override
  public String getDefaultCompanyId() {
    return mDefaultCompanyId;
  }

  @Override
  public void setDefaultCompanyId(String pDefaultCompanyId) {
    this.mDefaultCompanyId = pDefaultCompanyId;
  }

  @Override
  public Currency getCurrency() {
    return mCurrency == null ? sCurrencyManager.get(mCurrencyId) : sCurrencyManager.validate(mCurrency);
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
  public BigDecimal getReverseConversionFactor() {
    return mReverseConversionFactor;
  }

  @Override
  public void setReverseConversionFactor(BigDecimal pReverseConversionFactor) {
    this.mReverseConversionFactor = pReverseConversionFactor;
  }

  @Override
  public BigDecimal getBaseConversionFactor() {
    return mBaseConversionFactor;
  }

  @Override
  public void setBaseConversionFactor(BigDecimal pBaseConversionFactor) {
    this.mBaseConversionFactor = pBaseConversionFactor;
  }

  @Override
  public BigDecimal getReverseBaseConversionFactor() {
    return mReverseBaseConversionFactor;
  }

  @Override
  public void setReverseBaseConversionFactor(BigDecimal pReverseBaseConversionFactor) {
    this.mReverseBaseConversionFactor = pReverseBaseConversionFactor;
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
    return sCurrencyConversionManager.create(this);
  }

  @Override
  public void update() {
    sCurrencyConversionManager.update(this);
  }

  @Override
  public MutableCurrencyConversion edit() {
    return new PersistentCurrencyConversion(this);
  }

  @Override
  public void delete() {
    sCurrencyConversionManager.delete(this);
  }

  public PersistentCurrencyConversion() {}

  public PersistentCurrencyConversion(MutableCurrencyConversion pCurrencyConversion) {
    setId(pCurrencyConversion.getId());
    setCompany(pCurrencyConversion.getCompany());
    setCompanyId(pCurrencyConversion.getCompanyId());
    setDefaultCompany(pCurrencyConversion.getCompany());
    setDefaultCompanyId(pCurrencyConversion.getCompanyId());
    setCurrency(pCurrencyConversion.getCurrency());
    setCurrencyId(pCurrencyConversion.getCurrencyId());
    setConversionFactor(pCurrencyConversion.getConversionFactor());
    setReverseConversionFactor(pCurrencyConversion.getReverseConversionFactor());
    setBaseConversionFactor(pCurrencyConversion.getBaseConversionFactor());
    setReverseBaseConversionFactor(pCurrencyConversion.getReverseBaseConversionFactor());
    setStatFlag(pCurrencyConversion.getStatFlag());
    setStatUpFlag(pCurrencyConversion.getStatUpFlag());
    setModifiedDate(pCurrencyConversion.getModifiedDate());
    setModifiedBy(pCurrencyConversion.getModifiedBy());
    setLastModified(pCurrencyConversion.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);

    sCurrencyManager = applicationContext.getBean("currencyManager", CurrencyManager.class);
    sCurrencyConversionManager =
        applicationContext.getBean("currencyConversionManager", CurrencyConversionManager.class);
  }
}
