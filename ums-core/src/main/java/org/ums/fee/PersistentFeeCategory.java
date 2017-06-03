package org.ums.fee;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentFeeCategory implements MutableFeeCategory {
  private static FeeCategoryManager sFeeCategoryManager;
  private static FeeTypeManager sFeeTypeManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFeeCategoryManager = applicationContext.getBean("feeCategoryManager", FeeCategoryManager.class);
    sFeeTypeManager = applicationContext.getBean("feeTypeManager", FeeTypeManager.class);
  }
  private String mId;
  private String mFeeId;
  private FeeType mFeeType;
  private Integer mFeeTypeId;
  private String mName;
  private String mDescription;
  private String mLastModified;

  public PersistentFeeCategory() {}

  PersistentFeeCategory(final PersistentFeeCategory persistentFeeCategory) {
    setId(persistentFeeCategory.getId());
    setType(persistentFeeCategory.getType());
    setName(persistentFeeCategory.getName());
    setDescription(persistentFeeCategory.getDescription());
  }

  @Override
  public String create() {
    return sFeeCategoryManager.create(this);
  }

  @Override
  public void update() {
    sFeeCategoryManager.update(this);
  }

  @Override
  public MutableFeeCategory edit() {
    return new PersistentFeeCategory(this);
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
  public void delete() {
    sFeeCategoryManager.delete(this);
  }

  @Override
  public void setName(String pName) {
    mName = pName;
  }

  @Override
  public void setDescription(String pDescription) {
    mDescription = pDescription;
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public String getDescription() {
    return mDescription;
  }

  @Override
  public void setFeeId(String pId) {
    mFeeId = pId;
  }

  @Override
  public String getFeeId() {
    return mFeeId;
  }

  @Override
  public FeeType getType() {
    return mFeeType == null ? sFeeTypeManager.get(mFeeTypeId) : sFeeTypeManager.validate(mFeeType);
  }

  @Override
  public void setType(FeeType pType) {
    mFeeType = pType;
  }

  @Override
  public Integer getFeeTypeId() {
    return mFeeTypeId;
  }

  @Override
  public void setFeeTypeId(Integer pTypeId) {
    mFeeTypeId = pTypeId;
  }
}
