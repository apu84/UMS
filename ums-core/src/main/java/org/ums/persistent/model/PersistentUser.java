package org.ums.persistent.model;


import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.immutable.Role;
import org.ums.manager.RoleManager;
import org.ums.manager.UserManager;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;

public class PersistentUser implements MutableUser {
  private static UserManager sUserManager;
  private static RoleManager sRoleManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sUserManager = applicationContext.getBean("userManager", UserManager.class);
    sRoleManager = applicationContext.getBean("roleManager", RoleManager.class);
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

  public PersistentUser() {

  }

  public PersistentUser(final PersistentUser pPersistentUser) throws Exception {
    mId = pPersistentUser.getId();
    mPassword = pPersistentUser.getPassword();
    mTemporaryPassword = pPersistentUser.getTemporaryPassword();
    mRoleIds = pPersistentUser.getRoleIds();
    mRoles = pPersistentUser.getRoles();
    mActive = pPersistentUser.isActive();
    mPrimaryRole = pPersistentUser.getPrimaryRole();
    mAdditionalPermissions = pPersistentUser.getAdditionalPermissions();
  }

  @Override
  public void commit(boolean update) throws Exception {
    if (update) {
      sUserManager.update(this);
    } else {
      sUserManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
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
  public List<Role> getRoles() throws Exception {
    mRoles = new ArrayList<>();
    for (Integer roleId : mRoleIds) {
      mRoles.add(sRoleManager.get(roleId));
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
  public MutableUser edit() throws Exception {
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
    mPasswordResetToken=pPasswordResetToken;
  }

  @Override
  public String getPasswordResetToken() {
    return mPasswordResetToken;
  }

  @Override
  public void setPasswordTokenGenerateDateTime(Date pDateTime) {
    mPasswordTokenGenerateDateTime=pDateTime;
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
  public Role getPrimaryRole() throws Exception {
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
}
