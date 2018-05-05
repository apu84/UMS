package org.ums.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UMSConfiguration {
  @Value("${course.material.owner.only.modification}")
  private boolean mOwnerOnlyModification;

  @Value("${binary.storageRoot}")
  private String mStorageRoot;

  @Value("${enable.objectDb}")
  private boolean mEnableObjectDb;

  @Value("${notification.service.enabled}")
  private boolean mNotificationServiceEnabled;

  @Value("${pollingInterval}")
  private int mPollingInterval;

  @Value("${admin.user}")
  private String mAdminUser;

  @Value("${backend.user}")
  private String mBackendUser;

  @Value("${backend.user.password}")
  private String mBackendUserPassword;

  @Value("${enable.cache.warmer}")
  private boolean mEnableCacheWarmer;

  @Value("${process.gpa.only}")
  private boolean mProcessGPAOnly;

  @Value("${developer.mode}")
  private boolean mDeveloperMode;

  @Value("${data.dir}")
  private String mDataDir;

  @Value("${twoFa.token.lifeTime}")
  private int mTwoFATokenLifeTime;

  @Value("${twoFa.token.allowable.wrongTry}")
  private int mTwoFATokenAllowableWrongTry;

  @Value("${accounts.jdbc.url}")
  private String mAccountJdbcUrl;

  @Value("${accounts.jdbc.username}")
  private String mAccountJdbcUserName;

  @Value("${accounts.jdbc.password}")
  private String mAccountJdbcPassword;

  @Value("${fcm.service-account-file}")
  private String mFcmServiceFile;

  public boolean isOwnerOnlyModification() {
    return mOwnerOnlyModification;
  }

  public String getStorageRoot() {
    return mStorageRoot;
  }

  public boolean isEnableObjectDb() {
    return mEnableObjectDb;
  }

  public void setEnableObjectDb(boolean pEnableObjectDb) {
    mEnableObjectDb = pEnableObjectDb;
  }

  public boolean isNotificationServiceEnabled() {
    return mNotificationServiceEnabled;
  }

  public int getPollingInterval() {
    return mPollingInterval;
  }

  public String getAdminUser() {
    return mAdminUser;
  }

  public String getBackendUser() {
    return mBackendUser;
  }

  public String getBackendUserPassword() {
    return mBackendUserPassword;
  }

  public boolean isEnableCacheWarmer() {
    return mEnableCacheWarmer;
  }

  public boolean isProcessGPAOnly() {
    return mProcessGPAOnly;
  }

  public boolean isDeveloperMode() {
    return mDeveloperMode;
  }

  public String getDataDir() {
    return mDataDir;
  }

  public int getTwoFATokenLifeTime() {
    return mTwoFATokenLifeTime * 60;
  }

  public int getTwoFATokenAllowableWrongTry() {
    return mTwoFATokenAllowableWrongTry;
  }

  public String getAccountJdbcUrl() {
    return mAccountJdbcUrl;
  }

  public String getAccountJdbcUserName() {
    return mAccountJdbcUserName;
  }

  public String getAccountJdbcPassword() {
    return mAccountJdbcPassword;
  }

  public String getFcmServiceFile() {
    return mFcmServiceFile;
  }
}
