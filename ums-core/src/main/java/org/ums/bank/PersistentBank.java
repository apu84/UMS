package org.ums.bank;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentBank implements MutableBank {

  private static BankManager sBankManager;
  private String mId;
  private String mName;
  private String mLastModified;

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void setId(String pId) {
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
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public String create() {
    return sBankManager.create(this);
  }

  @Override
  public void update() {
    sBankManager.update(this);
  }

  @Override
  public MutableBank edit() {
    return new PersistentBank(this);
  }

  @Override
  public void delete() {
    sBankManager.delete(this);
  }

  public PersistentBank() {}

  public PersistentBank(MutableBank pBank) {
    setId(pBank.getId());
    setName(pBank.getName());
    setLastModified(pBank.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sBankManager = applicationContext.getBean("bankManager", BankManager.class);
  }
}
