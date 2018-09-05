package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.ems.profilemanagement.personal.PersonalInformationManager;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.serializer.UmsDateSerializer;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersistentAccount implements MutableAccount {

  private static AccountManager sAccountManager;
  private static CompanyManager sCompanyManager;
  private static PersonalInformationManager sPersonalInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);
    sPersonalInformationManager =
        applicationContext.getBean("personalInformationManager", PersonalInformationManager.class);
    sAccountManager = applicationContext.getBean("accountManager", AccountManager.class);
  }

  private Long mId;
  private Long mAccountcode;
  private Integer mRowNumber;
  private String mAccountName;
  private String mAccGroupCode;
  private Boolean mReserved;
  private BigDecimal mTaxLimit;
  private String mTaxCode;
  private String mDefaultComp;
  private String mStatFlag;
  private String mStatUpFlag;
  @JsonIgnore
  private Date mModifiedDate;
  @JsonIgnore
  private String mModifiedBy;
  private String mModifierName;
  @JsonIgnore
  private String mLastModified;
  @JsonIgnore
  private Company mCompany;
  private String mCompanyId;

  public PersistentAccount() {}

  public PersistentAccount(final PersistentAccount pPersistentAccount) {
    mId = pPersistentAccount.getId();
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
  public void setModifierName(String pModifierName) {
    mModifierName = pModifierName;
  }

  @Override
  public String getModifierName() {
    return mModifierName;
  }

  @Override
  @JsonProperty("company")
  public Company getCompany() {
    return mCompany == null ? sCompanyManager.get(mCompanyId) : mCompany;
  }

  @Override
  @JsonIgnore
  public void setCompany(Company pCompany) {
    mCompany = pCompany;
  }

  @Override
  public String getCompanyId() {
    return mCompanyId;
  }

  @Override
  public void setCompanyId(String pCompanyId) {
    mCompanyId = pCompanyId;
  }

  @Override
  public Integer getRowNumber() {
    return mRowNumber;
  }

  @Override
  public void setRowNumber(int pRowNumber) {
    mRowNumber = pRowNumber;
  }

  @Override
  public void setAccountCode(Long pAccountCode) {
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
  @JsonIgnore
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
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public Long getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public Long getAccountCode() {
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
  @JsonSerialize(using = UmsDateSerializer.class)
  public Date getModifiedDate() {
    return mModifiedDate;
  }

  @Override
  public String getModifiedBy() {
    return mModifiedBy;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public String toString() {
    return "PersistentAccount{" + "mId=" + mId + ", mAccountcode=" + mAccountcode + ", mRowNumber=" + mRowNumber
        + ", mAccountName='" + mAccountName + '\'' + ", mAccGroupCode='" + mAccGroupCode + '\'' + ", mReserved="
        + mReserved + ", mTaxLimit=" + mTaxLimit + ", mTaxCode='" + mTaxCode + '\'' + ", mDefaultComp='" + mDefaultComp
        + '\'' + ", mStatFlag='" + mStatFlag + '\'' + ", mStatUpFlag='" + mStatUpFlag + '\'' + ", mModifiedDate="
        + mModifiedDate + ", mModifiedBy='" + mModifiedBy + '\'' + ", mModifierName='" + mModifierName + '\''
        + ", mLastModified='" + mLastModified + '\'' + ", mCompany=" + mCompany + ", mCompanyId='" + mCompanyId + '\''
        + '}';
  }
}
