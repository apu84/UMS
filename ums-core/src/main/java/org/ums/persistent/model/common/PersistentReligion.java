package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.common.MutableReligion;
import org.ums.manager.common.ReligionManager;

public class PersistentReligion implements MutableReligion {

  private static ReligionManager sReligionManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sReligionManager = applicationContext.getBean("religionManager", ReligionManager.class);
  }

  private Integer mId;
  private String mReligion;
  private String mLastModified;

  public PersistentReligion() {}

  public PersistentReligion(PersistentReligion pPersistentReligion) {

    mId = pPersistentReligion.getId();
    mReligion = pPersistentReligion.getReligion();
    mLastModified = pPersistentReligion.getLastModified();
  }

  @Override
  public Integer create() {
    return sReligionManager.create(this);
  }

  @Override
  public void update() {
    sReligionManager.update(this);
  }

  @Override
  public void delete() {
    sReligionManager.delete(this);
  }

  @Override
  public MutableReligion edit() {
    return new PersistentReligion(this);
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
  public void setReligion(String pReligion) {
    mReligion = pReligion;
  }

  @Override
  public String getReligion() {
    return mReligion;
  }
}
