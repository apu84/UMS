package org.ums.bank;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentBranch implements MutableBranch {

  private static BankManager sBankManager;
  private static BranchManager sBranchManager;
  private String mId;
  private Bank mBank;
  private String mBankId;
  private String mName;
  private String mContactNo;
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
  public Bank getBank() {
    return mBank == null ? sBankManager.get(mBankId) : sBankManager.validate(mBank);
  }

  @Override
  public void setBank(Bank pBank) {
    this.mBank = pBank;
  }

  @Override
  public String getBankId() {
    return mBankId;
  }

  @Override
  public void setBankId(String pBankId) {
    this.mBankId = pBankId;
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
  public String getContactNo() {
    return mContactNo;
  }

  @Override
  public void setContactNo(String pContacatNo) {
    this.mContactNo = pContacatNo;
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
    return sBranchManager.create(this);
  }

  @Override
  public void update() {
    sBranchManager.update(this);
  }

  @Override
  public MutableBranch edit() {
    return new PersistentBranch(this);
  }

  @Override
  public void delete() {
    sBranchManager.delete(this);
  }

  public PersistentBranch() {}

  public PersistentBranch(MutableBranch pBranch) {
    setId(pBranch.getId());
    setBank(pBranch.getBank());
    setBankId(pBranch.getBankId());
    setName(pBranch.getName());
    setContactNo(pBranch.getContactNo());
    setLastModified(pBranch.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sBankManager = applicationContext.getBean("bankManager", BankManager.class);
    sBranchManager = applicationContext.getBean("branchManager", BranchManager.class);
  }
}
