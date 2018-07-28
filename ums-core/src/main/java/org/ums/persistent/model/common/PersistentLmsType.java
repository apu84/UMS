package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.common.MutableLmsType;
import org.ums.enums.common.*;
import org.ums.manager.common.LmsTypeManager;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.role.RoleManager;

import java.beans.Visibility;

/**
 * Created by Monjur-E-Morshed on 03-May-17.
 */
public class PersistentLmsType implements MutableLmsType {

  private static LmsTypeManager sLmsTypeManager;
  private static RoleManager sRoleManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sLmsTypeManager = applicationContext.getBean("lmsTypeManager", LmsTypeManager.class);
    sRoleManager = applicationContext.getBean("roleManager", RoleManager.class);
  }

  private int mId;
  private String mName;
  private EmployeeLeaveType mEmployeeLeaveType;
  private int mDurationDays;
  private int mMaxDuration;
  private int mMaxSimultaneousDuration;
  private DurationType mDurationType;
  private SalaryType mSalaryType;
  private String mLastModified;
  private int mAuthorizeRoleId;
  private Role mAuthorizeRole;
  private int mSpecialAuthorizeRoleId;
  private Role mSpecialAuthorizeRole;
  private LeaveTypeCategory mLeaveTypeCategory;
  private int mViewOrder;
  private VisibilityType mVisibilityType;

  public PersistentLmsType() {

  }

  public PersistentLmsType(final PersistentLmsType pPersistentLmsType) {
    mId = pPersistentLmsType.getId();
    mName = pPersistentLmsType.getName();
    mEmployeeLeaveType = pPersistentLmsType.getEmployeeLeaveType();
    mDurationDays = pPersistentLmsType.getDuration();
    mLastModified = pPersistentLmsType.getLastModified();
    mMaxDuration = pPersistentLmsType.getMaxDuration();
    mMaxSimultaneousDuration = pPersistentLmsType.getMaxSimultaneousDuration();
    mDurationType = pPersistentLmsType.getDurationType();
    mSalaryType = pPersistentLmsType.getSalaryType();
    mAuthorizeRoleId = pPersistentLmsType.getAuthorizeRoleId();
    mAuthorizeRole = pPersistentLmsType.getAuthorizeRole();
    mSpecialAuthorizeRoleId = pPersistentLmsType.getSpecialAuthorizeRoleId();
    mSpecialAuthorizeRole = pPersistentLmsType.getSpecialAuthorizeRole();
  }

  @Override
  public LeaveTypeCategory getLeaveTypeCategory() {
    return mLeaveTypeCategory;
  }

  @Override
  public void setLeaveTypeCategory(LeaveTypeCategory pLeaveTypeCategory) {
    mLeaveTypeCategory = pLeaveTypeCategory;
  }

  @Override
  public int getViewOrder() {
    return mViewOrder;
  }

  @Override
  public void setViewOrder(int pViewOrder) {
    mViewOrder = pViewOrder;
  }

  @Override
  public VisibilityType getVisibilityType() {
    return mVisibilityType;
  }

  @Override
  public void setVisibilityType(VisibilityType pVisibilityType) {
    mVisibilityType = pVisibilityType;
  }

  @Override
  public int getAuthorizeRoleId() {
    return mAuthorizeRoleId;
  }

  @Override
  public Role getAuthorizeRole() {
    return mAuthorizeRole == null ? sRoleManager.get(mAuthorizeRoleId) : sRoleManager.validate(mAuthorizeRole);
  }

  @Override
  public void setAuthorizeRoleId(int pAuthorizeRoleId) {
    mAuthorizeRoleId = pAuthorizeRoleId;
  }

  @Override
  public int getSpecialAuthorizeRoleId() {
    return mSpecialAuthorizeRoleId;
  }

  @Override
  public void setSpecialAuthorizeRoleId(int pSpecialAuthorizeRoleId) {
    mSpecialAuthorizeRoleId = pSpecialAuthorizeRoleId;
  }

  @Override
  public Role getSpecialAuthorizeRole() {
    return mSpecialAuthorizeRole == null ? sRoleManager.get(mSpecialAuthorizeRoleId) : sRoleManager
        .validate(mSpecialAuthorizeRole);
  }

  @Override
  public int getMaxDuration() {
    return mMaxDuration;
  }

  @Override
  public void setMaxDuration(int pMaxDuration) {
    mMaxDuration = pMaxDuration;
  }

  @Override
  public int getMaxSimultaneousDuration() {
    return mMaxSimultaneousDuration;
  }

  @Override
  public void setMaxSimultaneousDuration(int pMaxSimultaneousDuration) {
    mMaxSimultaneousDuration = pMaxSimultaneousDuration;
  }

  @Override
  public DurationType getDurationType() {
    return mDurationType;
  }

  @Override
  public SalaryType getSalaryType() {
    return mSalaryType;
  }

  @Override
  public void setDurationType(DurationType pDurationType) {
    mDurationType = pDurationType;
  }

  @Override
  public void setSalaryType(SalaryType pSalaryType) {
    mSalaryType = pSalaryType;
  }

  @Override
  public Integer create() {
    return sLmsTypeManager.create(this);
  }

  @Override
  public MutableLmsType edit() {
    return new PersistentLmsType(this);
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
    sLmsTypeManager.update(this);
  }

  @Override
  public void delete() {
    sLmsTypeManager.delete(this);
  }

  @Override
  public void setName(String pName) {
    mName = pName;
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public void setType(EmployeeLeaveType pType) {
    mEmployeeLeaveType = pType;
  }

  @Override
  public EmployeeLeaveType getEmployeeLeaveType() {
    return mEmployeeLeaveType;
  }

  @Override
  public int getDuration() {
    return mDurationDays;
  }

  @Override
  public void setDuration(int pDuration) {
    mDurationDays = pDuration;
  }
}
