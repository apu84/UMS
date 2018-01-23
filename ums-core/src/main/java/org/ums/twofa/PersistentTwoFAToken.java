package org.ums.twofa;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import java.util.Date;

public class PersistentTwoFAToken implements MutableTwoFAToken {

  private static UserManager sUserManager;
  private static TwoFATokenManager sTwoFATokenManager;

  private Long mId;
  private String mUserId;
  private String mType;
  private boolean mIsUsed;
  private Date mGeneratedOn;
  private Date mExpiredOn;
  private Date mUsedOn;
  private int mTryCount;
  private String mOtp;
  private String mLastModified;
  private User mUser;

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public String getUserId() {
    return mUserId;
  }

  @Override
  public void setUserId(String pUserId) {
    mUserId = pUserId;
  }

  @Override
  public String getType() {
    return mType;
  }

  @Override
  public void setType(String pType) {
    mType = pType;
  }

  @Override
  public boolean isUsed() {
    return mIsUsed;
  }

  @Override
  public void setUsed(boolean pIsUsed) {
    this.mIsUsed = pIsUsed;
  }

  @Override
  public Date getGeneratedOn() {
    return mGeneratedOn;
  }

  @Override
  public void setGeneratedOn(Date pGeneratedOn) {
    mGeneratedOn = pGeneratedOn;
  }

  @Override
  public Date getExpiredOn() {
    return mExpiredOn;
  }

  @Override
  public void setExpiredOn(Date pExpiredOn) {
    mExpiredOn = pExpiredOn;
  }

  @Override
  public Date getUsedOn() {
    return mUsedOn;
  }

  @Override
  public void setUsedOn(Date pUsedOn) {
    mUsedOn = pUsedOn;
  }

  @Override
  public int getTryCount() {
    return mTryCount;
  }

  @Override
  public void setTryCount(int pTryCount) {
    mTryCount = pTryCount;
  }

  @Override
  public String getOtp() {
    return mOtp;
  }

  @Override
  public void setOtp(String pOtp) {
    this.mOtp = pOtp;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
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
    setUserId(pTwoFAToken.getUserId());
    setType(pTwoFAToken.getType());
    setUsed(pTwoFAToken.isUsed());
    setGeneratedOn(pTwoFAToken.getGeneratedOn());
    setExpiredOn(pTwoFAToken.getExpiredOn());
    setUsedOn(pTwoFAToken.getUsedOn());
    setTryCount(pTwoFAToken.getTryCount());
    setOtp(pTwoFAToken.getOtp());
    setLastModified(pTwoFAToken.getLastModified());
    setUser(pTwoFAToken.getUser());
    setUserId(pTwoFAToken.getUserId());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sUserManager = applicationContext.getBean("userManager", UserManager.class);
    sTwoFATokenManager = applicationContext.getBean("twoFATokenManager", TwoFATokenManager.class);
  }
}
