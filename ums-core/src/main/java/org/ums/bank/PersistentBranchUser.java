package org.ums.bank;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.bank.BranchUser;
import org.ums.bank.MutableBranchUser;
import org.ums.bank.BranchUserManager;
import org.ums.bank.Branch;
import org.ums.bank.BranchManager;
import org.ums.bank.BankDesignation;
import org.ums.bank.BankDesignationManager;

public class PersistentBranchUser implements MutableBranchUser {

  private static BranchManager sBranchManager;
  private static BankDesignationManager sBankDesignationManager;
  private static BranchUserManager sBranchUserManager;
  private String mId;
  private Branch mBranch;
  private String mBranchId;
  private String mName;
  private BankDesignation mBankDesignation;
  private Long mBankDesignationId;
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
  public Branch getBranch() {
    return mBranch == null ? sBranchManager.get(mBranchId) : sBranchManager
        .validate(mBranch);
  }

  @Override
  public void setBranch(Branch pBranch) {
    this.mBranch = pBranch;
  }

  @Override
  public String getBranchId() {
    return mBranchId;
  }

  @Override
  public void setBranchId(String pBranchId) {
    this.mBranchId = pBranchId;
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
  public BankDesignation getBankDesignation() {
    return mBankDesignation == null ? sBankDesignationManager
        .get(mBankDesignationId) : sBankDesignationManager
        .validate(mBankDesignation);
  }

  @Override
  public void setBankDesignation(BankDesignation pBankDesignation) {
    this.mBankDesignation = pBankDesignation;
  }

  @Override
  public Long getBankDesignationId() {
    return mBankDesignationId;
  }

  @Override
  public void setBankDesignationId(Long pDesignationId) {
    this.mBankDesignationId = pDesignationId;
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
    return sBranchUserManager.create(this);
  }

  @Override
  public void update() {
    sBranchUserManager.update(this);
  }

  @Override
  public MutableBranchUser edit() {
    return new PersistentBranchUser(this);
  }

  @Override
  public void delete() {
    sBranchUserManager.delete(this);
  }

  public PersistentBranchUser() {
  }

  public PersistentBranchUser(MutableBranchUser pBranchUser) {
    setId(pBranchUser.getId());
    setBranch(pBranchUser.getBranch());
    setBranchId(pBranchUser.getBranchId());
    setName(pBranchUser.getName());
    setBankDesignation(pBranchUser.getBankDesignation());
    setBankDesignationId(pBranchUser.getBankDesignationId());
    setLastModified(pBranchUser.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext
        .getApplicationContext();
    sBranchManager = applicationContext.getBean("branchManager",
        BranchManager.class);
    sBankDesignationManager = applicationContext.getBean(
        "bankDesignationManager", BankDesignationManager.class);
    sBranchUserManager = applicationContext.getBean("branchUserManager",
        BranchUserManager.class);
  }
}
