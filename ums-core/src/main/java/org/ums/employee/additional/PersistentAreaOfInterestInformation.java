package org.ums.employee.additional;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.AreaOfInterest;
import org.ums.manager.registrar.AreaOfInterestInformationManager;

public class PersistentAreaOfInterestInformation implements MutableAreaOfInterestInformation {

  private static AreaOfInterestInformationManager sAreaOfInterestInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAreaOfInterestInformationManager =
        applicationContext.getBean("areaOfInterestInformationManager", AreaOfInterestInformationManager.class);
  }

  private String mId;
  private String mEmployeeId;
  private AreaOfInterest mAreaOfInterest;
  private int mAreaOfInterestId;
  private String mLastModified;

  public PersistentAreaOfInterestInformation() {};

  public PersistentAreaOfInterestInformation(PersistentAreaOfInterestInformation pPersistentAreaOfInterestInformation) {
    mEmployeeId = pPersistentAreaOfInterestInformation.getEmployeeId();
    mAreaOfInterest = pPersistentAreaOfInterestInformation.getAreaOfInterest();
    mAreaOfInterestId = pPersistentAreaOfInterestInformation.getAreaOfInterestId();
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
    return null;
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
  public String getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public AreaOfInterest getAreaOfInterest() {
    return mAreaOfInterest;
  }

  @Override
  public Integer getAreaOfInterestId() {
    return mAreaOfInterestId;
  }

  @Override
  public void setEmployeeId(String pEmployeeId) {
    mEmployeeId = pEmployeeId;
  }

  @Override
  public void setAreaOfInterest(AreaOfInterest pAreaOfInterest) {
    mAreaOfInterest = pAreaOfInterest;
  }

  @Override
  public void setAreaOfInterestId(Integer pAreaOfInterestId) {
    mAreaOfInterestId = pAreaOfInterestId;
  }
}
