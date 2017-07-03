package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.common.MutableNationality;
import org.ums.manager.common.NationalityManager;

public class PersistentNationality implements MutableNationality {

  private static NationalityManager sNationalityManager;

  static {

    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sNationalityManager = applicationContext.getBean("nationalityManager", NationalityManager.class);
  }

  private Integer mId;
  private String mNationality;
  private String mLastModified;

  public PersistentNationality() {}

  public PersistentNationality(PersistentNationality pPersistentNationality) {

    mId = pPersistentNationality.getId();
    mNationality = pPersistentNationality.getNationality();
    mLastModified = pPersistentNationality.getLastModified();
  }

  @Override
  public Integer create() {
    return sNationalityManager.create(this);
  }

  @Override
  public void update() {
    sNationalityManager.update(this);
  }

  @Override
  public void delete() {
    sNationalityManager.delete(this);
  }

  @Override
  public MutableNationality edit() {
    return new PersistentNationality(this);
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
  public void setNationality(String pNationality) {
    mNationality = pNationality;
  }

  @Override
  public String getNationality() {
    return mNationality;
  }
}
