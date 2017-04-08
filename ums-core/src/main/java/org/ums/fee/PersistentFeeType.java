package org.ums.fee;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentFeeType implements MutableFeeType {

  private static FeeTypeManager sFeeTypeManager;
  private Integer mId;
  private String mDescription;
  private String mLastModified;

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void setId(Integer pId) {
    this.mId = pId;
  }

  @Override
  public String getDescription() {
    return mDescription;
  }

  @Override
  public void setDescription(String pDescription) {
    this.mDescription = pDescription;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public Integer create() {
    return sFeeTypeManager.create(this);
  }

  @Override
  public void update() {
    sFeeTypeManager.update(this);
  }

  @Override
  public MutableFeeType edit() {
    return new PersistentFeeType(this);
  }

  @Override
  public void delete() {
    sFeeTypeManager.delete(this);
  }

  public PersistentFeeType() {}

  public PersistentFeeType(MutableFeeType pFeeType) {
    setId(pFeeType.getId());
    setDescription(pFeeType.getDescription());
    setLastModified(pFeeType.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFeeTypeManager = applicationContext.getBean("feeTypeManager", FeeTypeManager.class);
  }
}
