package org.ums.usermanagement.user;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.manager.DepartmentManager;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.role.RoleManager;

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
  private boolean mActive;
  private String mPasswordResetToken;
  private Date mPasswordTokenGenerateDateTime;
  private Integer mPrimaryRoleId;
  private Role mPrimaryRole;
  private String mLastModified;
  private String mEmployeeId;
  private Department mDepartment;
  private String mDepartmentId;
  private String mName;
  private String mEmail;

  public PersistentUser() {

  }

  public PersistentUser(final PersistentUser pPersistentUser) {
    mId = pPersistentUser.getId();
    mPassword = pPersistentUser.getPassword();
    mActive = pPersistentUser.isActive();
    mPrimaryRole = pPersistentUser.getPrimaryRole();
    mDepartment = pPersistentUser.getDepartment();
    mName = pPersistentUser.getName();
    mEmployeeId = pPersistentUser.getEmployeeId();
    mTemporaryPassword = pPersistentUser.getTemporaryPassword();
    mPasswordResetToken = pPersistentUser.getPasswordResetToken();
    mPasswordTokenGenerateDateTime = pPersistentUser.getPasswordTokenGenerateDateTime();
    mLastModified = pPersistentUser.getLastModified();
    mEmail = pPersistentUser.getEmail();
  }

  @Override
  public String create() {
    return sUserManager.create(this);
  }

  @Override
  public void update() {
    sUserManager.update(this);
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

  @Override
  public String getEmail() {
    return mEmail;
  }

  @Override
  public void setEmail(String pEmail) {
    mEmail = pEmail;
  }
}
