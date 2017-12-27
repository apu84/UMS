package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.manager.accounts.GroupManager;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 20-Dec-17.
 */
public class PersistentGroup implements MutableGroup {

  private static GroupManager sGroupManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sGroupManager = applicationContext.getBean("groupManager", GroupManager.class);
  }

  private Long mId;
  private String mStringId;
  private String mCompanyCode;
  private String mGroupCode;
  private String mGroupName;
  private String mMainGroup;
  private String mReservedFlag;
  private String mFlag;
  private BigDecimal mTaxLimit;
  private BigDecimal mTdsPercent;
  private String mDefaultComp;
  private String mStatusFlag;
  private String mStatusUpFlag;
  private Date mModifiedDate;
  private String mModifiedBy;
  @JsonIgnore
  private String mMainGroupObject;
  @JsonIgnore
  private Boolean mFlagBoolValue;

  public PersistentGroup() {
  }

  public PersistentGroup(final PersistentGroup pPersistentGroup) {
    mId = pPersistentGroup.getId();
    mCompanyCode = pPersistentGroup.getCompCode();
    mGroupCode = pPersistentGroup.getGroupCode();
    mGroupName = pPersistentGroup.getGroupName();
    mMainGroup = pPersistentGroup.getMainGroup();
    mReservedFlag = pPersistentGroup.getReservedFlag();
    mFlag = pPersistentGroup.getFlag();
    mTaxLimit = pPersistentGroup.getTaxLimit();
    mTdsPercent = pPersistentGroup.getTdsPercent();
    mDefaultComp = pPersistentGroup.getDefaultComp();
    mStatusFlag = pPersistentGroup.getStatFlag();
    mStatusUpFlag = pPersistentGroup.getStatUpFlag();
    mModifiedDate = pPersistentGroup.getModifiedDate();
    mModifiedBy = pPersistentGroup.getModifiedBy();
  }

  public String getMainGroupObject() {
    return mMainGroupObject;
  }

  public void setMainGroupObject(String pMainGroupObject) {
    mMainGroupObject = pMainGroupObject;
  }

  public Boolean getFlagBoolValue() {
    return mFlagBoolValue;
  }

  public void setFlagBoolValue(Boolean pFlagBoolValue) {
    mFlagBoolValue = pFlagBoolValue;
  }

  @Override
  public void setStringId(Long pId) {
    mStringId = pId.toString();
  }

  @Override
  public String getStringId() {
    return mStringId;
  }

  @Override
  public void setCompCode(String pCompanyCode) {
    mCompanyCode = pCompanyCode;
  }

  @Override
  public void setGroupCode(String pGroupCode) {
    mGroupCode = pGroupCode;
  }

  @Override
  public void setGroupName(String pGroupName) {
    mGroupName = pGroupName;
  }

  @Override
  public void setMainGroup(String pMainGroup) {
    mMainGroup = pMainGroup;
  }

  @Override
  public void setReservedFlag(String pReservedFlag) {
    mReservedFlag = pReservedFlag;
  }

  @Override
  public void setFlag(String pFlag) {
    mFlag = pFlag;
  }

  @Override
  public void setTaxLimit(BigDecimal pTexLimit) {
    mTaxLimit = pTexLimit;
  }

  @Override
  public void setTdsPercent(BigDecimal pTdsPercent) {
    mTdsPercent = pTdsPercent;
  }

  @Override
  public void setDefaultComp(String pDefaultCompCode) {
    mDefaultComp = pDefaultCompCode;
  }

  @Override
  public void setStatFlag(String pStatusFlag) {
    mStatusFlag = pStatusFlag;
  }

  @Override
  public void setStatUpFlag(String pStatusUpFlag) {
    mStatusUpFlag = pStatusUpFlag;
  }

  @Override
  public void setModifiedDate(Date pLastModifiedDate) {
    mModifiedDate = pLastModifiedDate;
  }

  @Override
  public void setModifiedBy(String pAuthenticationCode) {
    mModifiedBy = pAuthenticationCode;
  }

  @Override
  public Long create() {
    return sGroupManager.create(this);
  }

  @Override
  public void update() {
    sGroupManager.update(this);
  }

  @Override
  public void delete() {
    sGroupManager.delete(this);
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public String getCompCode() {
    return mCompanyCode;
  }

  @Override
  public String getGroupCode() {
    return mGroupCode;
  }

  @Override
  public String getGroupName() {
    return mGroupName;
  }

  @Override
  public String getMainGroup() {
    return mMainGroup;
  }

  @Override
  public String getReservedFlag() {
    return mReservedFlag;
  }

  @Override
  public String getFlag() {
    return mFlag;
  }

  @Override
  public BigDecimal getTaxLimit() {
    return mTaxLimit;
  }

  @Override
  public BigDecimal getTdsPercent() {
    return mTdsPercent;
  }

  @Override
  public String getDefaultComp() {
    return mDefaultComp;
  }

  @Override
  public String getStatFlag() {
    return mStatusFlag;
  }

  @Override
  public String getStatUpFlag() {
    return mStatusUpFlag;
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
  public MutableGroup edit() {
    return new PersistentGroup(this);
  }

  @Override
  public Long getId() {
    return Long.parseLong(mStringId);
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
