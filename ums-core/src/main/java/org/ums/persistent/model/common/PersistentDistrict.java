package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.common.MutableDistrict;
import org.ums.manager.common.DistrictManager;

public class PersistentDistrict implements MutableDistrict {

  private static DistrictManager sDistrictManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDistrictManager = applicationContext.getBean("districtManager", DistrictManager.class);
  }

  private Integer mId;
  private Integer mDivisionId;
  private String mDistrictName;
  private String mLastModified;

  public PersistentDistrict() {}

  public PersistentDistrict(final PersistentDistrict pPersistentDistrict) {
    mId = pPersistentDistrict.getId();
    mDivisionId = pPersistentDistrict.getDivisionId();
    mDistrictName = pPersistentDistrict.getDistrictName();
    mLastModified = pPersistentDistrict.getLastModified();
  }

  @Override
  public MutableDistrict edit() {
    return new PersistentDistrict(this);
  }

  @Override
  public Integer create() {
    return sDistrictManager.create(this);
  }

  @Override
  public void update() {
    sDistrictManager.update(this);
  }

  @Override
  public void delete() {
    sDistrictManager.delete(this);
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
  public void setDivisionId(Integer pDivisionId) {
    mDivisionId = pDivisionId;
  }

  @Override
  public void setDistrictName(String pDistrictName) {
    mDistrictName = pDistrictName;
  }

  @Override
  public Integer getDivisionId() {
    return mDivisionId;
  }

  @Override
  public String getDistrictName() {
    return mDistrictName;
  }
}
