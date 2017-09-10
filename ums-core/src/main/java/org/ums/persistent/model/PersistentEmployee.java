package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.employee.service.ServiceInformation;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.manager.DepartmentManager;
import org.ums.manager.EmployeeManager;

import java.util.Date;
import java.util.List;

public class PersistentEmployee implements MutableEmployee {

  private static DepartmentManager sDepartmentManager;
  private static EmployeeManager sEmployeeManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
    sEmployeeManager = applicationContext.getBean("employeeManager", EmployeeManager.class);
  }

  private String mId;
  private String mEmployeeName;
  private int mDesignation;
  private String mEmploymentType;
  private Department mDepartment;
  private String mDepartmentId;
  private String mFatherName;
  private String mMotherName;
  private Date mBirthDate;
  private String mGender;
  private String mBloodGroup;
  private String mPresentAddress;
  private String mPermanentAddress;
  private String mMobileNumber;
  private String mPhoneNumber;
  private String mEmailAddress;
  private Date mJoiningDate;
  private Date mJobParmanentDate;
  private int mStatus;
  private String mShortName;
  private String mLastModified;
  private List<ServiceInformation> mServiceInformationList;

  public List<ServiceInformation> getServiceInformationList() {
    return null;
  }

  public PersistentEmployee() {

  }

  public PersistentEmployee(final PersistentEmployee pPersistentEmployee) {
    mId = pPersistentEmployee.getId();
    mEmployeeName = pPersistentEmployee.getEmployeeName();
    mDesignation = pPersistentEmployee.getDesignation();
    mEmploymentType = pPersistentEmployee.getEmploymentType();
    mDepartment = pPersistentEmployee.getDepartment();
    mDepartmentId = pPersistentEmployee.getDepartmentId();
    mFatherName = pPersistentEmployee.getFatherName();
    mMotherName = pPersistentEmployee.getMotherName();
    mBirthDate = pPersistentEmployee.getBirthDate();
    mGender = pPersistentEmployee.getGender();
    mBloodGroup = pPersistentEmployee.getBloodGroup();
    mPresentAddress = pPersistentEmployee.getPresentAddress();
    mPermanentAddress = pPersistentEmployee.getPermanentAddress();
    mMobileNumber = pPersistentEmployee.getMobileNumber();
    mPhoneNumber = pPersistentEmployee.getPhoneNumber();
    mEmailAddress = pPersistentEmployee.getEmailAddress();
    mJoiningDate = pPersistentEmployee.getJoiningDate();
    mJobParmanentDate = pPersistentEmployee.getJobPermanentDate();
    mStatus = pPersistentEmployee.getStatus();
    mLastModified = pPersistentEmployee.getLastModified();
  }

  public String getDepartmentId() {
    return mDepartmentId;
  }

  public void setDepartmentId(String pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  @Override
  public void setEmployeeName(String pEmployeeName) {
    mEmployeeName = pEmployeeName;
  }

  @Override
  public void setDesignation(int pDesignation) {
    mDesignation = pDesignation;
  }

  @Override
  public void setEmploymentType(String pEmploymentType) {
    mEmploymentType = pEmploymentType;
  }

  @Override
  public void setDepartment(Department pDepartment) {
    mDepartment = pDepartment;
  }

  @Override
  public void setFatherName(String pFatherName) {
    mFatherName = pFatherName;
  }

  @Override
  public void setMotherName(String pMotherName) {
    mMotherName = pMotherName;
  }

  @Override
  public void setBirthDate(Date pBirthDate) {
    mBirthDate = pBirthDate;
  }

  @Override
  public void setGender(String pGender) {
    mGender = pGender;
  }

  @Override
  public void setBloodGroup(String pBloodGroup) {
    mBloodGroup = pBloodGroup;
  }

  @Override
  public void setPresentAddress(String pPresentAddress) {
    mPresentAddress = pPresentAddress;
  }

  @Override
  public void setPermanentAddress(String pPermanentAddress) {
    mPermanentAddress = pPermanentAddress;
  }

  @Override
  public void setMobileNumber(String pMobileNumber) {
    mMobileNumber = pMobileNumber;
  }

  @Override
  public void setPhoneNumber(String pPhoneNumber) {
    mPhoneNumber = pPhoneNumber;
  }

  @Override
  public void setEmailAddress(String pEmailAddress) {
    mEmailAddress = pEmailAddress;
  }

  @Override
  public void setJoiningDate(Date pJoiningDate) {
    mJoiningDate = pJoiningDate;
  }

  @Override
  public void setJobParmanentDate(Date pJobParmanentDate) {
    mJobParmanentDate = pJobParmanentDate;
  }

  @Override
  public void setStatus(int pStatus) {
    mStatus = pStatus;
  }

  @Override
  public String getEmployeeName() {
    return mEmployeeName;
  }

  @Override
  public int getDesignation() {
    return mDesignation;
  }

  @Override
  public String getEmploymentType() {
    return mEmploymentType;
  }

  @Override
  public Department getDepartment() {
    return mDepartment == null ? sDepartmentManager.get(mDepartmentId) : sDepartmentManager.validate(mDepartment);
  }

  @Override
  public String getFatherName() {
    return mFatherName;
  }

  @Override
  public String getMotherName() {
    return mMotherName;
  }

  @Override
  public Date getBirthDate() {
    return mBirthDate;
  }

  @Override
  public String getGender() {
    return mGender;
  }

  @Override
  public String getBloodGroup() {
    return mBloodGroup;
  }

  @Override
  public String getPresentAddress() {
    return mPresentAddress;
  }

  @Override
  public String getPermanentAddress() {
    return mPermanentAddress;
  }

  @Override
  public String getMobileNumber() {
    return mMobileNumber;
  }

  @Override
  public String getPhoneNumber() {
    return mPhoneNumber;
  }

  @Override
  public String getEmailAddress() {
    return mEmailAddress;
  }

  @Override
  public Date getJoiningDate() {
    return mJoiningDate;
  }

  @Override
  public Date getJobPermanentDate() {
    return mJobParmanentDate;
  }

  @Override
  public int getStatus() {
    return mStatus;
  }

  @Override
  public MutableEmployee edit() {
    return new PersistentEmployee(this);
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public String create() {
    return sEmployeeManager.create(this);
  }

  @Override
  public void update() {
    sEmployeeManager.update(this);
  }

  @Override
  public void delete() {
    sEmployeeManager.delete(this);
  }

  @Override
  public void setId(String pId) {
    mId = pId;
  }

  @Override
  public String getShortName() {
    return mShortName;
  }

  @Override
  public void setShortName(String pShortName) {
    mShortName = pShortName;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
