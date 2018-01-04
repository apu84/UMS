package org.ums.persistent.model.accounts;

import org.ums.domain.model.mutable.accounts.MutablePeriodClose;
import org.ums.enums.accounts.definitions.MonthType;
import org.ums.enums.accounts.definitions.OpenCloseFlag;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
public class PersistentPeriodClose implements MutablePeriodClose {

  private Long mId;
  private Long mFinAccountYearId;
  private MonthType mCloseMonth;
  private Integer mCloseYear;
  private OpenCloseFlag mPeriodClosingFlag;
  private String mStatFlag;
  private String mStatUpFlag;
  private Date mModifiedDate;
  private String mModifiedBy;

  public PersistentPeriodClose() {}

  @Override
  public Long getId() {
    return mId;
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
  public MonthType getCloseMonth() {
    return mCloseMonth;
  }

  @Override
  public void setCloseMonth(MonthType pCloseMonth) {
    mCloseMonth = pCloseMonth;
  }

  @Override
  public Integer getCloseYear() {
    return mCloseYear;
  }

  @Override
  public void setCloseYear(Integer pCloseYear) {
    mCloseYear = pCloseYear;
  }

  @Override
  public OpenCloseFlag getPeriodClosingFlag() {
    return mPeriodClosingFlag;
  }

  @Override
  public void setPeriodClosingFlag(OpenCloseFlag pPeriodClosingFlag) {
    mPeriodClosingFlag = pPeriodClosingFlag;
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
  public MutablePeriodClose edit() {
    return null;
  }

  @Override
  public Long create() {
    return null;
  }

  @Override
  public void update() {

  }

  @Override
  public void delete() {

  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
