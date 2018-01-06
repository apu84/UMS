package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.manager.library.CirculationManager;

import java.util.Date;

public class PersistentCirculation implements MutableCirculation {

  private static CirculationManager sCheckoutManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCheckoutManager = applicationContext.getBean("checkoutManager", CirculationManager.class);
  }

  private Long mId;
  private String mPatronId;
  private Long mMfn;
  private Date mIssueDate;
  private Date mDueDate;
  private Date mReturnDate;
  private int mFineStatus;
  private String mAccessionNumber;
  private String mLastModified;

  public PersistentCirculation() {}

  public PersistentCirculation(PersistentCirculation persistentCirculation) {
    mId = persistentCirculation.getId();
    mPatronId = persistentCirculation.getPatronId();
    mMfn = persistentCirculation.getMfn();
    mIssueDate = persistentCirculation.getIssueDate();
    mDueDate = persistentCirculation.getDueDate();
    mReturnDate = persistentCirculation.getReturnDate();
    mFineStatus = persistentCirculation.getFineStatus();
    mAccessionNumber = persistentCirculation.getAccessionNumber();
    mLastModified = persistentCirculation.getLastModified();
  }

  @Override
  public MutableCirculation edit() {
    return new PersistentCirculation(this);
  }

  @Override
  public Long create() {
    return sCheckoutManager.create(this);
  }

  @Override
  public void update() {
    sCheckoutManager.update(this);
  }

  @Override
  public void delete() {
    sCheckoutManager.delete(this);
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
  public void setPatronId(String pPatronId) {
    mPatronId = pPatronId;
  }

  @Override
  public void setMfn(Long pMfn) {
    mMfn = pMfn;
  }

  @Override
  public void setIssueDate(Date pIssueDate) {
    mIssueDate = pIssueDate;
  }

  @Override
  public void setDueDate(Date pDueDate) {
    mDueDate = pDueDate;
  }

  @Override
  public void setReturnDate(Date pReturnDate) {
    mReturnDate = pReturnDate;
  }

  @Override
  public void setFineStatus(int pFineStatus) {
    mFineStatus = pFineStatus;
  }

  @Override
  public void setAccessionNumber(String pAccessionNumber) {
    mAccessionNumber = pAccessionNumber;
  }

  @Override
  public String getPatronId() {
    return mPatronId;
  }

  @Override
  public Long getMfn() {
    return mMfn;
  }

  @Override
  public Date getIssueDate() {
    return mIssueDate;
  }

  @Override
  public Date getDueDate() {
    return mDueDate;
  }

  @Override
  public Date getReturnDate() {
    return mReturnDate;
  }

  @Override
  public int getFineStatus() {
    return mFineStatus;
  }

  @Override
  public String getAccessionNumber() {
    return mAccessionNumber;
  }
}
