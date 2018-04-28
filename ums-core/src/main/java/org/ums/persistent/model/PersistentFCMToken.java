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
    sFCMTokenManager = applicationContext.getBean("fcmTokenManager", FCMTokenManager.class);
  }

  private String mId;
  private String mFCMToken;
  private Date mTokenLastRefreshedOn;
  private Date mTokenDeletedOn;
  private String mLastModified;

  public PersistentFCMToken() {}

  public PersistentFCMToken(PersistentFCMToken pPersistentFCMToken) {
    mId = pPersistentFCMToken.getId();
    mFCMToken = pPersistentFCMToken.getFCMToken();
    mTokenLastRefreshedOn = pPersistentFCMToken.getTokenLastRefreshedOn();
    mTokenDeletedOn = pPersistentFCMToken.getTokenDeleteOn();
    mLastModified = pPersistentFCMToken.getLastModified();
  }

  @Override
  public MutableFCMToken edit() {
    return new PersistentFCMToken(this);
  }

  @Override
  public String create() {
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
  public String getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setId(String pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void setFCMToken(String pFCMToken) {
    mFCMToken = pFCMToken;
  }

  @Override
  public void setTokenLastRefreshedOn(Date pTokenLastRefreshedOn) {
    mTokenLastRefreshedOn = pTokenLastRefreshedOn;
  }

  @Override
  public void setTokenDeletedOn(Date pTokenDeletedOn) {
    mTokenDeletedOn = pTokenDeletedOn;
  }

  @Override
  public String getFCMToken() {
    return mFCMToken;
  }

  @Override
  public Date getTokenLastRefreshedOn() {
    return mTokenLastRefreshedOn;
  }

  @Override
  public Date getTokenDeleteOn() {
    return mTokenDeletedOn;
  }
}
