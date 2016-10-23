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
}
