package org.ums.bank.designation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentBankDesignation implements MutableBankDesignation {

  private static BankDesignationManager sBankDesignationManager;
  private Long mId;
  private String mCode;
  private String mName;
  private String mLastModified;

  @Override
  @JsonSerialize(using = ToStringSerializer.class)
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
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
    setCode(pBankDesignation.getCode());
    setName(pBankDesignation.getName());
    setLastModified(pBankDesignation.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sBankDesignationManager = applicationContext.getBean("bankDesignationManager", BankDesignationManager.class);
  }
}
