package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableAreaOfInterest;
import org.ums.manager.AreaOfInterestManager;

public class PersistentAreaOfInterest implements MutableAreaOfInterest {

  private static AreaOfInterestManager sAreaOfInterestManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAreaOfInterestManager = applicationContext.getBean("areaOfInterestManager", AreaOfInterestManager.class);
  }

  private int mId;
  private String mAreaOfInterest;
  private String mLastModified;

  public PersistentAreaOfInterest() {}

  public PersistentAreaOfInterest(PersistentAreaOfInterest pPersistentAreaOfInterest) {
    mId = pPersistentAreaOfInterest.getId();
    mAreaOfInterest = pPersistentAreaOfInterest.getAreaOfInterest();
    mLastModified = pPersistentAreaOfInterest.getLastModified();
  }

  @Override
  public MutableAreaOfInterest edit() {
    return new PersistentAreaOfInterest(this);
  }

  @Override
  public Integer create() {
    return sAreaOfInterestManager.create(this);
  }

  @Override
  public void update() {
    sAreaOfInterestManager.update(this);
  }

  @Override
  public void delete() {
    sAreaOfInterestManager.delete(this);
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
  public void seTAreaOfInterest(String pAreaOfInterest) {
    mAreaOfInterest = pAreaOfInterest;
  }

  @Override
  public String getAreaOfInterest() {
    return mAreaOfInterest;
  }
}
