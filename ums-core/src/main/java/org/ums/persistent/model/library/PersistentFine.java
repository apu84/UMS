package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.manager.library.FineManager;

import java.util.Date;

public class PersistentFine implements MutableFine {

  private static FineManager sFineManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFineManager = applicationContext.getBean("fineManager", FineManager.class);
  }

  private Long mId;
  private Long mCheckInId;
  private int mFineCategory;
  private Date mFineAppliedDate;
  private String mFineAppliedBy;
  private String mFineForgivenBy;
  private Date mFinePaymentDate;
  private String mDescription;
  private double mAmount;
  private String mPatronId;
  private String mLastModified;

  public PersistentFine() {}

  public PersistentFine(PersistentFine persistentFine) {
    mId = persistentFine.getId();
    mCheckInId = persistentFine.getCirculationId();
    mFineCategory = persistentFine.getFineCategory();
    mFineAppliedDate = persistentFine.getFineAppliedDate();
    mFineAppliedBy = persistentFine.getFineAppliedBy();
    mFineForgivenBy = persistentFine.getFineForgivenBy();
    mFinePaymentDate = persistentFine.getFinePaymentDate();
    mDescription = persistentFine.getDescription();
    mAmount = persistentFine.getAmount();
    mPatronId = persistentFine.getPatronId();
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
  public void setCirculationId(Long pCheckInId) {
    mCheckInId = pCheckInId;
  }

  @Override
  public void setFineCategory(int pFineCategory) {
    mFineCategory = pFineCategory;
  }

  @Override
  public void setFineAppliedDate(Date pFineAppliedDate) {
    mFineAppliedDate = pFineAppliedDate;
  }

  @Override
  public void setFineAppliedBy(String pFineAppliedBy) {
    mFineAppliedBy = pFineAppliedBy;
  }

  @Override
  public void setFineForgivenBy(String pFineForgivenBy) {
    mFineForgivenBy = pFineForgivenBy;
  }

  @Override
  public void setFinePaymentDate(Date pFinePaymentDate) {
    mFinePaymentDate = pFinePaymentDate;
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
  public void setPatronId(String pPatronId) {
    mPatronId = pPatronId;
  }

  @Override
  public Long getCirculationId() {
    return mCheckInId;
  }

  @Override
  public int getFineCategory() {
    return mFineCategory;
  }

  @Override
  public Date getFineAppliedDate() {
    return mFineAppliedDate;
  }

  @Override
  public String getFineAppliedBy() {
    return mFineAppliedBy;
  }

  @Override
  public String getFineForgivenBy() {
    return mFineForgivenBy;
  }

  @Override
  public Date getFinePaymentDate() {
    return mFinePaymentDate;
  }

  @Override
  public String getDescription() {
    return mDescription;
  }

  @Override
  public double getAmount() {
    return mAmount;
  }

  @Override
  public String getPatronId() {
    return mPatronId;
  }
}
