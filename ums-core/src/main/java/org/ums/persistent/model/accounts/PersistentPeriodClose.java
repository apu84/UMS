package org.ums.persistent.model.accounts;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.immutable.accounts.Month;
import org.ums.domain.model.immutable.accounts.PeriodClose;
import org.ums.domain.model.mutable.accounts.MutablePeriodClose;
import org.ums.enums.accounts.definitions.OpenCloseFlag;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.manager.accounts.MonthManager;
import org.ums.manager.accounts.PeriodCloseManager;

import java.util.Date;

@JsonDeserialize(as = PeriodClose.class)
public class PersistentPeriodClose implements MutablePeriodClose {

  @JsonIgnore
  private static MonthManager sMonthManager;
  @JsonIgnore
  private static FinancialAccountYearManager sFinancialAccountYearManager;
  @JsonIgnore
  private static PeriodCloseManager sPeriodCloseManager;

  @JsonProperty("id")
  @JsonIgnore
  private Long mId;
  @JsonProperty("month")
  @JsonIgnore
  @JsonManagedReference
  private Month mMonth;
  @JsonProperty("monthId")
  @JsonIgnore
  private Long mMonthId;
  @JsonProperty("financialAccountYear")
  @JsonIgnore
  private FinancialAccountYear mFinancialAccountYear;
  @JsonProperty("financialAccountYearId")
  @JsonIgnore
  private Long mFinancialAccountYearId;
  @JsonProperty("closeYear")
  @JsonIgnore
  private Integer mCloseYear;
  @JsonProperty("periodCloseFlag")
  @JsonIgnore
  private OpenCloseFlag mPeriodClosingFlag;
  @JsonProperty("statFlag")
  @JsonIgnore
  private String mStatFlag;
  @JsonProperty("statUpFlag")
  @JsonIgnore
  private String mStatUpFlag;
  @JsonProperty("modifiedDate")
  @JsonIgnore
  private Date mModifiedDate;
  @JsonProperty("modifiedBy")
  @JsonIgnore
  private String mModifiedBy;
  @JsonProperty("lastModified")
  @JsonIgnore
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
  public Month getMonth() {
    return mMonth == null ? sMonthManager.get(mMonthId) : sMonthManager.validate(mMonth);
  }

  @JsonIgnore
  public void setMonth(Month pMonth) {
    mMonth = pMonth;
  }

  @Override
  @JsonSerialize(using = ToStringSerializer.class)
  public Long getMonthId() {
    return mMonthId;
  }

  @Override
  public void setMonthId(Long pMonthId) {
    this.mMonthId = pMonthId;
  }

  @Override
  public FinancialAccountYear getFinancialAccountYear() {
    return mFinancialAccountYear == null ? sFinancialAccountYearManager.get(mFinancialAccountYearId)
        : sFinancialAccountYearManager.validate(mFinancialAccountYear);
  }

  @Override
  @JsonSerialize(using = ToStringSerializer.class)
  public Long getFinancialAccountYearId() {
    return mFinancialAccountYearId;
  }

  @Override
  public void setFinancialAccountYearId(Long pFinancialAccountYearId) {
    this.mFinancialAccountYearId = pFinancialAccountYearId;
  }

  @Override
  public Integer getCloseYear() {
    return mCloseYear;
  }

  @Override
  public void setCloseYear(Integer pCloseYear) {
    this.mCloseYear = pCloseYear;
  }

  @Override
  public OpenCloseFlag getPeriodClosingFlag() {
    return mPeriodClosingFlag;
  }

  @Override
  public void setPeriodClosingFlag(OpenCloseFlag pPeriodClosingFlag) {
    this.mPeriodClosingFlag = pPeriodClosingFlag;
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
    return sPeriodCloseManager.create(this);
  }

  @Override
  public void update() {
    sPeriodCloseManager.update(this);
  }

  @Override
  public MutablePeriodClose edit() {
    return new PersistentPeriodClose(this);
  }

  @Override
  public void delete() {
    sPeriodCloseManager.delete(this);
  }

  public PersistentPeriodClose() {}

  public PersistentPeriodClose(MutablePeriodClose pPeriodClose) {
    setId(pPeriodClose.getId());
    setMonthId(pPeriodClose.getMonthId());
    setFinancialAccountYearId(pPeriodClose.getFinancialAccountYearId());
    setCloseYear(pPeriodClose.getCloseYear());
    setPeriodClosingFlag(pPeriodClose.getPeriodClosingFlag());
    setStatFlag(pPeriodClose.getStatFlag());
    setStatUpFlag(pPeriodClose.getStatUpFlag());
    setModifiedDate(pPeriodClose.getModifiedDate());
    setModifiedBy(pPeriodClose.getModifiedBy());
    setLastModified(pPeriodClose.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sMonthManager = applicationContext.getBean("monthManager", MonthManager.class);
    sFinancialAccountYearManager =
        applicationContext.getBean("financialAccountYearManager", FinancialAccountYearManager.class);
    sPeriodCloseManager = applicationContext.getBean("periodCloseManager", PeriodCloseManager.class);
  }
}
