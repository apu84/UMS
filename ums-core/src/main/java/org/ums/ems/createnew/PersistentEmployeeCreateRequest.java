package org.ums.ems.createnew;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

import java.util.Date;

public class PersistentEmployeeCreateRequest implements MutableEmployeeCreateRequest {

  private static EmployeeCreateRequestManager sEmployeeCreateRequestManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sEmployeeCreateRequestManager =
        applicationContext.getBean("employeeCreateRequestManager", EmployeeCreateRequestManager.class);
  }

  private String mId;
  private String mEmail;
  private Integer mSalutation;
  private String mEmployeeName;
  private String mDepartmentId;
  private Integer mEmployeeType;
  private Integer mDesignation;
  private Integer mEmploymentType;
  private Date mJoiningDate;
  private Integer mCreateAccount;
  private String mRequestedBy;
  private Date mRequestedOn;
  private String mActionTakenBy;
  private Date mActionTakenOn;
  private Integer mActionTakenStatus;
  private String mLastModified;

  public PersistentEmployeeCreateRequest() {}

  public PersistentEmployeeCreateRequest(PersistentEmployeeCreateRequest pPersistentEmployeeCreateRequest) {
    mId = pPersistentEmployeeCreateRequest.getId();
    mEmail = pPersistentEmployeeCreateRequest.getEmail();
    mSalutation = pPersistentEmployeeCreateRequest.getSalutation();
    mEmployeeName = pPersistentEmployeeCreateRequest.getEmployeeName();
    mDepartmentId = pPersistentEmployeeCreateRequest.getDepartmentId();
    mEmployeeType = pPersistentEmployeeCreateRequest.getEmployeeType();
    mDesignation = pPersistentEmployeeCreateRequest.getDesignation();
    mEmploymentType = pPersistentEmployeeCreateRequest.getEmploymentType();
    mJoiningDate = pPersistentEmployeeCreateRequest.getJoiningDate();
    mCreateAccount = pPersistentEmployeeCreateRequest.getCreateAccount();
    mRequestedBy = pPersistentEmployeeCreateRequest.getRequestedBy();
    mRequestedOn = pPersistentEmployeeCreateRequest.getRequestedOn();
    mActionTakenBy = pPersistentEmployeeCreateRequest.getActionTakenBy();
    mActionTakenOn = pPersistentEmployeeCreateRequest.getActionTakenOn();
    mActionTakenStatus = pPersistentEmployeeCreateRequest.getActionStatus();
    mLastModified = pPersistentEmployeeCreateRequest.getLastModified();
  }

  @Override
  public MutableEmployeeCreateRequest edit() {
    return new PersistentEmployeeCreateRequest(this);
  }

  @Override
  public String create() {
    return sEmployeeCreateRequestManager.create(this);
  }

  @Override
  public void update() {
    sEmployeeCreateRequestManager.update(this);
  }

  @Override
  public void delete() {
    sEmployeeCreateRequestManager.delete(this);
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
  public void setId(String pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void setEmail(String pEmail) {
    mEmail = pEmail;
  }

  @Override
  public void setSalutation(Integer pSalutation) {
    mSalutation = pSalutation;
  }

  @Override
  public void setEmployeeName(String pEmployeeName) {
    mEmployeeName = pEmployeeName;
  }

  @Override
  public void setDepartmentId(String pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  @Override
  public void setEmployeeType(Integer pEmployeeType) {
    mEmployeeType = pEmployeeType;
  }

  @Override
  public void setDesignation(Integer pDesignation) {
    mDesignation = pDesignation;
  }

  @Override
  public void setEmploymentType(Integer pEmploymentType) {
    mEmploymentType = pEmploymentType;
  }

  @Override
  public void setJoiningDate(Date pJoiningDate) {
    mJoiningDate = pJoiningDate;
  }

  @Override
  public void setCreateAccount(Integer pCreateAccount) {
    mCreateAccount = pCreateAccount;
  }

  @Override
  public void setRequestedBy(String pRequestedBy) {
    mRequestedBy = pRequestedBy;
  }

  @Override
  public void setRequestedOn(Date pRequestedOn) {
    mRequestedOn = pRequestedOn;
  }

  @Override
  public void setActionTakenBy(String pActionTakenBy) {
    mActionTakenBy = pActionTakenBy;
  }

  @Override
  public void setActionTakenOn(Date pActionTakenOn) {
    mActionTakenOn = pActionTakenOn;
  }

  @Override
  public void setActionStatus(Integer pActionStatus) {
    mActionTakenStatus = pActionStatus;
  }

  @Override
  public String getEmail() {
    return mEmail;
  }

  @Override
  public Integer getSalutation() {
    return mSalutation;
  }

  @Override
  public String getEmployeeName() {
    return mEmployeeName;
  }

  @Override
  public String getDepartmentId() {
    return mDepartmentId;
  }

  @Override
  public Integer getEmployeeType() {
    return mEmployeeType;
  }

  @Override
  public Integer getDesignation() {
    return mDesignation;
  }

  @Override
  public Integer getEmploymentType() {
    return mEmploymentType;
  }

  @Override
  public Date getJoiningDate() {
    return mJoiningDate;
  }

  @Override
  public Integer getCreateAccount() {
    return mCreateAccount;
  }

  @Override
  public String getRequestedBy() {
    return mRequestedBy;
  }

  @Override
  public Date getRequestedOn() {
    return mRequestedOn;
  }

  @Override
  public String getActionTakenBy() {
    return mActionTakenBy;
  }

  @Override
  public Date getActionTakenOn() {
    return mActionTakenOn;
  }

  @Override
  public Integer getActionStatus() {
    return mActionTakenStatus;
  }
}
