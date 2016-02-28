package org.ums.academic.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableAdditionalRolePermission;
import org.ums.domain.model.mutable.MutableRole;
import org.ums.domain.model.readOnly.Role;
import org.ums.domain.model.readOnly.User;
import org.ums.manager.AdditionalRolePermissionsManager;
import org.ums.manager.ContentManager;
import org.ums.manager.UserManager;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PersistentAdditionalRolePermissions implements MutableAdditionalRolePermission {
  private static UserManager sUserManager;
  private static ContentManager<Role, MutableRole, Integer> sRoleManager;
  private static AdditionalRolePermissionsManager sAdditionalRolePermissionsManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sUserManager = applicationContext.getBean("userManager", UserManager.class);
    sRoleManager = applicationContext.getBean("roleManager", ContentManager.class);
    sAdditionalRolePermissionsManager = applicationContext.getBean("additionalRolePermissionsManager", AdditionalRolePermissionsManager.class);
  }

  private Integer mId;
  private User mUser;
  private Role mRole;
  private Set<String> mPermission;
  private Date mValidFrom;
  private Date mValidTo;
  private boolean mActive;
  private String mLastModified;

  private String mUserId;
  private Integer mRoleId;

  public PersistentAdditionalRolePermissions() {
  }

  public PersistentAdditionalRolePermissions(final PersistentAdditionalRolePermissions pAdditional) throws Exception {
    setId(pAdditional.getId());
    setUser(pAdditional.getUser());
    setUserId(pAdditional.getUserId());
    setRoleId(pAdditional.getRoleId());
    setRole(pAdditional.getRole());
    setPermission(pAdditional.getPermission());
    setValidFrom(pAdditional.getValidFrom());
    setValidTo(pAdditional.getValidTo());
    setActive(pAdditional.isActive());
    setLastModified(pAdditional.getLastModified());
  }

  @Override
  public void setUser(User pUser) {
    mUser = pUser;
  }

  @Override
  public void setRole(Role pRole) {
    mRole = pRole;
  }

  @Override
  public void setPermission(Set<String> pPermission) {
    mPermission = pPermission;
  }

  @Override
  public void setRoleId(Integer pRoleId) {
    mRoleId = pRoleId;
  }

  @Override
  public void setValidFrom(Date pFromDate) {
    mValidFrom = pFromDate;
  }

  @Override
  public void setValidTo(Date pToDate) {
    mValidTo = pToDate;
  }

  @Override
  public void setActive(boolean pActive) {
    mActive = pActive;
  }

  @Override
  public User getUser() throws Exception {
    return mUser == null ? sUserManager.get(mUserId) : sUserManager.validate(mUser);
  }

  @Override
  public Role getRole() throws Exception {
    return mRole == null ? sRoleManager.get(mRoleId) : sRoleManager.validate(mRole);
  }

  @Override
  public Integer getRoleId() {
    return mRoleId;
  }

  @Override
  public Set<String> getPermission() throws Exception {
    Role role = getRole();
    if (role.getPermissions() != null) {
      if (mPermission == null) {
        mPermission = new HashSet<>();
      }
      mPermission.addAll(role.getPermissions());
    }
    return mPermission;
  }

  @Override
  public Date getValidFrom() {
    return mValidFrom;
  }

  @Override
  public Date getValidTo() {
    return mValidTo;
  }

  @Override
  public boolean isActive() {
    return mActive;
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
  public void setUserId(String pUserId) {
    mUserId = pUserId;
  }

  @Override
  public String getUserId() {
    return mUserId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void commit(boolean update) throws Exception {
    if (update) {
      sAdditionalRolePermissionsManager.update(this);
    } else {
      sAdditionalRolePermissionsManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sAdditionalRolePermissionsManager.delete(this);
  }

  @Override
  public MutableAdditionalRolePermission edit() throws Exception {
    return new PersistentAdditionalRolePermissions(this);
  }

}
