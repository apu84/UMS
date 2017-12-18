package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.library.MutableCheckout;
import org.ums.manager.library.CheckoutManager;

import java.util.Date;

public class PersistentCheckout implements MutableCheckout {

  private static CheckoutManager sCheckoutManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCheckoutManager = applicationContext.getBean("checkoutManager", CheckoutManager.class);
  }

  private Long mId;
  private String mPatronId;
  private Long mMfn;
  private Date mIssueDate;
  private Date mDueDate;
  private String mLastModified;

  public PersistentCheckout() {}

  public PersistentCheckout(PersistentCheckout persistentCheckout) {
    mId = persistentCheckout.getId();
    mPatronId = persistentCheckout.getPatronId();
    mMfn = persistentCheckout.getMfn();
    mIssueDate = persistentCheckout.getIssueDate();
    mDueDate = persistentCheckout.getDueDate();
    mLastModified = persistentCheckout.getLastModified();
  }

  @Override
  public MutableCheckout edit() {
    return new PersistentCheckout(this);
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
}
