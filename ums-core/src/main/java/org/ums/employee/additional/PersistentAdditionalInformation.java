package org.ums.employee.additional;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentAdditionalInformation implements MutableAdditionalInformation {

  private static AdditionalInformationManager sAdditionalInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAdditionalInformationManager =
        applicationContext.getBean("additionalInformationManager", AdditionalInformationManager.class);
  }
  private String mId;
  private String mRoomNo;
  private String mExtNo;
  private String mAcademicInitial;
  private String mLastModified;

  public PersistentAdditionalInformation() {}

  public PersistentAdditionalInformation(PersistentAdditionalInformation pPersistentAdditionalInformation) {
    mId = pPersistentAdditionalInformation.getId();
    mRoomNo = pPersistentAdditionalInformation.getRoomNo();
    mExtNo = pPersistentAdditionalInformation.getExtNo();
    mAcademicInitial = pPersistentAdditionalInformation.getAcademicInitial();
    mLastModified = pPersistentAdditionalInformation.getLastModified();
  }

  @Override
  public String create() {
    return sAdditionalInformationManager.create(this);
  }

  @Override
  public void update() {
    sAdditionalInformationManager.update(this);
  }

  @Override
  public void delete() {
    sAdditionalInformationManager.delete(this);
  }

  @Override
  public MutableAdditionalInformation edit() {
    return new PersistentAdditionalInformation(this);
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
  public void setRoomNo(String pRoomNo) {
    mRoomNo = pRoomNo;
  }

  @Override
  public void setExtNo(String pExtNo) {
    mExtNo = pExtNo;
  }

  @Override
  public void setAcademicInitial(String pAcademicInitial) {
    mAcademicInitial = pAcademicInitial;
  }

  @Override
  public String getRoomNo() {
    return mRoomNo;
  }

  @Override
  public String getExtNo() {
    return mExtNo;
  }

  @Override
  public String getAcademicInitial() {
    return mAcademicInitial;
  }
}
