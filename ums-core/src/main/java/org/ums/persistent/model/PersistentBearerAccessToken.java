package org.ums.persistent.model;


import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;

import java.util.Date;

public class PersistentBearerAccessToken implements MutableBearerAccessToken {
  private static BearerAccessTokenManager sBearerAccessTokenManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sBearerAccessTokenManager = applicationContext.getBean("bearerAccessTokenManager", BearerAccessTokenManager.class);
  }

  private String mId;
  private String mUserId;
  private Date mLastAccessTime;

  public PersistentBearerAccessToken() {
  }

  public PersistentBearerAccessToken(final PersistentBearerAccessToken pPersistentBearerAccessToken) {
    setId(pPersistentBearerAccessToken.getId());
    setUserId(pPersistentBearerAccessToken.getUserId());
    setLastAccessedTime(pPersistentBearerAccessToken.getLastAccessTime());
  }

  @Override
  public void setUserId(String pUserId) {
    mUserId = pUserId;
  }

  @Override
  public void setLastAccessedTime(Date pDate) {
    mLastAccessTime = pDate;
  }

  @Override
  public String getUserId() {
    return mUserId;
  }

  @Override
  public Date getLastAccessTime() {
    return mLastAccessTime;
  }

  @Override
  public MutableBearerAccessToken edit() throws Exception {
    return new PersistentBearerAccessToken(this);
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void setId(String pId) {
    mId = pId;
  }

  @Override
  public void commit(boolean update) throws Exception {
    if (update) {
      sBearerAccessTokenManager.update(this);
    } else {
      sBearerAccessTokenManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sBearerAccessTokenManager.delete(this);
  }
}
