package org.ums.persistent.model.registrar.employee;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.registrar.employee.EmployeeInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableEmployeeInformation;
import org.ums.manager.registrar.employee.EmployeeInformationManager;

public class PersistentEmployeeInformation implements MutableEmployeeInformation {

  private static EmployeeInformationManager sEmployeeInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sEmployeeInformationManager =
        applicationContext.getBean("employeeInformationManager", EmployeeInformationManager.class);
  }

  private int mId;
  private int mEmployeeId;
  private String mEmploymentType;
  private int mDesignation;
  private String mDeptOffice;
  private String mJoiningDate;
  private String mJobPermanentDate;
  private String mJobTerminationDate;
  private int mExtNo;
  private String mShortName;
  private String mRoomNo;
  private String mLastModified;

  public PersistentEmployeeInformation() {}

  public PersistentEmployeeInformation(EmployeeInformation pEmployeeInformation) {
    mEmployeeId = pEmployeeInformation.getEmployeeId();
    mEmploymentType = pEmployeeInformation.getEmploymentType();
    mDesignation = pEmployeeInformation.getDesignation();
    mDeptOffice = pEmployeeInformation.getDeptOffice();
    mJoiningDate = pEmployeeInformation.getJoiningDate();
    mJobPermanentDate = pEmployeeInformation.getJobPermanentDate();
    mJobTerminationDate = pEmployeeInformation.getJobTerminationDate();
    mExtNo = pEmployeeInformation.getExtNo();
    mShortName = pEmployeeInformation.getShortName();
    mRoomNo = pEmployeeInformation.getRoomNo();
  }

  @Override
  public MutableEmployeeInformation edit() {
    return new PersistentEmployeeInformation(this);
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
  public void setEmploymentType(String pEmploymentType) {
    mEmploymentType = pEmploymentType;
  }

  @Override
  public void setDesignation(int pDesignation) {
    mDesignation = pDesignation;
  }

  @Override
  public void setDeptOffice(String pDeptOffice) {
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
  public String getEmploymentType() {
    return mEmploymentType;
  }

  @Override
  public int getDesignation() {
    return mDesignation;
  }

  @Override
  public String getDeptOffice() {
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
