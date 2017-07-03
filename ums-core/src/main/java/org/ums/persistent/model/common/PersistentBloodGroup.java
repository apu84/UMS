package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.common.MutableBloodGroup;
import org.ums.manager.common.BloodGroupManager;

public class PersistentBloodGroup implements MutableBloodGroup {

  private static BloodGroupManager sBloodGroupManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sBloodGroupManager = applicationContext.getBean("bloodGroupManager", BloodGroupManager.class);
  }

  private Integer mId;
  private String mBloodGroup;
  private String mLastModified;

  public PersistentBloodGroup() {}

  public PersistentBloodGroup(PersistentBloodGroup pPersistentBloodGroup) {
    mId = pPersistentBloodGroup.getId();
    mBloodGroup = pPersistentBloodGroup.getBloodGroup();
    mLastModified = pPersistentBloodGroup.getLastModified();
  }

  @Override
  public Integer create() {
    return sBloodGroupManager.create(this);
  }

  @Override
  public void update() {
    sBloodGroupManager.update(this);
  }

  @Override
  public void delete() {
    sBloodGroupManager.delete(this);
  }

  @Override
  public MutableBloodGroup edit() {
    return new PersistentBloodGroup(this);
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
  public void setBloodGroup(String pBloodGroup) {
    mBloodGroup = pBloodGroup;
  }

  @Override
  public String getBloodGroup() {
    return mBloodGroup;
  }
}
