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
}
