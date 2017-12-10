package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.manager.library.FineManager;

public class PersistentFine implements MutableFine {

  private static FineManager sFineManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFineManager = applicationContext.getBean("fineManager", FineManager.class);
  }

  private Long mId;
  private Long mCheckInId;
  private int mFineCategory;
  private String mDescription;
  private double mAmount;
  private String mLastModified;

  public PersistentFine() {}

  public PersistentFine(PersistentFine persistentFine) {
    mId = persistentFine.getId();
    mCheckInId = persistentFine.getCheckInId();
    mFineCategory = persistentFine.getFineCategory();
    mDescription = persistentFine.getDescription();
    mAmount = persistentFine.getAmount();
    mLastModified = persistentFine.getLastModified();
  }

  @Override
  public MutableFine edit() {
    return new PersistentFine(this);
  }

  @Override
  public Long create() {
    return sFineManager.create(this);
  }

  @Override
  public void update() {
    sFineManager.update(this);
  }

  @Override
  public void delete() {
    sFineManager.delete(this);
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

  @Override
  public void setCheckInId(Long pCheckInId) {
    mCheckInId = pCheckInId;
  }

  @Override
  public void setFineCategory(int pFineCategory) {
    mFineCategory = pFineCategory;
  }

  @Override
  public void setDescription(String pDescription) {
    mDescription = pDescription;
  }

  @Override
  public void setAmount(double pAmount) {
    mAmount = pAmount;
  }

  @Override
  public Long getCheckInId() {
    return mCheckInId;
  }

  @Override
  public int getFineCategory() {
    return mFineCategory;
  }

  @Override
  public String getDescription() {
    return mDescription;
  }

  @Override
  public double getAmount() {
    return mAmount;
  }
}
