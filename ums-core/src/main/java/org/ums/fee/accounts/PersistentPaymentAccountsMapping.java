package org.ums.fee.accounts;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Faculty;
import org.ums.fee.FeeType;
import org.ums.fee.FeeTypeManager;
import org.ums.manager.FacultyManager;

public class PersistentPaymentAccountsMapping implements MutablePaymentAccountsMapping {

  private static FeeTypeManager sFeeTypeManager;
  private static FacultyManager sFacultyManager;
  private static PaymentAccountsMappingManager sPaymentAccountsMappingManager;
  private Long mId;
  private FeeType mFeeType;
  private Integer mFeeTypeId;
  private Faculty mFaculty;
  private Integer mFacultyId;
  private String mAccount;
  private String mAccountDetails;
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
  public FeeType getFeeType() {
    return mFeeType == null ? sFeeTypeManager.get(mFeeTypeId) : sFeeTypeManager.validate(mFeeType);
  }

  @Override
  public void setFeeType(FeeType pFeeType) {
    this.mFeeType = pFeeType;
  }

  @Override
  public Integer getFeeTypeId() {
    return mFeeTypeId;
  }

  @Override
  public void setFeeTypeId(Integer pFeeTypeId) {
    this.mFeeTypeId = pFeeTypeId;
  }

  @Override
  public Faculty getFaculty() {
    return mFaculty == null ? sFacultyManager.get(mFacultyId) : sFacultyManager.validate(mFaculty);
  }

  @Override
  public void setFaculty(Faculty pFaculty) {
    this.mFaculty = pFaculty;
  }

  @Override
  public Integer getFacultyId() {
    return mFacultyId;
  }

  @Override
  public void setFacultyId(Integer pFacultyId) {
    this.mFacultyId = pFacultyId;
  }

  @Override
  public String getAccount() {
    return mAccount;
  }

  @Override
  public void setAccount(String pAccount) {
    this.mAccount = pAccount;
  }

  @Override
  public String getAccountDetails() {
    return mAccountDetails;
  }

  @Override
  public void setAccountDetails(String pAccountDetails) {
    this.mAccountDetails = pAccountDetails;
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
    return sPaymentAccountsMappingManager.create(this);
  }

  @Override
  public void update() {
    sPaymentAccountsMappingManager.update(this);
  }

  @Override
  public MutablePaymentAccountsMapping edit() {
    return new PersistentPaymentAccountsMapping(this);
  }

  @Override
  public void delete() {
    sPaymentAccountsMappingManager.delete(this);
  }

  public PersistentPaymentAccountsMapping() {}

  public PersistentPaymentAccountsMapping(MutablePaymentAccountsMapping pPaymentAccountsMapping) {
    setId(pPaymentAccountsMapping.getId());
    setFeeType(pPaymentAccountsMapping.getFeeType());
    setFeeTypeId(pPaymentAccountsMapping.getFeeTypeId());
    setFaculty(pPaymentAccountsMapping.getFaculty());
    setFacultyId(pPaymentAccountsMapping.getFacultyId());
    setAccount(pPaymentAccountsMapping.getAccount());
    setAccountDetails(pPaymentAccountsMapping.getAccountDetails());
    setLastModified(pPaymentAccountsMapping.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFeeTypeManager = applicationContext.getBean("feeTypeManager", FeeTypeManager.class);
    sFacultyManager = applicationContext.getBean("facultyManager", FacultyManager.class);
    sPaymentAccountsMappingManager =
        applicationContext.getBean("paymentAccountsMappingManager", PaymentAccountsMappingManager.class);
  }
}
