package org.ums.bank.branch.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang.Validate;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.bank.branch.Branch;
import org.ums.bank.branch.BranchManager;
import org.ums.bank.designation.BankDesignation;
import org.ums.bank.designation.BankDesignationManager;

public class PersistentBranchUser implements MutableBranchUser {
  private static BranchManager sBranchManager;
  private static BankDesignationManager sBankDesignationManager;
  private static BranchUserManager sBranchUserManager;
  private Long mId;
  private String mUserId;
  private Branch mBranch;
  private Long mBranchId;
  private String mName;
  private BankDesignation mBankDesignation;
  private Long mBankDesignationId;
  private String mEmail;
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
  public void setUserId(String pUserId) {
    mUserId = pUserId;
  }

  @Override
  public String getUserId() {
    return mUserId;
  }

  @Override
  public Branch getBranch() {
    return mBranch == null ? sBranchManager.get(mBranchId) : sBranchManager.validate(mBranch);
  }

  @Override
  public void setBranch(Branch pBranch) {
    this.mBranch = pBranch;
  }

  @Override
  @JsonSerialize(using = ToStringSerializer.class)
  public Long getBranchId() {
    return mBranchId;
  }

  @Override
  public void setBranchId(Long pBranchId) {
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
    return mBankDesignation == null ? sBankDesignationManager.get(mBankDesignationId) : sBankDesignationManager
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
  public void setEmail(String pEmail) {
    mEmail = pEmail;
  }

  @Override
  public String getEmail() {
    return mEmail;
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
    Validate.notEmpty(mEmail);
    Validate.notNull(mBranchId);
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

  public PersistentBranchUser() {}

  public PersistentBranchUser(MutableBranchUser pBranchUser) {
    setId(pBranchUser.getId());
    setUserId(pBranchUser.getUserId());
    setBranch(pBranchUser.getBranch());
    setBranchId(pBranchUser.getBranchId());
    setName(pBranchUser.getName());
    setBankDesignation(pBranchUser.getBankDesignation());
    setBankDesignationId(pBranchUser.getBankDesignationId());
    setLastModified(pBranchUser.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sBranchManager = applicationContext.getBean("branchManager", BranchManager.class);
    sBankDesignationManager = applicationContext.getBean("bankDesignationManager", BankDesignationManager.class);
    sBranchUserManager = applicationContext.getBean("branchUserManager", BranchUserManager.class);
  }
}
