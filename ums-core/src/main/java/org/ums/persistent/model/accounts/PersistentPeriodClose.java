package org.ums.persistent.model.accounts;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.immutable.accounts.Month;
import org.ums.domain.model.mutable.accounts.MutablePeriodClose;
import org.ums.enums.accounts.definitions.OpenCloseFlag;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.manager.accounts.MonthManager;
import org.ums.manager.accounts.PeriodCloseManager;

import java.util.Date;

public class PersistentPeriodClose implements MutablePeriodClose {

  private static MonthManager sMonthManager;
  private static FinancialAccountYearManager sFinancialAccountYearManager;
  private static PeriodCloseManager sPeriodCloseManager;
  private Long mId;
  private Month mMonth;
  private Long mMonthId;
  private FinancialAccountYear mFinancialAccountYear;
  private Long mFinancialAccountYearId;
  private Integer mCloseYear;
  private OpenCloseFlag mPeriodClosingFlag;
  private String mStatFlag;
  private String mStatUpFlag;
  private Date mModifiedDate;
  private String mModifiedBy;
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
  public Month getMonth() {
    return mMonth == null ? sMonthManager.get(mMonthId) : sMonthManager.validate(mMonth);
  }

  @Override
  public void setMonth(Month pMonth) {
    this.mMonth = pMonth;
  }

  @Override
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
  public void setFinancialAccountYear(FinancialAccountYear pFinancialAccountYear) {
    this.mFinancialAccountYear = pFinancialAccountYear;
  }

  @Override
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
    setMonth(pPeriodClose.getMonth());
    setMonthId(pPeriodClose.getMonthId());
    setFinancialAccountYear(pPeriodClose.getFinancialAccountYear());
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
