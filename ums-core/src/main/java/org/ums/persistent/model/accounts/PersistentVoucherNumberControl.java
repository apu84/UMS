package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableVoucherNumberControl;
import org.ums.enums.accounts.definitions.voucher.number.control.ResetBasis;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.manager.accounts.VoucherManager;
import org.ums.manager.accounts.VoucherNumberControlManager;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
public class PersistentVoucherNumberControl implements MutableVoucherNumberControl {
  @JsonIgnore
  private static FinancialAccountYearManager sFinancialAccountYearManager;
  @JsonIgnore
  private static VoucherNumberControlManager sVoucherNumberControlManager;
  @JsonIgnore
  private static VoucherManager sVoucherManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFinancialAccountYearManager =
        applicationContext.getBean("financialAccountManager", FinancialAccountYearManager.class);
    sVoucherManager = applicationContext.getBean("voucherManager", VoucherManager.class);
    sVoucherNumberControlManager =
        applicationContext.getBean("voucherNumberControlManager", VoucherNumberControlManager.class);
  }

  @JsonIgnore
  @JsonProperty("id")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long mId;
  @JsonIgnore
  @JsonProperty("finAccountYearId")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long mFinAccountYearId;
  @JsonIgnore
  @JsonProperty("finAccountYear")
  @JsonBackReference
  private FinancialAccountYear mFinancialAccountYear;
  @JsonIgnore
  @JsonProperty("voucherId")
  private Long mVoucherId;
  @JsonIgnore
  @JsonProperty("voucher")
  @JsonBackReference
  private Voucher mVoucher;
  @JsonIgnore
  @JsonProperty("resetBasis")
  private ResetBasis mResetBasis;
  @JsonIgnore
  @JsonProperty("startVoucherNo")
  private Integer mStartVoucherNo;
  @JsonIgnore
  @JsonProperty("voucherLimit")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private BigDecimal mVoucherLimit;
  @JsonIgnore
  @JsonProperty("statFlag")
  private String mStatFlag;
  @JsonIgnore
  @JsonProperty("statUpFlag")
  private String mStatUpFlag;
  @JsonIgnore
  @JsonProperty("modifiedDate")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private Date mModifiedDate;
  @JsonIgnore
  @JsonProperty("modifiedBy")
  private String mModifiedBy;

  public PersistentVoucherNumberControl() {}

  public PersistentVoucherNumberControl(final PersistentVoucherNumberControl pPersistentVoucherNumberControl) {
    mId = pPersistentVoucherNumberControl.getId();
    mFinAccountYearId = pPersistentVoucherNumberControl.getFinAccountYearId();
    mVoucher = pPersistentVoucherNumberControl.getVoucher();
    mResetBasis = pPersistentVoucherNumberControl.getResetBasis();
    mStartVoucherNo = pPersistentVoucherNumberControl.getStartVoucherNo();
    mVoucherLimit = pPersistentVoucherNumberControl.getVoucherLimit();
    mStatFlag = pPersistentVoucherNumberControl.getStatFlag();
    mStatUpFlag = pPersistentVoucherNumberControl.getStatUpFlag();
    mModifiedDate = pPersistentVoucherNumberControl.getModifiedDate();
    mModifiedBy = pPersistentVoucherNumberControl.getModifiedBy();
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public Long getVoucherId() {
    return mVoucherId;
  }

  public void setVoucherId(Long pVoucherId) {
    mVoucherId = pVoucherId;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public Long getFinAccountYearId() {
    return mFinAccountYearId;
  }

  @Override
  public void setFinAccountYearId(Long pFinAccountYearId) {
    mFinAccountYearId = pFinAccountYearId;
  }

  @Override
  public Voucher getVoucher() {
    return mVoucher == null ? sVoucherManager.get(mVoucherId) : mVoucher;
  }

  @Override
  public ResetBasis getResetBasis() {
    return mResetBasis;
  }

  @Override
  public void setResetBasis(ResetBasis pResetBasis) {
    mResetBasis = pResetBasis;
  }

  public Integer getStartVoucherNo() {
    return mStartVoucherNo;
  }

  public void setStartVoucherNo(Integer pStartVoucherNo) {
    mStartVoucherNo = pStartVoucherNo;
  }

  @Override
  public BigDecimal getVoucherLimit() {
    return mVoucherLimit;
  }

  @Override
  public void setVoucherLimit(BigDecimal pVoucherLimit) {
    mVoucherLimit = pVoucherLimit;
  }

  @Override
  public String getStatFlag() {
    return mStatFlag;
  }

  @Override
  public void setStatFlag(String pStatFlag) {
    mStatFlag = pStatFlag;
  }

  @Override
  public String getStatUpFlag() {
    return mStatUpFlag;
  }

  @Override
  public void setStatUpFlag(String pStatUpFlag) {
    mStatUpFlag = pStatUpFlag;
  }

  @Override
  public Date getModifiedDate() {
    return mModifiedDate;
  }

  @Override
  public void setModifiedDate(Date pModifiedDate) {
    mModifiedDate = pModifiedDate;
  }

  @Override
  public String getModifiedBy() {
    return mModifiedBy;
  }

  @Override
  public void setModifiedBy(String pModifiedBy) {
    mModifiedBy = pModifiedBy;
  }

  @Override
  public MutableVoucherNumberControl edit() {
    return null;
  }

  @Override
  public Long create() {
    return sVoucherNumberControlManager.create(this);
  }

  @Override
  public void update() {
    sVoucherNumberControlManager.update(this);
  }

  @Override
  public void delete() {
    sVoucherNumberControlManager.delete(this);
  }

  @Override
  public FinancialAccountYear getFinAccountYear() {
    return mFinancialAccountYear == null ? sFinancialAccountYearManager.get(mFinAccountYearId) : mFinancialAccountYear;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
