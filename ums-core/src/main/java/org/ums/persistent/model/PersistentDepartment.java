package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableDepartment;
import org.ums.manager.DepartmentManager;

public class PersistentDepartment implements MutableDepartment {
  private static DepartmentManager sDepartmentManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
  }

  private String mId;
  private String mShortName;
  private String mLongName;
  private int mType;
  private String mLastModified;

  public PersistentDepartment() {

  }

  public PersistentDepartment(final PersistentDepartment pPersistentDepartment) {
    mId = pPersistentDepartment.getId();
    mShortName = pPersistentDepartment.getShortName();
    mLongName = pPersistentDepartment.getLongName();
    mType = pPersistentDepartment.getType();
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sDepartmentManager.update(this);
    }
    else {
      sDepartmentManager.create(this);
    }
  }

  @Override
  public void delete() {
    sDepartmentManager.delete(this);
  }

  public String getId() {
    return mId;
  }

  @Override
  public void setId(String pId) {
    mId = pId;
  }

  public String getShortName() {
    return mShortName;
  }

  @Override
  public void setShortName(String pShortName) {
    mShortName = pShortName;
  }

  public String getLongName() {
    return mLongName;
  }

  @Override
  public void setLongName(String pLongName) {
    mLongName = pLongName;
  }

  public int getType() {
    return mType;
  }

  @Override
  public void setType(int pType) {
    mType = pType;
  }

  @Override
  public MutableDepartment edit() {
    return new PersistentDepartment(this);
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
