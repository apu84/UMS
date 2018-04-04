package org.ums.bank;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentBank implements MutableBank {

  private static BankManager sBankManager;
  private Long mId;
  private String mCode;
  private String mName;
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
  public void setCode(String pCode) {
    mCode = pCode;
  }

  @Override
  public String getCode() {
    return mCode;
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
    setCode(pBank.getCode());
    setName(pBank.getName());
    setLastModified(pBank.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sBankManager = applicationContext.getBean("bankManager", BankManager.class);
  }
}
