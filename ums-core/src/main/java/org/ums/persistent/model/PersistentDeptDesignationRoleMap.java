package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.mutable.MutableDeptDesignationRoleMap;
import org.ums.manager.DeptDesignationRoleMapManager;
import org.ums.usermanagement.role.Role;

public class PersistentDeptDesignationRoleMap implements MutableDeptDesignationRoleMap {

  private static DeptDesignationRoleMapManager sDeptDesignationRoleMapManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDeptDesignationRoleMapManager =
        applicationContext.getBean("deptDesignationMapManager", DeptDesignationRoleMapManager.class);
  }

  private int mId;
  private Department mDepartment;
  private String mDepartmentId;
  private int mEmployeeTypeId;
  private int mDesignationId;
  private Designation mDesignation;
  private Role mRole;
  private int mRoleId;
  private String mLastModified;

  public PersistentDeptDesignationRoleMap() {

  }

  public PersistentDeptDesignationRoleMap(final PersistentDeptDesignationRoleMap pPersistentDeptDesignationRoleMap) {
    mId = pPersistentDeptDesignationRoleMap.getId();
    mDepartment = pPersistentDeptDesignationRoleMap.getDepartment();
    mDepartmentId = pPersistentDeptDesignationRoleMap.getDepartmentId();
    mEmployeeTypeId = pPersistentDeptDesignationRoleMap.getEmployeeTypeId();
    mDesignationId = pPersistentDeptDesignationRoleMap.getEmployeeTypeId();
    mDesignation = pPersistentDeptDesignationRoleMap.getDesignation();
    mRole = pPersistentDeptDesignationRoleMap.getRole();
    mRoleId = pPersistentDeptDesignationRoleMap.getRoleId();
    mLastModified = pPersistentDeptDesignationRoleMap.getLastModified();
  }

  @Override
  public MutableDeptDesignationRoleMap edit() {
    return new PersistentDeptDesignationRoleMap(this);
  }

  @Override
  public Integer create() {
    return sDeptDesignationRoleMapManager.create(this);
  }

  @Override
  public void update() {
    sDeptDesignationRoleMapManager.update(this);
  }

  @Override
  public void delete() {
    sDeptDesignationRoleMapManager.delete(this);
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
  public void setRole(Role pRole) {
    mRole = pRole;
  }

  @Override
  public void setRoleId(int pRoleId) {
    mRoleId = pRoleId;
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

  @Override
  public Role getRole() {
    return mRole;
  }

  @Override
  public int getRoleId() {
    return mRoleId;
  }
}
