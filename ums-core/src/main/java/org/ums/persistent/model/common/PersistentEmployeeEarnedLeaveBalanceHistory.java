package org.ums.persistent.model.common;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalanceHistory;
import org.ums.enums.common.LeaveBalanceType;
import org.ums.enums.common.LeaveMigrationType;
import org.ums.manager.EmployeeManager;
import org.ums.manager.common.EmployeeEarnedLeaveBalanceHistoryManager;

public class PersistentEmployeeEarnedLeaveBalanceHistory implements MutableEmployeeEarnedLeaveBalanceHistory {

  private static EmployeeManager sEmployeeManager;
  private static EmployeeEarnedLeaveBalanceHistoryManager sEmployeeEarnedLeaveBalanceHistoryManager;
  private Long mId;
  private Employee mEmployee;
  private String mEmployeeId;
  private LeaveMigrationType mLeaveMigrationType;
  private Date mChangedOn;
  private BigDecimal mDebit;
  private BigDecimal mCredit;
  private BigDecimal mBalance;
  private LeaveBalanceType mBalanceType;
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
  public Employee getEmployee() {
    return mEmployee == null ? sEmployeeManager.get(mEmployeeId) : sEmployeeManager.validate(mEmployee);
  }

  @Override
  public void setEmployee(Employee pEmployee) {
    this.mEmployee = pEmployee;
  }

  @Override
  public String getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public void setEmployeeId(String pEmployeeId) {
    this.mEmployeeId = pEmployeeId;
  }

  @Override
  public LeaveMigrationType getLeaveMigrationType() {
    return mLeaveMigrationType;
  }

  @Override
  public void setLeaveMigrationType(LeaveMigrationType pLeaveMigrationType) {
    this.mLeaveMigrationType = pLeaveMigrationType;
  }

  @Override
  public Date getChangedOn() {
    return mChangedOn;
  }

  @Override
  public void setChangedOn(Date pChangedOn) {
    this.mChangedOn = pChangedOn;
  }

  @Override
  public BigDecimal getDebit() {
    return mDebit;
  }

  @Override
  public void setDebit(BigDecimal pDebit) {
    this.mDebit = pDebit;
  }

  @Override
  public BigDecimal getCredit() {
    return mCredit;
  }

  @Override
  public void setCredit(BigDecimal pCredit) {
    this.mCredit = pCredit;
  }

  @Override
  public BigDecimal getBalance() {
    return mBalance;
  }

  @Override
  public void setBalance(BigDecimal pBalance) {
    this.mBalance = pBalance;
  }

  @Override
  public LeaveBalanceType getBalanceType() {
    return mBalanceType;
  }

  @Override
  public void setBalanceType(LeaveBalanceType pBalanceType) {
    this.mBalanceType = pBalanceType;
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
    return sEmployeeEarnedLeaveBalanceHistoryManager.create(this);
  }

  @Override
  public void update() {
    sEmployeeEarnedLeaveBalanceHistoryManager.update(this);
  }

  @Override
  public MutableEmployeeEarnedLeaveBalanceHistory edit() {
    return new PersistentEmployeeEarnedLeaveBalanceHistory(this);
  }

  @Override
  public void delete() {
    sEmployeeEarnedLeaveBalanceHistoryManager.delete(this);
  }

  public PersistentEmployeeEarnedLeaveBalanceHistory() {}

  public PersistentEmployeeEarnedLeaveBalanceHistory(
      MutableEmployeeEarnedLeaveBalanceHistory pEmployeeEarnedLeaveBalanceHistory) {
    setId(pEmployeeEarnedLeaveBalanceHistory.getId());
    setEmployee(pEmployeeEarnedLeaveBalanceHistory.getEmployee());
    setEmployeeId(pEmployeeEarnedLeaveBalanceHistory.getEmployeeId());
    setLeaveMigrationType(pEmployeeEarnedLeaveBalanceHistory.getLeaveMigrationType());
    setChangedOn(pEmployeeEarnedLeaveBalanceHistory.getChangedOn());
    setDebit(pEmployeeEarnedLeaveBalanceHistory.getDebit());
    setCredit(pEmployeeEarnedLeaveBalanceHistory.getCredit());
    setBalance(pEmployeeEarnedLeaveBalanceHistory.getBalance());
    setBalanceType(pEmployeeEarnedLeaveBalanceHistory.getBalanceType());
    setLastModified(pEmployeeEarnedLeaveBalanceHistory.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sEmployeeManager = applicationContext.getBean("employeeManager", EmployeeManager.class);
    sEmployeeEarnedLeaveBalanceHistoryManager =
        applicationContext.getBean("employeeEarnedLeaveBalanceHistoryManager",
            EmployeeEarnedLeaveBalanceHistoryManager.class);
  }
}
