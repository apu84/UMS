package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.mutable.MutableDesignationRoleMap;
import org.ums.manager.DesignationRoleMapManager;
import org.ums.usermanagement.role.Role;

public class PersistentDesignationRoleMap implements MutableDesignationRoleMap {

  private static DesignationRoleMapManager sDesignationRoleMapManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDesignationRoleMapManager =
        applicationContext.getBean("designationRoleMapManager", DesignationRoleMapManager.class);
  }

  private int mId;
  private Designation mDesignation;
  private int mDesignationId;
  private Role mRole;
  private int mRoleId;
  private String mLastModified;

  public PersistentDesignationRoleMap() {

  }

  public PersistentDesignationRoleMap(final PersistentDesignationRoleMap pPersistentDesignationRoleMap) {
    mId = pPersistentDesignationRoleMap.getId();
    mDesignation = pPersistentDesignationRoleMap.getDesignation();
    mDesignationId = pPersistentDesignationRoleMap.getDesignationId();
    mRole = pPersistentDesignationRoleMap.getRole();
    mRoleId = pPersistentDesignationRoleMap.getRoleId();
    mLastModified = pPersistentDesignationRoleMap.getLastModified();
  }

  @Override
  public void setDesignation(Designation pDesignation) {
    mDesignation = pDesignation;
  }

  @Override
  public void setDesignationId(Integer pDesignationId) {
    mDesignationId = pDesignationId;
  }

  @Override
  public void setRole(Role pRole) {
    mRole = pRole;
  }

  @Override
  public void setRoleId(Integer pRoleId) {
    mRoleId = pRoleId;
  }

  @Override
  public Integer create() {
    return sDesignationRoleMapManager.create(this);
  }

  @Override
  public void update() {
    sDesignationRoleMapManager.update(this);
  }

  @Override
  public void delete() {
    sDesignationRoleMapManager.delete(this);
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public Designation getDesignation() {
    return mDesignation;
  }

  @Override
  public Integer getDesignationId() {
    return mDesignationId;
  }

  @Override
  public Role getRole() {
    return mRole;
  }

  @Override
  public Integer getRoleId() {
    return mRoleId;
  }

  @Override
  public MutableDesignationRoleMap edit() {
    return new PersistentDesignationRoleMap(this);
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
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
