package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.library.MutableRecordLog;
import org.ums.manager.library.RecordLogManager;

import java.util.Date;

public class PersistentRecordLog implements MutableRecordLog {

  private static RecordLogManager sRecordLogManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sRecordLogManager = applicationContext.getBean("recordLogManager", RecordLogManager.class);
  }

  private Long mId;
  private Long mMfn;
  private String mModifiedBy;
  private Date mModifiedOn;
  private String mModification;
  private String mLastModified;

  public PersistentRecordLog() {}

  public PersistentRecordLog(PersistentRecordLog pPersistentRecordLog) {
    mId = pPersistentRecordLog.getId();
    mMfn = pPersistentRecordLog.getMfn();
    mModifiedBy = pPersistentRecordLog.getModifiedBy();
    mModifiedOn = pPersistentRecordLog.getModifiedOn();
    mModification = pPersistentRecordLog.getModification();
    mLastModified = pPersistentRecordLog.getLastModified();
  }

  @Override
  public MutableRecordLog edit() {
    return new PersistentRecordLog(this);
  }

  @Override
  public Long create() {
    return sRecordLogManager.create(this);
  }

  @Override
  public void update() {
    sRecordLogManager.update(this);
  }

  @Override
  public void delete() {
    sRecordLogManager.delete(this);
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
  public void setMfn(Long pMfn) {
    mMfn = pMfn;
  }

  @Override
  public void setModifiedBy(String pModifiedBy) {
    mModifiedBy = pModifiedBy;
  }

  @Override
  public void setModifiedOn(Date pModifiedOn) {
    mModifiedOn = pModifiedOn;
  }

  @Override
  public void setModification(String pModification) {
    mModification = pModification;
  }

  @Override
  public Long getMfn() {
    return mMfn;
  }

  @Override
  public String getModifiedBy() {
    return mModifiedBy;
  }

  @Override
  public Date getModifiedOn() {
    return mModifiedOn;
  }

  @Override
  public String getModification() {
    return mModification;
  }
}
