package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.common.HolidayType;
import org.ums.domain.model.mutable.common.MutableHolidayType;
import org.ums.manager.common.HolidayTypeManager;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public class PersistentHolidayType implements MutableHolidayType {

  private static HolidayTypeManager sHolidayTypeManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sHolidayTypeManager = applicationContext.getBean("holidayTypeManager", HolidayTypeManager.class);
  }

  private Long mId;
  private String mHolidayName;
  private HolidayType.SubjectToMoon mMoonDependency;
  private String mLastModified;

  public PersistentHolidayType() {}

  public PersistentHolidayType(final PersistentHolidayType pPersistentHolidayType) {
    mId = pPersistentHolidayType.getId();
    mHolidayName = pPersistentHolidayType.getHolidayName();
    mMoonDependency = pPersistentHolidayType.getMoonDependency();
    mLastModified = pPersistentHolidayType.getLastModified();
  }

  public MutableHolidayType edit() {
    return new PersistentHolidayType(this);
  }

  @Override
  public Long create() {
    return sHolidayTypeManager.create(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void update() {
    sHolidayTypeManager.update(this);
  }

  @Override
  public void delete() {
    sHolidayTypeManager.delete(this);
  }

  @Override
  public void setHolidayName(String pHolidayName) {
    mHolidayName = pHolidayName;
  }

  @Override
  public void setMoonDependency(SubjectToMoon pMoonDependency) {
    mMoonDependency = pMoonDependency;
  }

  @Override
  public String getHolidayName() {
    return mHolidayName;
  }

  @Override
  public SubjectToMoon getMoonDependency() {
    return mMoonDependency;
  }
}
