package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.DeptDesignationMap;
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
  private String mLastModified;

  public PersistentDeptDesignationMap() {

  }

  public PersistentDeptDesignationMap(final PersistentDeptDesignationMap pPersistentDeptDesignationMap) {
    mId = pPersistentDeptDesignationMap.getId();
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
}
