package org.ums.bank.branch;

import org.springframework.context.ApplicationContext;
import org.ums.bank.Bank;
import org.ums.bank.BankManager;
import org.ums.context.AppContext;

public class PersistentBranch implements MutableBranch {

  private static BankManager sBankManager;
  private static BranchManager sBranchManager;
  private Long mId;
  private String mCode;
  private Bank mBank;
  private Long mBankId;
  private String mName;
  private String mContactNo;
  private String mLastModified;
  private String mLocation;

  @Override
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
  public Bank getBank() {
    return mBank == null ? sBankManager.get(mBankId) : sBankManager.validate(mBank);
  }

  @Override
  public void setBank(Bank pBank) {
    this.mBank = pBank;
  }

  @Override
  public Long getBankId() {
    return mBankId;
  }

  @Override
  public void setBankId(Long pBankId) {
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
  public void setLocation(String pLocation) {
    mLocation = pLocation;
  }

  @Override
  public String getLocation() {
    return mLocation;
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
    setCode(pBranch.getCode());
    setBank(pBranch.getBank());
    setBankId(pBranch.getBankId());
    setName(pBranch.getName());
    setContactNo(pBranch.getContactNo());
    setLocation(pBranch.getLocation());
    setLastModified(pBranch.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sBankManager = applicationContext.getBean("bankManager", BankManager.class);
    sBranchManager = applicationContext.getBean("branchManager", BranchManager.class);
  }
}
