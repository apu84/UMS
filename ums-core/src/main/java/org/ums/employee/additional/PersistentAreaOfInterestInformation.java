package org.ums.employee.additional;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentAreaOfInterestInformation implements MutableAreaOfInterestInformation {

  private static AreaOfInterestInformationManager sAreaOfInterestInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAreaOfInterestInformationManager =
        applicationContext.getBean("areaOfInterestInformationManager", AreaOfInterestInformationManager.class);
  }

  private String mId;
  private String mAreaOfInterest;
  private String mLastModified;

  public PersistentAreaOfInterestInformation() {};

  public PersistentAreaOfInterestInformation(PersistentAreaOfInterestInformation pPersistentAreaOfInterestInformation) {
    mAreaOfInterest = pPersistentAreaOfInterestInformation.getAreaOfInterest();
    mLastModified = pPersistentAreaOfInterestInformation.getLastModified();
  }

  @Override
  public MutableAreaOfInterestInformation edit() {
    return new PersistentAreaOfInterestInformation(this);
  }

  @Override
  public String create() {
    return sAreaOfInterestInformationManager.create(this);
  }

  @Override
  public void update() {
    sAreaOfInterestInformationManager.update(this);
  }

  @Override
  public void delete() {
    sAreaOfInterestInformationManager.delete(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
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
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void setAreaOfInterest(String pAreaOfInterest) {
    mAreaOfInterest = pAreaOfInterest;
  }

  @Override
  public String getAreaOfInterest() {
    return mAreaOfInterest;
  }
}
