package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.common.MutableMaritalStatus;
import org.ums.manager.common.MaritalStatusManager;

public class PersistentMaritalStatus implements MutableMaritalStatus {

  private static MaritalStatusManager sMaritalStatusManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sMaritalStatusManager = applicationContext.getBean("maritalStatusManager", MaritalStatusManager.class);
  }

  private Integer mId;
  private String mMaritalStatus;
  private String mLastModified;

  public PersistentMaritalStatus() {}

  public PersistentMaritalStatus(PersistentMaritalStatus pPersistentMaritalStatus) {
    mId = pPersistentMaritalStatus.getId();
    mMaritalStatus = pPersistentMaritalStatus.getMaritalStatus();
    mLastModified = pPersistentMaritalStatus.getLastModified();
  }

  @Override
  public MutableMaritalStatus edit() {
    return new PersistentMaritalStatus(this);
  }

  @Override
  public Integer create() {
    return sMaritalStatusManager.create(this);
  }

  @Override
  public void update() {
    sMaritalStatusManager.update(this);
  }

  @Override
  public void delete() {
    sMaritalStatusManager.delete(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void setMaritalStatus(String pMaritalStatus) {
    mMaritalStatus = pMaritalStatus;
  }

  @Override
  public String getMaritalStatus() {
    return mMaritalStatus;
  }
}
