package org.ums.persistent.model.accounts;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.manager.accounts.GroupManager;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 20-Dec-17.
 */
public class PersistentGroup implements MutableGroup{

  private static GroupManager sGroupManager;

  static{
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sGroupManager = applicationContext.getBean("groupManager", GroupManager.class);
  }

  private Long mId;
  private String mCompanyCode;
  private String mGroupCode;
  private String mGroupName;
  private String mMainGroup;
  private String mReservedFlag;
  private String mFlag;
  private double mTaxLimit;
  private double mTdsPercent;
  private String mDefaultCompanyCode;
  private char mStatusFlag;
  private char mStatusUpFlag;
  private Date mModifiedDate;
  private String mAuthCode;

  public PersistentGroup() {
  }

  public PersistentGroup(final PersistentGroup pPersistentGroup){
    mId  = pPersistentGroup.getId();
    mCompanyCode = pPersistentGroup.getCompanyCode();
    mGroupCode = pPersistentGroup.getGroupCode();
    mGroupName = pPersistentGroup.getGroupName();
    mMainGroup = pPersistentGroup.getMainGroup();
    mReservedFlag = pPersistentGroup.getReservedFlag();
    mFlag = pPersistentGroup.getFlag();
    mTaxLimit = pPersistentGroup.getTaxLimit();
    mTdsPercent = pPersistentGroup.getTaxPercent();
    mDefaultCompanyCode = pPersistentGroup.getDefaultCompanyCode();
    mStatusFlag = pPersistentGroup.getStatusFlag();
    mStatusUpFlag = pPersistentGroup.getStatusUpFlag();
    mModifiedDate = pPersistentGroup.getLastModifiedDate();
    mAuthCode = pPersistentGroup.getAuthenticationCode();
  }


  @Override
  public void setCompanyCode(String pCompanyCode) {
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
  public void setTexLimit(double pTexLimit) {
    mTaxLimit = pTexLimit;
  }

  @Override
  public void setTdsPercent(double pTdsPercent) {
    mTdsPercent = pTdsPercent;
  }

  @Override
  public void setDefaultCompanyCode(String pDefaultCompanyCode) {
    mDefaultCompanyCode = pDefaultCompanyCode;
  }

  @Override
  public void setStatusFlag(char pStatusFlag) {
    mStatusFlag = pStatusFlag;
  }

  @Override
  public void setStatusUpFlag(char pStatusUpFlag) {
    mStatusUpFlag = pStatusUpFlag;
  }

  @Override
  public void setLastModifiedDate(Date pLastModifiedDate) {
    mModifiedDate = pLastModifiedDate;
  }

  @Override
  public void setAuthenticationCode(String pAuthenticationCode) {
    mAuthCode = pAuthenticationCode;
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
  public String getCompanyCode() {
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
  public double getTaxLimit() {
    return mTaxLimit;
  }

  @Override
  public double getTaxPercent() {
    return mTdsPercent;
  }

  @Override
  public String getDefaultCompanyCode() {
    return mDefaultCompanyCode;
  }

  @Override
  public char getStatusFlag() {
    return mStatusFlag;
  }

  @Override
  public char getStatusUpFlag() {
    return mStatusUpFlag;
  }

  @Override
  public Date getLastModifiedDate() {
    return mModifiedDate;
  }

  @Override
  public String getAuthenticationCode() {
    return mAuthCode;
  }

  @Override
  public MutableGroup edit() {
    return new PersistentGroup(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
