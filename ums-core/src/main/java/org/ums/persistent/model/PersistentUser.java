package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.manager.DepartmentManager;
import org.ums.manager.RoleManager;
import org.ums.manager.UserManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersistentUser implements MutableUser {
  private static UserManager sUserManager;
  private static RoleManager sRoleManager;
  private static DepartmentManager sDepartmentManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sUserManager = applicationContext.getBean("userManager", UserManager.class);
    sRoleManager = applicationContext.getBean("roleManager", RoleManager.class);
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
  }

  private String mId;
  private char[] mPassword;
  private char[] mTemporaryPassword;
  private List<Role> mRoles;
  private List<Integer> mRoleIds;
  private boolean mActive;
  private String mPasswordResetToken;
  private Date mPasswordTokenGenerateDateTime;
  private Integer mPrimaryRoleId;
  private Role mPrimaryRole;
  private List<String> mAdditionalPermissions;
  private String mLastModified;
  private String mEmployeeId;
  private Department mDepartment;
  private String mDepartmentId;
  private String mName;

  public PersistentUser() {

  }

  public PersistentUser(final PersistentUser pPersistentUser) {
    mId = pPersistentUser.getId();
    mPassword = pPersistentUser.getPassword();
    mRoleIds = pPersistentUser.getRoleIds();
    mRoles = pPersistentUser.getRoles();
    mActive = pPersistentUser.isActive();
    mPrimaryRole = pPersistentUser.getPrimaryRole();
    mAdditionalPermissions = pPersistentUser.getAdditionalPermissions();
    mDepartment = pPersistentUser.getDepartment();
    mName = pPersistentUser.getName();
    mEmployeeId = pPersistentUser.getEmployeeId();

    mTemporaryPassword = pPersistentUser.getTemporaryPassword();
    mPasswordResetToken = pPersistentUser.getPasswordResetToken();
    mPasswordTokenGenerateDateTime = pPersistentUser.getPasswordTokenGenerateDateTime();
    mLastModified = pPersistentUser.getLastModified();

  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sUserManager.update(this);
    }
    else {
      sUserManager.create(this);
    }
  }

  @Override
  public void delete() {
    sUserManager.delete(this);
  }

  @Override
  public char[] getPassword() {
    return mPassword;
  }

  @Override
  public void setPassword(char[] pPassword) {
    mPassword = pPassword;
  }

  @Override
  public List<Integer> getRoleIds() {
    return mRoleIds;
  }

  @Override
  public void setRoleIds(List<Integer> pRoleIds) {
    mRoleIds = pRoleIds;
  }

  @Override
  public List<Role> getRoles() {
    mRoles = new ArrayList<>();
    if(mRoleIds != null) {
      for(Integer roleId : mRoleIds) {
        mRoles.add(sRoleManager.get(roleId));
      }
    }
    return mRoles;
  }

  @Override
  public void setRoles(List<Role> pRoles) {
    mRoles = pRoles;
  }

  @Override
  public boolean isActive() {
    return mActive;
  }

  @Override
  public void setActive(boolean pActive) {
    mActive = pActive;
  }

  @Override
  public MutableUser edit() {
    return new PersistentUser(this);
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void setId(String pId) {
    mId = pId;
  }

  @Override
  public char[] getTemporaryPassword() {
    return mTemporaryPassword;
  }

  @Override
  public void setTemporaryPassword(char[] pPassword) {
    mTemporaryPassword = pPassword;
  }

  @Override
  public void setPasswordResetToken(String pPasswordResetToken) {
    mPasswordResetToken = pPasswordResetToken;
  }

  @Override
  public String getPasswordResetToken() {
    return mPasswordResetToken;
  }

  @Override
  public void setPasswordTokenGenerateDateTime(Date pDateTime) {
    mPasswordTokenGenerateDateTime = pDateTime;
  }

  @Override
  public Date getPasswordTokenGenerateDateTime() {
    return mPasswordTokenGenerateDateTime;
  }

  @Override
  public void setPrimaryRoleId(Integer pRoleId) {
    mPrimaryRoleId = pRoleId;
  }

  @Override
  public void setPrimaryRole(Role pPrimaryRole) {
    mPrimaryRole = pPrimaryRole;
  }

  @Override
  public Integer getPrimaryRoleId() {
    return mPrimaryRoleId;
  }

  @Override
  public Role getPrimaryRole() {
    return mPrimaryRole == null ? sRoleManager.get(mPrimaryRoleId) : sRoleManager.validate(mPrimaryRole);
  }

  @Override
  public void setAdditionalPermissions(List<String> pAdditionalPermissions) {
    mAdditionalPermissions = pAdditionalPermissions;
  }

  @Override
  public List<String> getAdditionalPermissions() {
    return mAdditionalPermissions;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public String getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public void setEmployeeId(String pEmployeeId) {
    mEmployeeId = pEmployeeId;
  }

  @Override
  public void setDepartment(Department pDepartment) {
    mDepartment = pDepartment;
  }

  @Override
  public void setDepartmentId(String pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  @Override
  public Department getDepartment() {
    return mDepartment == null ? (StringUtils.isEmpty(mDepartmentId) ? null : sDepartmentManager.get(mDepartmentId))
        : sDepartmentManager.validate(mDepartment);
  }

  @Override
  public String getDepartmentId() {
    return mDepartmentId;
  }

  @Override
  public void setName(String pName) {
    mName = pName;
  }

  @Override
  public String getName() {
    return mName;
  }
}
