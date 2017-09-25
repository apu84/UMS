package org.ums.persistent.model.applications;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.applications.MutableAppRules;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeCategoryManager;
import org.ums.manager.applications.AppRulesManager;

/**
 * Created by Monjur-E-Morshed on 21-Sep-17.
 */
public class PersistentAppRules implements MutableAppRules {


  private static AppRulesManager sAppRulesManager;
  private static FeeCategoryManager sFeeCategoryManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFeeCategoryManager = applicationContext.getBean("feeCategoryManager", FeeCategoryManager.class);
    sAppRulesManager = applicationContext.getBean("appRulesManager", AppRulesManager.class);
  }


  private Long mId;
  private String mFeeCategoryId;
  private FeeCategory mFeeCategory;
  private String mDependentFeeCategoryId;
  private FeeCategory mDependentFeeCategory;
  private String mLastModified;

  public PersistentAppRules() {
  }

  public PersistentAppRules(final PersistentAppRules pPersistentAppRules) {
    mId = pPersistentAppRules.getId();
    mFeeCategoryId = pPersistentAppRules.getDependentFeeCategory().getId();
    mFeeCategory = pPersistentAppRules.getFeeCategory();
    mDependentFeeCategoryId = pPersistentAppRules.getDependentFeeCategory().getId();
    mDependentFeeCategory = pPersistentAppRules.getDependentFeeCategory();
    mLastModified = pPersistentAppRules.getLastModified();
  }


  @Override
  public void setFeeCategoryId(String pFeeCategoryId) {
    mFeeCategoryId = pFeeCategoryId;
  }

  @Override
  public void setDependentFeeCategoryId(String pDependentFeeCategoryId) {
    mDependentFeeCategoryId = pDependentFeeCategoryId;
  }

  @Override
  public MutableAppRules edit() {
    return new PersistentAppRules(this);
  }

  @Override
  public Long create() {
    return sAppRulesManager.create(this);
  }

  @Override
  public void update() {
    sAppRulesManager.update(this);
  }

  @Override
  public void delete() {
    sAppRulesManager.delete(this);
  }

  @Override
  public FeeCategory getFeeCategory() {
    return mFeeCategory;
  }

  @Override
  public FeeCategory getDependentFeeCategory() {
    return mDependentFeeCategory;
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
