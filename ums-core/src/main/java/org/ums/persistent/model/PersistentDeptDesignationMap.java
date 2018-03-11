package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.DeptDesignationMap;
import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.mutable.MutableDeptDesignationMap;
import org.ums.manager.DeptDesignationMapManager;

public class PersistentDeptDesignationMap implements MutableDeptDesignationMap {

  private static DeptDesignationMapManager sDeptDesignationMapManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDeptDesignationMapManager =
        applicationContext.getBean("deptDesignationMapManager", DeptDesignationMapManager.class);
  }

  private int mId;
  private Department mDepartment;
  private String mDepartmentId;
  private int mEmployeeTypeId;
  private int mDesignationId;
  private Designation mDesignation;
  private String mLastModified;

  public PersistentDeptDesignationMap() {

  }

  public PersistentDeptDesignationMap(final PersistentDeptDesignationMap pPersistentDeptDesignationMap) {
    mId = pPersistentDeptDesignationMap.getId();
    mDepartment = pPersistentDeptDesignationMap.getDepartment();
    mDepartmentId = pPersistentDeptDesignationMap.getDepartmentId();
    mEmployeeTypeId = pPersistentDeptDesignationMap.getEmployeeTypeId();
    mDesignationId = pPersistentDeptDesignationMap.getEmployeeTypeId();
    mDesignation = pPersistentDeptDesignationMap.getDesignation();
    mLastModified = pPersistentDeptDesignationMap.getLastModified();
  }

  @Override
  public MutableDeptDesignationMap edit() {
    return new PersistentDeptDesignationMap(this);
  }

  @Override
  public Integer create() {
    return sDeptDesignationMapManager.create(this);
  }

  @Override
  public void update() {
    sDeptDesignationMapManager.update(this);
  }

  @Override
  public void delete() {
    sDeptDesignationMapManager.delete(this);
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
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
  public void setDepartment(Department pDepartment) {
    mDepartment = pDepartment;
  }

  @Override
  public void setDepartmentId(String pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  @Override
  public void setEmployeeTypeId(int pEmployeeTypeId) {
    mEmployeeTypeId = pEmployeeTypeId;
  }

  @Override
  public void setDesignationId(int pDesignationId) {
    mDesignationId = pDesignationId;
  }

  @Override
  public void setDesignation(Designation pDesignation) {
    mDesignation = pDesignation;
  }

  @Override
  public Department getDepartment() {
    return mDepartment;
  }

  @Override
  public String getDepartmentId() {
    return mDepartmentId;
  }

  @Override
  public int getEmployeeTypeId() {
    return mEmployeeTypeId;
  }

  @Override
  public int getDesignationId() {
    return mDesignationId;
  }

  @Override
  public Designation getDesignation() {
    return mDesignation;
  }
}
