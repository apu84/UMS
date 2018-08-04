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
  private String mToken;
  private Date mCreatedOn;
  private String mLastModified;

  public PersistentFCMToken() {}

  public PersistentFCMToken(PersistentFCMToken pPersistentFCMToken) {
    mId = pPersistentFCMToken.getId();
    mToken = pPersistentFCMToken.getToken();
    mCreatedOn = pPersistentFCMToken.getCreatedOn();
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
  public void setToken(String pToken) {
    mToken = pToken;
  }

  @Override
  public void setCreatedOn(Date pCreatedOn) {
    mCreatedOn = pCreatedOn;
  }

  @Override
  public String getToken() {
    return mToken;
  }

  @Override
  public Date getCreatedOn() {
    return mCreatedOn;
  }
}
