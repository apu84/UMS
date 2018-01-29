package org.ums.persistent.model.accounts;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.accounts.MutableCurrency;
import org.ums.enums.accounts.definitions.currency.CurrencyFlag;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.CurrencyManager;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class PersistentCurrency implements MutableCurrency {

  private static CompanyManager sCompanyManager;
  private static CurrencyManager sCurrencyManager;
  private Long mId;
  private Company mCompany;
  private String mCompanyId;
  private Integer mCurrencyCode;
  private String mCurrencyDescription;
  private CurrencyFlag mCurrencyFlag;
  private String mNotation;
  private String mDefaultCompany;
  private String mStatFlag;
  private String mStatUpFlag;
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
  public String getCompanyId() {
    return mCompanyId;
  }

  @Override
  public void setCompanyId(String pCompanyId) {
    this.mCompanyId = pCompanyId;
  }

  @Override
  public Integer getCurrencyCode() {
    return mCurrencyCode;
  }

  @Override
  public void setCurrencyCode(Integer pCurrencyCode) {
    this.mCurrencyCode = pCurrencyCode;
  }

  @Override
  public String getCurrencyDescription() {
    return mCurrencyDescription;
  }

  @Override
  public void setCurrencyDescription(String pCurrencyDescription) {
    this.mCurrencyDescription = pCurrencyDescription;
  }

  @Override
  public CurrencyFlag getCurrencyFlag() {
    return mCurrencyFlag;
  }

  @Override
  public void setCurrencyFlag(CurrencyFlag pCurrencyFlag) {
    this.mCurrencyFlag = pCurrencyFlag;
  }

  @Override
  public String getNotation() {
    return mNotation;
  }

  @Override
  public void setNotation(String pNotation) {
    this.mNotation = pNotation;
  }

  @Override
  public String getDefaultCompany() {
    return mDefaultCompany;
  }

  @Override
  public void setDefaultCompany(String pDefaultCompany) {
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
    return sCurrencyManager.create(this);
  }

  @Override
  public void update() {
    sCurrencyManager.update(this);
  }

  @Override
  public MutableCurrency edit() {
    return new PersistentCurrency(this);
  }

  @Override
  public void delete() {
    sCurrencyManager.delete(this);
  }

  public PersistentCurrency() {
  }

  public PersistentCurrency(MutableCurrency pCurrency) {
    setId(pCurrency.getId());
    setCompany(pCurrency.getCompany());
    setCompanyId(pCurrency.getCompanyId());
    setCurrencyCode(pCurrency.getCurrencyCode());
    setCurrencyDescription(pCurrency.getCurrencyDescription());
    setCurrencyFlag(pCurrency.getCurrencyFlag());
    setNotation(pCurrency.getNotation());
    setDefaultCompany(pCurrency.getDefaultCompany());
    setStatFlag(pCurrency.getStatFlag());
    setStatUpFlag(pCurrency.getStatUpFlag());
    setModifiedDate(pCurrency.getModifiedDate());
    setModifiedBy(pCurrency.getModifiedBy());
    setLastModified(pCurrency.getLastModified());
  }



  static {
    ApplicationContext applicationContext = AppContext
        .getApplicationContext();
    sCompanyManager = applicationContext.getBean("companyManager",
        CompanyManager.class);
    sCurrencyManager = applicationContext.getBean("currencyManager",
        CurrencyManager.class);
  }
}