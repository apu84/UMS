package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.manager.EmployeeManager;
import org.ums.manager.common.LmsAppStatusManager;
import org.ums.manager.common.LmsApplicationManager;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 07-May-17.
 */
public class PersistentLmsAppStatus implements MutableLmsAppStatus {

  private static LmsApplicationManager sLmsApplicationManager;
  private static EmployeeManager sEmployeeManager;
  private static LmsAppStatusManager sLmsAppStatusManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sLmsApplicationManager = applicationContext.getBean("lmsApplicationManager", LmsApplicationManager.class);
    sEmployeeManager = applicationContext.getBean("employeeManager", EmployeeManager.class);
    sLmsAppStatusManager = applicationContext.getBean("lmsAppStatusManager", LmsAppStatusManager.class);
  }

  private Long mId;
  private Long mLmsApplicationId;
  private LmsApplication mLmsApplication;
  private Date mActionTakenOn;
  private String mActionTakenById;
  private Employee mActionTakenBy;
  private String mComments;
  private LeaveApplicationApprovalStatus mActionStatus;
  private String mLastModified;
  private int mRowNumber;

  public PersistentLmsAppStatus() {

  }

  public PersistentLmsAppStatus(final PersistentLmsAppStatus pPersistentLmsAppStatus) {
    mId = pPersistentLmsAppStatus.getId();
    mLmsApplicationId = pPersistentLmsAppStatus.getLmsAppId();
    mLmsApplication = pPersistentLmsAppStatus.getLmsApplication();
    mActionTakenOn = pPersistentLmsAppStatus.getActionTakenOn();
    mActionTakenById = pPersistentLmsAppStatus.getActionTakenById();
    mActionTakenBy = pPersistentLmsAppStatus.getActionTakenBy();
    mComments = pPersistentLmsAppStatus.getComments();
    mActionStatus = pPersistentLmsAppStatus.getActionStatus();
    mRowNumber = pPersistentLmsAppStatus.getRowNumber();
    mLastModified = pPersistentLmsAppStatus.getLastModified();
  }

  @Override
  public void setRowNumber(int pRowNumber) {
    mRowNumber = pRowNumber;
  }

  @Override
  public int getRowNumber() {
    return mRowNumber;
  }

  @Override
  public Long create() {
    return sLmsAppStatusManager.create(this);
  }

  @Override
  public MutableLmsAppStatus edit() {
    return new PersistentLmsAppStatus(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void update() {
    sLmsAppStatusManager.update(this);
  }

  @Override
  public void delete() {
    sLmsAppStatusManager.delete(this);
  }

  @Override
  public void setLmsApplicationId(Long pLmsApplicationId) {
    mLmsApplicationId = pLmsApplicationId;
  }

  @Override
  public Long getLmsAppId() {
    return mLmsApplicationId;
  }

  @Override
  public void setActionTakenOn(Date pActionTakenOn) {
    mActionTakenOn = pActionTakenOn;
  }

  @Override
  public LmsApplication getLmsApplication() {
    return mLmsApplication == null ? sLmsApplicationManager.get(mLmsApplicationId) : sLmsApplicationManager
        .validate(mLmsApplication);
  }

  @Override
  public Date getActionTakenOn() {
    return mActionTakenOn;
  }

  @Override
  public void setActionTakenById(String pActionTakenBy) {
    mActionTakenById = pActionTakenBy;
  }

  @Override
  public String getActionTakenById() {
    return mActionTakenById;
  }

  @Override
  public void setComments(String pComments) {
    mComments = pComments;
  }

  @Override
  public Employee getActionTakenBy() {
    return mActionTakenBy == null ? sEmployeeManager.get(mActionTakenById) : sEmployeeManager.validate(mActionTakenBy);
  }

  @Override
  public String getComments() {
    return mComments;
  }

  @Override
  public void setActionStatus(LeaveApplicationApprovalStatus pActionStatus) {
    mActionStatus = pActionStatus;
  }

  @Override
  public LeaveApplicationApprovalStatus getActionStatus() {
    return mActionStatus;
  }
}
