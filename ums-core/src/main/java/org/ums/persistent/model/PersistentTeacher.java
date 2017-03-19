package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.domain.model.immutable.Department;
import org.ums.manager.DepartmentManager;

public class PersistentTeacher implements MutableTeacher {
  private static DepartmentManager sDepartmentManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
  }
  private String mId;
  private String mName;
  private String mDesignationName;
  private String mDesignationId;
  private String mDepartmentName;
  private String mDepartmentId;
  private Department mDepartment;
  private boolean mStatus;
  private String mLastModified;

  @Override
  public void setName(String pName) {
    mName = pName;
  }

  @Override
  public void setDesignationId(String pDesignationId) {
    mDesignationId = pDesignationId;
  }

  @Override
  public void setDesignationName(String pDesignationName) {
    mDesignationName = pDesignationName;
  }

  @Override
  public void setDepartmentId(String pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  @Override
  public void setDepartmentName(String pDepartmentName) {
    mDepartmentName = pDepartmentName;
  }

  @Override
  public void setDepartment(Department pDepartment) {
    mDepartment = pDepartment;
  }

  @Override
  public void setStatus(boolean pStatus) {
    mStatus = pStatus;
  }

  @Override
  public String create() {
    return null;
  }

  @Override
  public void update() {

  }

  @Override
  public void delete() {

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
  public String getName() {
    return mName;
  }

  @Override
  public String getDesignationId() {
    return mDesignationId;
  }

  @Override
  public String getDesignationName() {
    return mDesignationName;
  }

  @Override
  public String getDepartmentId() {
    return mDepartmentId;
  }

  @Override
  public Department getDepartment() {
    return mDepartment == null ? sDepartmentManager.get(mDepartmentId) : sDepartmentManager
        .validate(mDepartment);
  }

  @Override
  public String getDepartmentName() {
    return mDepartmentName;
  }

  @Override
  public boolean getStatus() {
    return mStatus;
  }

  @Override
  public MutableTeacher edit() {
    return null;
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified == null ? "" : mLastModified;
  }
}
