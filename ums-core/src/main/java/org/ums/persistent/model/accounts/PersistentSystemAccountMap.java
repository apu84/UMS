package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableSystemAccountMap;
import org.ums.ems.profilemanagement.personal.PersonalInformationManager;
import org.ums.enums.accounts.definitions.account.balance.AccountType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.manager.accounts.SystemAccountMapManager;
import org.ums.serializer.UmsDateSerializer;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 04-Jun-18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersistentSystemAccountMap implements MutableSystemAccountMap {

  private static AccountManager sAccountManager;
  private static CompanyManager sCompanyManager;
  private static PersonalInformationManager sPersonalInformationManager;
  private static SystemAccountMapManager sSystemAccountMapManager;

  private Long mId;
  @JsonIgnore
  private AccountType mAccountType;
  @JsonIgnore
  private Account mAccount;
  private Long mAccountId;
  private String mModifiedBy;
  private String mModifierName;
  @JsonIgnore
  private Date mModifiedDate;
  @JsonIgnore
  private Company mCompany;
  private String mCompanyId;
  private String mLastModified;

  @Override
  @JsonIgnore
  public void setCompany(Company pCompany) {
    mCompany = pCompany;
  }

  @Override
  public void setCompanyId(String pCompanyId) {
    mCompanyId = pCompanyId;
  }

  @Override
  @JsonProperty("company")
  public Company getCompany() {
    return mCompany == null ? mCompany : sCompanyManager.get(mCompanyId);
  }

  @Override
  public String getCompanyId() {
    return mCompanyId;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  @JsonProperty("accountType")
  public AccountType getAccountType() {
    return mAccountType;
  }

  @Override
  @JsonProperty("accountType")
  public void setAccountType(AccountType pAccountType) {
    this.mAccountType = pAccountType;
  }

  @Override
  @JsonProperty
  public Account getAccount() {
    return mAccount == null ? sAccountManager.get(mAccountId) : sAccountManager.validate(mAccount);
  }

  @Override
  @JsonIgnore
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
  public String getModifiedBy() {
    return mModifiedBy;
  }

  @Override
  public void setModifiedBy(String pModifiedBy) {
    this.mModifiedBy = pModifiedBy;
  }

  @Override
  public String getModifierName() {
    return sPersonalInformationManager.get(mModifiedBy).getName();
  }

  @Override
  public void setModifierName(String pModifierName) {
    this.mModifierName = pModifierName;
  }

  @Override
  @JsonProperty
  @JsonSerialize(using = UmsDateSerializer.class)
  public Date getModifiedDate() {
    return mModifiedDate;
  }

  @Override
  @JsonIgnore
  public void setModifiedDate(Date pModifiedDate) {
    this.mModifiedDate = pModifiedDate;
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
    return sSystemAccountMapManager.create(this);
  }

  @Override
  public void update() {
    sSystemAccountMapManager.update(this);
  }

  @Override
  public MutableSystemAccountMap edit() {
    return new PersistentSystemAccountMap(this);
  }

  @Override
  public void delete() {
    sSystemAccountMapManager.delete(this);
  }

  public PersistentSystemAccountMap() {}

  public PersistentSystemAccountMap(MutableSystemAccountMap pSystemAccountMap) {
    setId(pSystemAccountMap.getId());
    setAccountType(pSystemAccountMap.getAccountType());
    setAccount(pSystemAccountMap.getAccount());
    setAccountId(pSystemAccountMap.getAccountId());
    setModifiedBy(pSystemAccountMap.getModifiedBy());
    setModifierName(pSystemAccountMap.getModifierName());
    setModifiedDate(pSystemAccountMap.getModifiedDate());
    setLastModified(pSystemAccountMap.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAccountManager = applicationContext.getBean("accountManager", AccountManager.class);
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);
    sPersonalInformationManager =
        applicationContext.getBean("personalInformationManager", PersonalInformationManager.class);
    sSystemAccountMapManager = applicationContext.getBean("systemAccountMapManager", SystemAccountMapManager.class);
  }
}
