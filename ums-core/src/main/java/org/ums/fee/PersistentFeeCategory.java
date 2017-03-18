package org.ums.fee;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentFeeCategory implements MutableFeeCategory {
  private static FeeCategoryManager sFeeCategoryManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFeeCategoryManager =
        applicationContext.getBean("feeCategoryManager", FeeCategoryManager.class);
  }
  private String mId;
  private String mFeeId;
  private FeeCategory.Type mType;
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
  public void commit(boolean update) {
    if(update) {
      sFeeCategoryManager.update(this);
    }
    else {
      sFeeCategoryManager.create(this);
    }
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
  public void setType(Type pType) {
    mType = pType;
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
  public Type getType() {
    return mType;
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
}
