package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.common.LmsType;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.enums.common.LeaveApplicationStatus;
import org.ums.manager.EmployeeManager;
import org.ums.manager.common.LmsApplicationManager;
import org.ums.manager.common.LmsTypeManager;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public class PersistentLmsApplication implements MutableLmsApplication {

  private static LmsTypeManager sLmsTypeManager;
  private static EmployeeManager sEmployeeManager;
  private static LmsApplicationManager sLmsApplicationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sLmsTypeManager = applicationContext.getBean("lmsTypeManager", LmsTypeManager.class);
    sEmployeeManager = applicationContext.getBean("employeeManager", EmployeeManager.class);
    sLmsApplicationManager = applicationContext.getBean("lmsApplicationManager", LmsApplicationManager.class);
  }

  private int mId;
  private String mEmployeeId;
  private Employee mEmployee;
  private int mLeaveTypeId;
  private LmsType mLeaveType;
  private Date mAppliedOn;
  private Date mFromDate;
  private Date mToDate;
  private String mReason;
  private LeaveApplicationStatus mLeaveApplicationStatus;
  private String mLastModified;

  public PersistentLmsApplication() {

  }

  public PersistentLmsApplication(final PersistentLmsApplication pPersistentLmsApplication) {
    mId = pPersistentLmsApplication.getId();
    mEmployeeId = pPersistentLmsApplication.getEmployeeId();
    mEmployee = pPersistentLmsApplication.getEmployee();
    mLeaveTypeId = pPersistentLmsApplication.getLeaveTypeId();
    mLeaveType = pPersistentLmsApplication.getLmsType();
    mAppliedOn = pPersistentLmsApplication.getAppliedOn();
    mFromDate = pPersistentLmsApplication.getFromDate();
    mToDate = pPersistentLmsApplication.getToDate();
    mReason = pPersistentLmsApplication.getReason();
    mLastModified = pPersistentLmsApplication.getLastModified();
    mLeaveApplicationStatus = pPersistentLmsApplication.getApplicationStatus();
  }

  @Override
  public void setLeaveApplicationStatus(LeaveApplicationStatus pLeaveApplicationStatus) {
    mLeaveApplicationStatus = pLeaveApplicationStatus;
  }

  @Override
  public LeaveApplicationStatus getApplicationStatus() {
    return mLeaveApplicationStatus;
  }

  @Override
  public Integer create() {
    return sLmsApplicationManager.create(this);
  }

  @Override
  public MutableLmsApplication edit() {
    return new PersistentLmsApplication(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void update() {
    sLmsApplicationManager.update(this);
  }

  @Override
  public void delete() {
    sLmsApplicationManager.delete(this);
  }

  @Override
  public void setEmployeeId(String pEmployeeId) {
    mEmployeeId = pEmployeeId;
  }

  @Override
  public void setLeaveTypeId(int pTypeId) {
    mLeaveTypeId = pTypeId;
  }

  @Override
  public String getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public void setAppliedOn(Date pAppliedOn) {
    mAppliedOn = pAppliedOn;
  }

  @Override
  public Employee getEmployee() {
    return mEmployee == null ? sEmployeeManager.get(mEmployeeId) : sEmployeeManager.validate(mEmployee);
  }

  @Override
  public int getLeaveTypeId() {
    return mLeaveTypeId;
  }

  @Override
  public void setFromDate(Date pFromDate) {
    mFromDate = pFromDate;
  }

  @Override
  public LmsType getLmsType() {
    return mLeaveType == null ? sLmsTypeManager.get(mLeaveTypeId) : sLmsTypeManager.validate(mLeaveType);
  }

  @Override
  public void setToDate(Date pToDate) {
    mToDate = pToDate;
  }

  @Override
  public Date getAppliedOn() {
    return mAppliedOn;
  }

  @Override
  public void setReason(String pReason) {
    mReason = pReason;
  }

  @Override
  public Date getFromDate() {
    return mFromDate;
  }

  @Override
  public Date getToDate() {
    return mToDate;
  }

  @Override
  public String getReason() {
    return mReason;
  }
}
