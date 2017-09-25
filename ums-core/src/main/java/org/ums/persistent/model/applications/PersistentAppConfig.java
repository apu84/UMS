package org.ums.persistent.model.applications;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.applications.MutableAppConfig;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeCategoryManager;
import org.ums.manager.DepartmentManager;
import org.ums.manager.applications.AppConfigManager;

/**
 * Created by Monjur-E-Morshed on 20-Sep-17.
 */
public class PersistentAppConfig implements MutableAppConfig {

  private static AppConfigManager sAppConfigManager;
  private static DepartmentManager sDepartmentManager;
  private static FeeCategoryManager sFeeCategoryManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAppConfigManager = applicationContext.getBean("appConfigManager", AppConfigManager.class);
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
    sFeeCategoryManager = applicationContext.getBean("feeCategoryManager", FeeCategoryManager.class);
  }

  private long mId;
  private String mFeeCategoryId;
  private FeeCategory mFeeCategory;
  private int mValidityPeriod;
  private String mDepartmentId;
  private Department mDepartment;
  private boolean mHeadsForwarding;
  private String mLastModified;

  public PersistentAppConfig() {
  }

  public PersistentAppConfig(final PersistentAppConfig pPersistentAppConfig) {
    mId = pPersistentAppConfig.getId();
    mFeeCategoryId = pPersistentAppConfig.getFeeCategory().getId();
    mFeeCategory = pPersistentAppConfig.getFeeCategory();
    mValidityPeriod = pPersistentAppConfig.getValidityPeriod();
    mDepartmentId = pPersistentAppConfig.getDepartment().getId();
    mDepartment = pPersistentAppConfig.getDepartment();
    mHeadsForwarding = pPersistentAppConfig.getHeadsForwarding();
  }

  @Override
  public void setFeeCategoryId(String pFeeCategoryId) {
    mFeeCategoryId = pFeeCategoryId;
  }

  @Override
  public void setValidityPeriod(int pValidityPeriod) {
    mValidityPeriod = pValidityPeriod;
  }

  @Override
  public void setDepartmentId(String pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  @Override
  public void setHeadsForwarding(boolean pHeadsForwarding) {
    mHeadsForwarding = pHeadsForwarding;
  }

  @Override
  public FeeCategory getFeeCategory() {
    return mFeeCategory;
  }

  @Override
  public int getValidityPeriod() {
    return mValidityPeriod;
  }

  @Override
  public Department getDepartment() {
    return mDepartment == null ? sDepartmentManager.get(mDepartmentId) : sDepartmentManager.validate(mDepartment);
  }

  @Override
  public Boolean getHeadsForwarding() {
    return mHeadsForwarding;
  }

  @Override
  public MutableAppConfig edit() {
    return new PersistentAppConfig(this);
  }

  @Override
  public Long create() {
    return sAppConfigManager.create(this);
  }

  @Override
  public void update() {
    sAppConfigManager.update(this);
  }

  @Override
  public void delete() {
    sAppConfigManager.delete(this);
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
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
