package org.ums.persistent.model.registrar;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.registrar.MutableServiceInformation;
import org.ums.manager.registrar.ServiceInformationManager;

public class PersistentServiceInformation implements MutableServiceInformation {

  private static ServiceInformationManager sEmployeeInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sEmployeeInformationManager =
        applicationContext.getBean("employeeInformationManager", ServiceInformationManager.class);
  }

  private int mId;
  private int mEmployeeId;
  private int mEmploymentType;
  private int mDesignation;
  private int mDeptOffice;
  private String mJoiningDate;
  private String mJobContractualDate;
  private String mJobProbationDate;
  private String mJobPermanentDate;
  private String mJobTerminationDate;
  private int mExtNo;
  private String mShortName;
  private String mRoomNo;
  private String mLastModified;

  public PersistentServiceInformation() {}

  public PersistentServiceInformation(PersistentServiceInformation pPersistentServiceInformation) {
    mEmployeeId = pPersistentServiceInformation.getEmployeeId();
    mEmploymentType = pPersistentServiceInformation.getEmploymentType();
    mDesignation = pPersistentServiceInformation.getDesignation();
    mDeptOffice = pPersistentServiceInformation.getDeptOffice();
    mJoiningDate = pPersistentServiceInformation.getJoiningDate();
    mJobContractualDate = pPersistentServiceInformation.getJobContractualDate();
    mJobProbationDate = pPersistentServiceInformation.getJobProbationDate();
    mJobPermanentDate = pPersistentServiceInformation.getJobPermanentDate();
    mJobTerminationDate = pPersistentServiceInformation.getJobTerminationDate();
    mExtNo = pPersistentServiceInformation.getExtNo();
    mShortName = pPersistentServiceInformation.getShortName();
    mRoomNo = pPersistentServiceInformation.getRoomNo();
  }

  @Override
  public MutableServiceInformation edit() {
    return new PersistentServiceInformation(this);
  }

  @Override
  public Integer create() {
    return sEmployeeInformationManager.create(this);
  }

  @Override
  public void update() {
    sEmployeeInformationManager.update(this);
  }

  @Override
  public void delete() {
    sEmployeeInformationManager.delete(this);
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
  public void setEmployeeId(int pEmployeeId) {
    mEmployeeId = pEmployeeId;
  }

  @Override
  public void setEmploymentType(int pEmploymentType) {
    mEmploymentType = pEmploymentType;
  }

  @Override
  public void setDesignation(int pDesignation) {
    mDesignation = pDesignation;
  }

  @Override
  public void setDeptOffice(int pDeptOffice) {
    mDeptOffice = pDeptOffice;
  }

  @Override
  public void setJoiningDate(String pJoiningDate) {
    mJoiningDate = pJoiningDate;
  }

  @Override
  public void setJobPermanentDate(String pJobPermanentDate) {
    mJobPermanentDate = pJobPermanentDate;
  }

  @Override
  public void setJobContractualDate(String pJobContractualDate) {
    mJobContractualDate = pJobContractualDate;
  }

  @Override
  public void setJobProbationDate(String pJobProbationDate) {
    mJobProbationDate = pJobProbationDate;
  }

  @Override
  public void setJobTerminationDate(String pJobTerminationDate) {
    mJobTerminationDate = pJobTerminationDate;
  }

  @Override
  public void setExtNo(int pExtNo) {
    mExtNo = pExtNo;
  }

  @Override
  public void setShortName(String pShortName) {
    mShortName = pShortName;
  }

  @Override
  public void setRoomNo(String pRoomNo) {
    mRoomNo = pRoomNo;
  }

  @Override
  public int getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public int getEmploymentType() {
    return mEmploymentType;
  }

  @Override
  public int getDesignation() {
    return mDesignation;
  }

  @Override
  public int getDeptOffice() {
    return mDeptOffice;
  }

  @Override
  public String getJoiningDate() {
    return mJoiningDate;
  }

  @Override
  public String getJobPermanentDate() {
    return mJobPermanentDate;
  }

  @Override
  public String getJobContractualDate() {
    return mJobContractualDate;
  }

  @Override
  public String getJobProbationDate() {
    return mJobProbationDate;
  }

  @Override
  public String getJobTerminationDate() {
    return mJobTerminationDate;
  }

  @Override
  public int getExtNo() {
    return mExtNo;
  }

  @Override
  public String getShortName() {
    return mShortName;
  }

  @Override
  public String getRoomNo() {
    return mRoomNo;
  }
}
