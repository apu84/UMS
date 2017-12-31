package org.ums.twofa;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

public class PersistentTwoFAToken implements MutableTwoFAToken {

  private static UserManager sUserManager;
  private static TwoFATokenManager sTwoFATokenManager;
  private Long mId;
  private String mState;
  private String mToken;
  private Date mTokenExpiry;
  private User mUser;
  private String mUserId;
  private String mLastModified;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public String getState() {
    return mState;
  }

  @Override
  public void setState(String pState) {
    this.mState = pState;
  }

  @Override
  public String getToken() {
    return mToken;
  }

  @Override
  public void setToken(String pToken) {
    this.mToken = pToken;
  }

  @Override
  public Date getTokenExpiry() {
    return mTokenExpiry;
  }

  public void setTokenExpiry(Date pTokenExpiry) {
    this.mTokenExpiry = pTokenExpiry;
  }

  @Override
  public User getUser() {
    return mUser == null ? sUserManager.get(mUserId) : sUserManager.validate(mUser);
  }

  @Override
  public void setUser(User pUser) {
    this.mUser = pUser;
  }

  @Override
  public String getUserId() {
    return mUserId;
  }

  @Override
  public void setUserId(String pUserId) {
    this.mUserId = pUserId;
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
    return sTwoFATokenManager.create(this);
  }

  @Override
  public void update() {
    sTwoFATokenManager.update(this);
  }

  @Override
  public MutableTwoFAToken edit() {
    return new PersistentTwoFAToken(this);
  }

  @Override
  public void delete() {
    sTwoFATokenManager.delete(this);
  }

  public PersistentTwoFAToken() {}

  public PersistentTwoFAToken(MutableTwoFAToken pTwoFAToken) {
    setId(pTwoFAToken.getId());
    setState(pTwoFAToken.getState());
    setToken(pTwoFAToken.getToken());
    setTokenExpiry(pTwoFAToken.getTokenExpiry());
    setUser(pTwoFAToken.getUser());
    setUserId(pTwoFAToken.getUserId());
    setLastModified(pTwoFAToken.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sUserManager = applicationContext.getBean("userManager", UserManager.class);
    sTwoFATokenManager = applicationContext.getBean("twoFATokenManager", TwoFATokenManager.class);
  }
}
