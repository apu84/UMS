package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.manager.FCMTokenManager;

import java.util.Date;

public class PersistentFCMToken implements MutableFCMToken {

  private static FCMTokenManager sFCMTokenManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFCMTokenManager = applicationContext.getBean("FCMTokenManager", FCMTokenManager.class);
  }

  private Long mId;
  private String mUserId;
  private String mFCMToken;
  private Date mTokenDeletedOn;
  private String mLastModified;

  public PersistentFCMToken() {}

  public PersistentFCMToken(PersistentFCMToken pPersistentFCMToken) {
    mId = pPersistentFCMToken.getId();
    mUserId = pPersistentFCMToken.getUserId();
    mFCMToken = pPersistentFCMToken.getFCMToken();
    mTokenDeletedOn = pPersistentFCMToken.getTokenDeleteOn();
    mLastModified = pPersistentFCMToken.getLastModified();
  }

  @Override
  public MutableFCMToken edit() {
    return new PersistentFCMToken(this);
  }

  @Override
  public Long create() {
    return sFCMTokenManager.create(this);
  }

  @Override
  public void update() {
    sFCMTokenManager.update(this);
  }

  @Override
  public void delete() {
    sFCMTokenManager.delete(this);
  }

  @Override
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
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void setUserId(String pUserId) {
    mUserId = pUserId;
  }

  @Override
  public void setFCMToken(String pFCMToken) {
    mFCMToken = pFCMToken;
  }

  @Override
  public void setTokenDeletedOn(Date pTokenDeletedOn) {
    mTokenDeletedOn = pTokenDeletedOn;
  }

  @Override
  public String getUserId() {
    return mUserId;
  }

  @Override
  public String getFCMToken() {
    return mFCMToken;
  }

  @Override
  public Date getTokenDeleteOn() {
    return mTokenDeletedOn;
  }
}
