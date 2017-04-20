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

  private String mId;
  private String mDistrictId;
  private String mDivisionId;
  private String mDistrictName;
  private String mLastModified;

  public PersistentDistrict() {}

  public PersistentDistrict(final PersistentDistrict pPersistentDistrict) {
    mId = pPersistentDistrict.getId();
    mDistrictId = pPersistentDistrict.getDistrictId();
    mDivisionId = pPersistentDistrict.getDivisionId();
    mDistrictName = pPersistentDistrict.getDistrictName();
  }

  @Override
  public MutableDistrict edit() {
    return new PersistentDistrict(this);
  }

  @Override
  public String create() {
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
  public String getId() {
    return mId;
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
  public void setDistrictId(String pDistrictId) {
    mDistrictId = pDistrictId;
  }

  @Override
  public void setDivisionId(String pDivisionId) {
    mDivisionId = pDivisionId;
  }

  @Override
  public void setDistrictName(String pDistrictName) {
    mDistrictName = pDistrictName;
  }

  @Override
  public String getDistrictId() {
    return mDistrictId;
  }

  @Override
  public String getDivisionId() {
    return mDivisionId;
  }

  @Override
  public String getDistrictName() {
    return mDistrictName;
  }
}
