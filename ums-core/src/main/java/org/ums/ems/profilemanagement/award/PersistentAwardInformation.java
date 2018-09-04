package org.ums.ems.profilemanagement.award;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentAwardInformation implements MutableAwardInformation {
  private static AwardInformationManager sAwardInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAwardInformationManager = applicationContext.getBean("awardInformationManager", AwardInformationManager.class);
  }

  private Long mId;
  private String mEmployeeId;
  private String mAwardName;
  private String mAwardInstitute;
  private int mAwardedYear;
  private String mAwardShortDescription;
  private String mLastModified;

  public PersistentAwardInformation() {}

  public PersistentAwardInformation(PersistentAwardInformation pPersistentAwardInformation) {
    mId = pPersistentAwardInformation.getId();
    mEmployeeId = pPersistentAwardInformation.getEmployeeId();
    mAwardName = pPersistentAwardInformation.getAwardName();
    mAwardInstitute = pPersistentAwardInformation.getAwardInstitute();
    mAwardedYear = pPersistentAwardInformation.getAwardedYear();
    mAwardShortDescription = pPersistentAwardInformation.getAwardShortDescription();
    mLastModified = pPersistentAwardInformation.getLastModified();
  }

  @Override
  public MutableAwardInformation edit() {
    return new PersistentAwardInformation(this);
  }

  @Override
  public Long create() {
    return sAwardInformationManager.create(this);
  }

  @Override
  public void update() {
    sAwardInformationManager.update(this);
  }

  @Override
  public void delete() {
    sAwardInformationManager.delete(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
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
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void setEmployeeId(String pEmployeeId) {
    mEmployeeId = pEmployeeId;
  }

  @Override
  public void setAwardName(String pAwardName) {
    mAwardName = pAwardName;
  }

  @Override
  public void setAwardInstitute(String pAwardInstitute) {
    mAwardInstitute = pAwardInstitute;
  }

  @Override
  public void setAwardedYear(int pAwardedYear) {
    mAwardedYear = pAwardedYear;
  }

  @Override
  public void setAwardShortDescription(String pAwardShortDescription) {
    mAwardShortDescription = pAwardShortDescription;
  }

  @Override
  public String getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public String getAwardName() {
    return mAwardName;
  }

  @Override
  public String getAwardInstitute() {
    return mAwardInstitute;
  }

  @Override
  public int getAwardedYear() {
    return mAwardedYear;
  }

  @Override
  public String getAwardShortDescription() {
    return mAwardShortDescription;
  }
}
