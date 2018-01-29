package org.ums.persistent.model.accounts;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.accounts.MutableReceipt;
import org.ums.manager.accounts.ReceiptManager;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class PersistentReceipt implements MutableReceipt {

  private static ReceiptManager sReceiptManager;
  private Long mId;
  private String mName;
  private String mShortName;
  private String mLastModified;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public void setName(String pName) {
    this.mName = pName;
  }

  @Override
  public String getShortName() {
    return mShortName;
  }

  @Override
  public void setShortName(String pShortName) {
    this.mShortName = pShortName;
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
  public Long create() {
    return sReceiptManager.create(this);
  }

  @Override
  public void update() {
    sReceiptManager.update(this);
  }

  @Override
  public MutableReceipt edit() {
    return new PersistentReceipt(this);
  }

  @Override
  public void delete() {
    sReceiptManager.delete(this);
  }

  public PersistentReceipt() {}

  public PersistentReceipt(MutableReceipt pReceipt) {
    setId(pReceipt.getId());
    setName(pReceipt.getName());
    setShortName(pReceipt.getShortName());
    setLastModified(pReceipt.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sReceiptManager = applicationContext.getBean("receiptManager", ReceiptManager.class);
  }
}
