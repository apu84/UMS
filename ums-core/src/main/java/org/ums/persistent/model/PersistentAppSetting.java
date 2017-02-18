package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableAppSetting;
import org.ums.manager.AppSettingManager;

/**
 * Created by My Pc on 30-Aug-16.
 */
public class PersistentAppSetting implements MutableAppSetting {

  private static AppSettingManager sAppSettingManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAppSettingManager = applicationContext.getBean("appSettingManager", AppSettingManager.class);
  }

  private Long mId;
  private String mParameterName;
  private String mParameterValue;
  private String mDescription;
  private String mDataType;
  private String mLastModified;

  public PersistentAppSetting() {

  }

  public PersistentAppSetting(final PersistentAppSetting pPersistentAppSetting) {
    mId = pPersistentAppSetting.getId();
    mParameterName = pPersistentAppSetting.getParameterName();
    mParameterValue = pPersistentAppSetting.getParameterValue();
    mDescription = pPersistentAppSetting.getDescription();
    mDataType = pPersistentAppSetting.getDataType();
    mLastModified = pPersistentAppSetting.getLastModified();
  }

  @Override
  public void setDataType(String pDataType) {
    mDataType = pDataType;
  }

  @Override
  public String getDataType() {
    return mDataType;
  }

  @Override
  public void setParameterName(String pParameterName) {
    mParameterName = pParameterName;
  }

  @Override
  public void setParameterValue(String pParameterValue) {
    mParameterValue = pParameterValue;
  }

  @Override
  public void setParameterDescription(String pParameterDescription) {
    mDescription = pParameterDescription;
  }

  @Override
  public String getParameterName() {
    return mParameterName;
  }

  @Override
  public String getParameterValue() {
    return mParameterValue;
  }

  @Override
  public String getDescription() {
    return mDescription;
  }

  @Override
  public MutableAppSetting edit() {
    return new PersistentAppSetting(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sAppSettingManager.update(this);
    }
    else {
      sAppSettingManager.create(this);
    }
  }

  @Override
  public void delete() {
    sAppSettingManager.delete(this);
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
