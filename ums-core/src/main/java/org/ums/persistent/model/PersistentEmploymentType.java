package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.EmploymentType;
import org.ums.domain.model.mutable.MutableEmploymentType;
import org.ums.manager.EmploymentTypeManager;

public class PersistentEmploymentType implements MutableEmploymentType {

  private static EmploymentTypeManager sEmploymentTypeManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sEmploymentTypeManager = applicationContext.getBean("employmentTypeManager", EmploymentTypeManager.class);
  }

  private int mId;
  private String mType;
  private String mLastModified;

  public PersistentEmploymentType() {}

  public PersistentEmploymentType(PersistentEmploymentType pPersistentEmploymentType) {
    mId = pPersistentEmploymentType.getId();
    mType = pPersistentEmploymentType.getType();
    mLastModified = pPersistentEmploymentType.getLastModified();
  }

  @Override
  public MutableEmploymentType edit() {
    return new PersistentEmploymentType(this);
  }

  @Override
  public Integer create() {
    return sEmploymentTypeManager.create(this);
  }

  @Override
  public void update() {
    sEmploymentTypeManager.update(this);
  }

  @Override
  public void delete() {
    sEmploymentTypeManager.delete(this);
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
  public void setType(String pType) {
    mType = pType;
  }

  @Override
  public String getType() {
    return mType;
  }
}
