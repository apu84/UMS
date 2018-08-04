package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.PredefinedNarrationManager;
import org.ums.manager.accounts.VoucherManager;

import java.util.Date;

public class PersistentPredefinedNarration implements MutablePredefinedNarration {

  @JsonIgnore
  private static VoucherManager sVoucherManager;
  @JsonIgnore
  private static CompanyManager sCompanyManager;
  @JsonIgnore
  private static PredefinedNarrationManager sPredefinedNarrationManager;
  @JsonProperty("id")
  private Long mId;
  @JsonIgnore
  @JsonProperty("voucher")
  private Voucher mVoucher;
  @JsonIgnore
  private Company mCompany;
  private String mCompanyId;
  @JsonProperty("voucherId")
  @JsonIgnore
  private Long mVoucherId;
  @JsonProperty("statFlag")
  private String mStatFlag;
  @JsonProperty("statUpFlag")
  private String mStatUpFlag;
  @JsonProperty("modifiedDate")
  private Date mModifiedDate;
  @JsonProperty("modifiedBy")
  private String mModifiedBy;
  @JsonProperty("narration")
  private String mNarration;
  @JsonIgnore
  private String mLastModified;

  @Override
  @JsonProperty("company")
  public Company getCompany() {
    return mCompany == null ? sCompanyManager.get(mCompanyId) : mCompany;
  }

  @Override
  @JsonIgnore
  public void setCompany(Company pCompany) {
    mCompany = pCompany;
  }

  @Override
  public String getCompanyId() {
    return mCompanyId;
  }

  @Override
  public void setCompanyId(String pCompanyId) {
    mCompanyId = pCompanyId;
  }

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
  public void setNarration(String pNarration) {
    mNarration = pNarration;
  }

  @Override
  public String getNarration() {
    return mNarration;
  }

  @Override
  public Voucher getVoucher() {
    return mVoucher == null ? sVoucherManager.get(mVoucherId) : sVoucherManager.validate(mVoucher);
  }

  @Override
  public void setVoucher(Voucher pVoucher) {
    this.mVoucher = pVoucher;
  }

  @Override
  @JsonSerialize(using = ToStringSerializer.class)
  public Long getVoucherId() {
    return mVoucherId;
  }

  @Override
  public void setVoucherId(Long pVoucherId) {
    this.mVoucherId = pVoucherId;
  }

  @Override
  public String getStatFlag() {
    return mStatFlag;
  }

  @Override
  public void setStatFlag(String pStatFlag) {
    this.mStatFlag = pStatFlag;
  }

  @Override
  public String getStatUpFlag() {
    return mStatUpFlag;
  }

  @Override
  public void setStatUpFlag(String pStatUpFlag) {
    this.mStatUpFlag = pStatUpFlag;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  public Date getModifiedDate() {
    return mModifiedDate;
  }

  @Override
  public void setModifiedDate(Date pModifiedDate) {
    this.mModifiedDate = pModifiedDate;
  }

  @Override
  public String getModifiedBy() {
    return mModifiedBy;
  }

  @Override
  public void setModifiedBy(String pModifiedBy) {
    this.mModifiedBy = pModifiedBy;
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
    return sPredefinedNarrationManager.create(this);
  }

  @Override
  public void update() {
    sPredefinedNarrationManager.update(this);
  }

  @Override
  public MutablePredefinedNarration edit() {
    return new PersistentPredefinedNarration(this);
  }

  @Override
  public void delete() {
    sPredefinedNarrationManager.delete(this);
  }

  public PersistentPredefinedNarration() {}

  public PersistentPredefinedNarration(MutablePredefinedNarration pPredefinedNarration) {
    setId(pPredefinedNarration.getId());
    setVoucher(pPredefinedNarration.getVoucher());
    setVoucherId(pPredefinedNarration.getVoucherId());
    setStatFlag(pPredefinedNarration.getStatFlag());
    setStatUpFlag(pPredefinedNarration.getStatUpFlag());
    setModifiedDate(pPredefinedNarration.getModifiedDate());
    setModifiedBy(pPredefinedNarration.getModifiedBy());
    setNarration(pPredefinedNarration.getNarration());
    setLastModified(pPredefinedNarration.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sVoucherManager = applicationContext.getBean("voucherManager", VoucherManager.class);
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);
    sPredefinedNarrationManager =
        applicationContext.getBean("predefinedNarrationManager", PredefinedNarrationManager.class);
  }
}
