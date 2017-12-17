package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.library.MutableCheckIn;
import org.ums.manager.library.CheckInManager;

import java.util.Date;

public class PersistentCheckIn implements MutableCheckIn {

  private static CheckInManager sCheckInManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCheckInManager = applicationContext.getBean("checkInManager", CheckInManager.class);
  }

  private Long mId;
  private Long mCheckoutId;
  private Date mReturnDate;
  private String mLastModified;

  public PersistentCheckIn() {}

  public PersistentCheckIn(PersistentCheckIn persistentCheckIn) {
    mId = persistentCheckIn.getId();
    mCheckoutId = persistentCheckIn.getCheckoutId();
    mReturnDate = persistentCheckIn.mReturnDate;
    mLastModified = persistentCheckIn.mLastModified;
  }

  @Override
  public MutableCheckIn edit() {
    return new PersistentCheckIn(this);
  }

  @Override
  public Long create() {
    return sCheckInManager.create(this);
  }

  @Override
  public void update() {
    sCheckInManager.update(this);
  }

  @Override
  public void delete() {
    sCheckInManager.delete(this);
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
  public void setCheckoutId(Long pCheckoutId) {
    mCheckoutId = pCheckoutId;
  }

  @Override
  public void setReturnDate(Date pReturnDate) {
    mReturnDate = pReturnDate;
  }

  @Override
  public Long getCheckoutId() {
    return mCheckoutId;
  }

  @Override
  public Date getReturnDate() {
    return mReturnDate;
  }
}
