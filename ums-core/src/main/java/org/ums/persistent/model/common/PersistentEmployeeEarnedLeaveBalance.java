package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalance;
import org.ums.manager.EmployeeManager;
import org.ums.manager.common.EmployeeEarnedLeaveBalanceManager;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 26-Jul-18.
 */
public class PersistentEmployeeEarnedLeaveBalance implements MutableEmployeeEarnedLeaveBalance {

  private static EmployeeManager sEmployeeManager;
  private static EmployeeEarnedLeaveBalanceManager sEmployeeEarnedLeaveBalanceManager;
  private Long mId;
  private Employee mEmployee;
  private String mEmployeeId;
  private BigDecimal mFullPay;
  private BigDecimal mHalfPay;
  private Date mCreatedOn;
  private Date mUpdatedOn;
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
  public BigDecimal getFullPay() {
    return mFullPay;
  }

  @Override
  public void setFullPay(BigDecimal pFullPay) {
    this.mFullPay = pFullPay;
  }

  @Override
  public BigDecimal getHalfPay() {
    return mHalfPay;
  }

  @Override
  public void setHalfPay(BigDecimal pHalfPay) {
    this.mHalfPay = pHalfPay;
  }

  @Override
  public Date getCreatedOn() {
    return mCreatedOn;
  }

  @Override
  public void setCreatedOn(Date pCreatedOn) {
    this.mCreatedOn = pCreatedOn;
  }

  @Override
  public Date getUpdatedOn() {
    return mUpdatedOn;
  }

  @Override
  public void setUpdatedOn(Date pUpdatedOn) {
    this.mUpdatedOn = pUpdatedOn;
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
    return sEmployeeEarnedLeaveBalanceManager.create(this);
  }

  @Override
  public void update() {
    sEmployeeEarnedLeaveBalanceManager.update(this);
  }

  @Override
  public MutableEmployeeEarnedLeaveBalance edit() {
    return new PersistentEmployeeEarnedLeaveBalance(this);
  }

  @Override
  public void delete() {
    sEmployeeEarnedLeaveBalanceManager.delete(this);
  }

  public PersistentEmployeeEarnedLeaveBalance() {}

  public PersistentEmployeeEarnedLeaveBalance(MutableEmployeeEarnedLeaveBalance pEmployeeEarnedLeaveBalance) {
    setId(pEmployeeEarnedLeaveBalance.getId());
    setEmployee(pEmployeeEarnedLeaveBalance.getEmployee());
    setEmployeeId(pEmployeeEarnedLeaveBalance.getEmployeeId());
    setFullPay(pEmployeeEarnedLeaveBalance.getFullPay());
    setHalfPay(pEmployeeEarnedLeaveBalance.getHalfPay());
    setCreatedOn(pEmployeeEarnedLeaveBalance.getCreatedOn());
    setUpdatedOn(pEmployeeEarnedLeaveBalance.getUpdatedOn());
    setLastModified(pEmployeeEarnedLeaveBalance.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sEmployeeManager = applicationContext.getBean("employeeManager", EmployeeManager.class);
    sEmployeeEarnedLeaveBalanceManager =
        applicationContext.getBean("employeeEarnedLeaveBalanceManager", EmployeeEarnedLeaveBalanceManager.class);
  }
}
