package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.employee.personal.PersonalInformation;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.employee.service.ServiceInformation;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.manager.DepartmentManager;
import org.ums.manager.EmployeeManager;

import java.util.Date;
import java.util.List;

public class PersistentEmployee implements MutableEmployee {

  private static DepartmentManager sDepartmentManager;
  private static EmployeeManager sEmployeeManager;
  private static PersonalInformationManager sPersonalInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
    sEmployeeManager = applicationContext.getBean("employeeManager", EmployeeManager.class);
    sPersonalInformationManager =
        applicationContext.getBean("personalInformationManager", PersonalInformationManager.class);
  }

  private String mId;
  private int mDesignation;
  private String mEmploymentType;
  private Department mDepartment;
  private String mDepartmentId;;
  private Date mJoiningDate;
  private int mStatus;
  private String mShortName;
  private int mEmployeeType;
  private String mLastModified;
  private PersonalInformation mPersonalInformation;

  public PersistentEmployee() {

  }

  public PersistentEmployee(final PersistentEmployee pPersistentEmployee) {
    mId = pPersistentEmployee.getId();
    mDesignation = pPersistentEmployee.getDesignation();
    mEmploymentType = pPersistentEmployee.getEmploymentType();
    mDepartment = pPersistentEmployee.getDepartment();
    mDepartmentId = pPersistentEmployee.getDepartmentId();
    mJoiningDate = pPersistentEmployee.getJoiningDate();
    mStatus = pPersistentEmployee.getStatus();
    mEmployeeType = pPersistentEmployee.getEmployeeType();
    mLastModified = pPersistentEmployee.getLastModified();
    mPersonalInformation = pPersistentEmployee.getPersonalInformation();
  }

  public String getDepartmentId() {
    return mDepartmentId;
  }

  public void setDepartmentId(String pDepartmentId) {
    mDepartmentId = pDepartmentId;
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
  public void setJoiningDate(Date pJoiningDate) {
    mJoiningDate = pJoiningDate;
  }

  @Override
  public void setStatus(int pStatus) {
    mStatus = pStatus;
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
  public Date getJoiningDate() {
    return mJoiningDate;
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
  public int getEmployeeType() {
    return mEmployeeType;
  }

  @Override
  public PersonalInformation getPersonalInformation() {
    /*
     * return mPersonalInformation == null ? sPersonalInformationManager.getPersonalInformation(mId)
     * : mPersonalInformation;
     */
    return mPersonalInformation == null ? sPersonalInformationManager.get(mId) : mPersonalInformation;
  }

  @Override
  public void setShortName(String pShortName) {
    mShortName = pShortName;
  }

  @Override
  public void setEmployeeType(int pEmployeeType) {
    mEmployeeType = pEmployeeType;
  }

  @Override
  public void setPersonalInformation(PersonalInformation pPersonalInformation) {
    mPersonalInformation = pPersonalInformation;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
