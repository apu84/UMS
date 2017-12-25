package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableOtp;
import org.ums.manager.OtpManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;

public class PersistentOtp implements MutableOtp {
  private static OtpManager sOtpManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sOtpManager = applicationContext.getBean("otpManager", OtpManager.class);
  }

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

  public PersistentOtp() {}

  public PersistentOtp(MutableOtp pOtp) {
    setId(pOtp.getId());
    setUserId(pOtp.getUserId());
    setType(pOtp.getType());
    setUsed(pOtp.isUsed());
    setGeneratedOn(pOtp.getGeneratedOn());
    setExpiredOn(pOtp.getExpiredOn());
    setUsedOn(pOtp.getUsedOn());
    setTryCount(pOtp.getTryCount());
    setOtp(pOtp.getOtp());
    setLastModified(pOtp.getLastModified());
  }

  @Override
  public Long create() {
    return sOtpManager.create(this);
  }

  @Override
  public void update() {
    sOtpManager.update(this);
  }

  @Override
  public void delete() {
    throw new NotImplementedException();
  }

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

  public MutableOtp edit() {
    return new PersistentOtp(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }
}
