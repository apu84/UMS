package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.common.MutableThana;
import org.ums.manager.common.ThanaManager;

public class PersistentThana implements MutableThana {

  private static ThanaManager sThanaManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sThanaManager = applicationContext.getBean("thanaManager", ThanaManager.class);
  }

  private Integer mId;
  private Integer mDistrictId;
  private String mThanaName;
  private String mLastModified;

  public PersistentThana() {}

  public PersistentThana(final PersistentThana pPersistentThana) {
    mId = pPersistentThana.getId();
    mDistrictId = pPersistentThana.getDistrictId();
    mThanaName = pPersistentThana.getThanaName();
    mLastModified = pPersistentThana.getLastModified();
  }

  @Override
  public MutableThana edit() {
    return new PersistentThana(this);
  }

  @Override
  public Integer create() {
    return sThanaManager.create(this);
  }

  @Override
  public void update() {
    sThanaManager.update(this);
  }

  @Override
  public void delete() {
    sThanaManager.delete(this);
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
  public void setDistrictId(Integer pDistrictId) {
    mDistrictId = pDistrictId;
  }

  @Override
  public void setThanaName(String pThanaName) {
    mThanaName = pThanaName;
  }

  @Override
  public Integer getDistrictId() {
    return mDistrictId;
  }

  @Override
  public String getThanaName() {
    return mThanaName;
  }
}
