package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.*;
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
        applicationContext.getBean("financialAccountYearManager", FinancialAccountYearManager.class);
    sVoucherManager = applicationContext.getBean("voucherManager", VoucherManager.class);
    sVoucherNumberControlManager =
        applicationContext.getBean("voucherNumberControlManager", VoucherNumberControlManager.class);
  }

  @JsonIgnore
  @JsonProperty("id")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  @JsonIgnore
  @JsonProperty("finAccountYearId")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long finAccountYearId;
  @JsonProperty("finAccountYear")
  @JsonIgnore
  private FinancialAccountYear finAccountYear;
  @JsonIgnore
  @JsonProperty("voucherId")
  private Long voucherId;
  @JsonProperty("voucher")
  @JsonBackReference
  @JsonIgnore
  private Voucher voucher;
  @JsonIgnore
  @JsonProperty("resetBasis")
  private ResetBasis resetBasis;
  @JsonIgnore
  @JsonProperty("startVoucherNo")
  private Integer startVoucherNo;
  @JsonIgnore
  @JsonProperty("voucherLimit")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private BigDecimal voucherLimit;
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
    id = pPersistentVoucherNumberControl.getId();
    finAccountYearId = pPersistentVoucherNumberControl.getFinAccountYearId();
    voucher = pPersistentVoucherNumberControl.getVoucher();
    resetBasis = pPersistentVoucherNumberControl.getResetBasis();
    startVoucherNo = pPersistentVoucherNumberControl.getStartVoucherNo();
    voucherLimit = pPersistentVoucherNumberControl.getVoucherLimit();
    mStatFlag = pPersistentVoucherNumberControl.getStatFlag();
    mStatUpFlag = pPersistentVoucherNumberControl.getStatUpFlag();
    mModifiedDate = pPersistentVoucherNumberControl.getModifiedDate();
    mModifiedBy = pPersistentVoucherNumberControl.getModifiedBy();
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public Long getVoucherId() {
    return voucherId;
  }

  public void setVoucherId(Long pVoucherId) {
    voucherId = pVoucherId;
  }

  @Override
  public void setId(Long pId) {
    id = pId;
  }

  @Override
  public Long getFinAccountYearId() {
    return finAccountYearId;
  }

  @Override
  public void setFinAccountYearId(Long pFinAccountYearId) {
    finAccountYearId = pFinAccountYearId;
  }

  @Override
  public Voucher getVoucher() {
    return voucher == null ? sVoucherManager.get(voucherId) : voucher;
  }

  @Override
  public ResetBasis getResetBasis() {
    return resetBasis;
  }

  @Override
  public void setResetBasis(ResetBasis pResetBasis) {
    resetBasis = pResetBasis;
  }

  public Integer getStartVoucherNo() {
    return startVoucherNo;
  }

  public void setStartVoucherNo(Integer pStartVoucherNo) {
    startVoucherNo = pStartVoucherNo;
  }

  @Override
  public BigDecimal getVoucherLimit() {
    return voucherLimit;
  }

  @Override
  public void setVoucherLimit(BigDecimal pVoucherLimit) {
    voucherLimit = pVoucherLimit;
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
    return finAccountYear == null ? sFinancialAccountYearManager.get(finAccountYearId) : finAccountYear;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
