package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.mutable.MutableDesignation;
import org.ums.manager.DepartmentManager;
import org.ums.manager.DesignationManager;

public class PersistentDesignation implements MutableDesignation {
  private static DesignationManager sDesignationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDesignationManager = applicationContext.getBean("designationManager", DesignationManager.class);
  }

  private int mId;
  private String mDesignationName;
  private int mEmployeeType;
  private String mLastModified;

  public PersistentDesignation() {

  }

  public PersistentDesignation(final PersistentDesignation pPersistentDesignation) {
    mId = pPersistentDesignation.getId();
    mDesignationName = pPersistentDesignation.getDesignationName();
    mEmployeeType = pPersistentDesignation.getEmployeeType();
  }

  @Override
  public MutableDesignation edit() {
    return new PersistentDesignation(this);
  }

  @Override
  public Integer create() {
    return sDesignationManager.create(this);
  }

  @Override
  public void update() {
    sDesignationManager.update(this);
  }

  @Override
  public void delete() {
    sDesignationManager.delete(this);
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
  public void setDesignationName(String pDesignationName) {
    mDesignationName = pDesignationName;
  }

  @Override
  public void setEmployeeType(int pEmployeeType) {
    mEmployeeType = pEmployeeType;
  }

  @Override
  public String getDesignationName() {
    return mDesignationName;
  }

  @Override
  public int getEmployeeType() {
    return mEmployeeType;
  }
}
