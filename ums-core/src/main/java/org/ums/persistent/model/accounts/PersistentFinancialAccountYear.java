package org.ums.persistent.model.accounts;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear;
import org.ums.enums.accounts.definitions.financial.account.year.BookClosingFlagType;
import org.ums.enums.accounts.definitions.financial.account.year.YearClosingFlagType;
import org.ums.manager.accounts.FinancialAccountYearManager;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class PersistentFinancialAccountYear implements MutableFinancialAccountYear {

  private static FinancialAccountYearManager sFinancialAccountYearManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFinancialAccountYearManager =
        applicationContext.getBean("financialAccountYearManager", FinancialAccountYearManager.class);
  }

  private Long mId;
  private String mStringId;
  private Date mCurrentStartDate;
  private Date mCurrentEndDate;
  private Date mPreviousStartDate;
  private Date mPreviousEndDate;
  private BookClosingFlagType mBookClosingFlag;
  private BigDecimal mItLimit;
  private YearClosingFlagType mYearClosingFlag;
  private String mStatFlag;
  private String mStatUpFlag;
  private Date mModifiedDate;
  private String mModifiedBy;

  public PersistentFinancialAccountYear() {}

  public PersistentFinancialAccountYear(final PersistentFinancialAccountYear pPersistentFinancialAccountYear) {
    mId = pPersistentFinancialAccountYear.getId();
    mStringId = pPersistentFinancialAccountYear.getStringId();
    mCurrentStartDate = pPersistentFinancialAccountYear.getCurrentStartDate();
    mCurrentEndDate = pPersistentFinancialAccountYear.getCurrentEndDate();
    mPreviousStartDate = pPersistentFinancialAccountYear.getPreviousStartDate();
    mPreviousEndDate = pPersistentFinancialAccountYear.getPreviousEndDate();
    mBookClosingFlag = pPersistentFinancialAccountYear.getBookClosingFlag();
    mItLimit = pPersistentFinancialAccountYear.getItLimit();
    mYearClosingFlag = pPersistentFinancialAccountYear.getYearClosingFlag();
    mStatFlag = pPersistentFinancialAccountYear.getStatFlag();
    mStatUpFlag = pPersistentFinancialAccountYear.getStatUpFlag();
    mModifiedDate = pPersistentFinancialAccountYear.getModifiedDate();
    mModifiedBy = pPersistentFinancialAccountYear.getModifiedBy();
  }

  @Override
  public Long getId() {
    return mId == null ? Long.parseLong(getStringId()) : mId;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public Date getCurrentStartDate() {
    return mCurrentStartDate;
  }

  @Override
  public void setCurrentStartDate(Date pCurrentStartDate) {
    mCurrentStartDate = pCurrentStartDate;
  }

  @Override
  public Date getCurrentEndDate() {
    return mCurrentEndDate;
  }

  @Override
  public void setCurrentEndDate(Date pCurrentEndDate) {
    mCurrentEndDate = pCurrentEndDate;
  }

  @Override
  public Date getPreviousStartDate() {
    return mPreviousStartDate;
  }

  @Override
  public void setPreviousStartDate(Date pPreviousStartDate) {
    mPreviousStartDate = pPreviousStartDate;
  }

  @Override
  public Date getPreviousEndDate() {
    return mPreviousEndDate;
  }

  @Override
  public void setPreviousEndDate(Date pPreviousEndDate) {
    mPreviousEndDate = pPreviousEndDate;
  }

  @Override
  public BookClosingFlagType getBookClosingFlag() {
    return mBookClosingFlag;
  }

  @Override
  public void setBookClosingFlag(BookClosingFlagType pBookClosingFlag) {
    mBookClosingFlag = pBookClosingFlag;
  }

  @Override
  public BigDecimal getItLimit() {
    return mItLimit;
  }

  @Override
  public void setItLimit(BigDecimal pItLimit) {
    mItLimit = pItLimit;
  }

  @Override
  public YearClosingFlagType getYearClosingFlag() {
    return mYearClosingFlag;
  }

  @Override
  public void setYearClosingFlag(YearClosingFlagType pYearClosingFlag) {
    mYearClosingFlag = pYearClosingFlag;
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
  public void setStringId(Long pId) {
    mStringId = pId.toString();
  }

  @Override
  public MutableFinancialAccountYear edit() {
    return new PersistentFinancialAccountYear(this);
  }

  @Override
  public Long create() {
    return sFinancialAccountYearManager.create(this);
  }

  @Override
  public void update() {
    sFinancialAccountYearManager.update(this);
  }

  @Override
  public void delete() {
    sFinancialAccountYearManager.delete(this);
  }

  @Override
  public String getStringId() {
    return mStringId;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
