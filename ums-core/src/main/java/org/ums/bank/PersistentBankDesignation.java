package org.ums.bank;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentBankDesignation implements MutableBankDesignation {

  private static BankDesignationManager sBankDesignationManager;
  private Long mId;
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
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public Long create() {
    return sBankDesignationManager.create(this);
  }

  @Override
  public void update() {
    sBankDesignationManager.update(this);
  }

  @Override
  public MutableBankDesignation edit() {
    return new PersistentBankDesignation(this);
  }

  @Override
  public void delete() {
    sBankDesignationManager.delete(this);
  }

  public PersistentBankDesignation() {}

  public PersistentBankDesignation(MutableBankDesignation pBankDesignation) {
    setId(pBankDesignation.getId());
    setName(pBankDesignation.getName());
    setLastModified(pBankDesignation.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sBankDesignationManager = applicationContext.getBean("bankDesignationManager", BankDesignationManager.class);
  }
}
