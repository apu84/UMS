package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableAdditionalRolePermissions;
import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.immutable.User;
import org.ums.manager.AdditionalRolePermissionsManager;
import org.ums.manager.RoleManager;
import org.ums.manager.UserManager;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PersistentAdditionalRolePermissions implements MutableAdditionalRolePermissions {
  private static UserManager sUserManager;
  private static RoleManager sRoleManager;
  private static AdditionalRolePermissionsManager sAdditionalRolePermissionsManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sUserManager = applicationContext.getBean("userManager", UserManager.class);
    sRoleManager = applicationContext.getBean("roleManager", RoleManager.class);
    sAdditionalRolePermissionsManager =
        applicationContext.getBean("additionalRolePermissionsManager",
            AdditionalRolePermissionsManager.class);
  }

  private Long mId;
  private User mUser;
  private Role mRole;
  private Set<String> mPermission;
  private Date mValidFrom;
  private Date mValidTo;
  private boolean mActive;
  private String mLastModified;
  private User mAssignedBy;

  private String mUserId;
  private Integer mRoleId;
  private String mAssignedByUserId;

  public PersistentAdditionalRolePermissions() {}

  public PersistentAdditionalRolePermissions(final PersistentAdditionalRolePermissions pAdditional) {
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
  public User getUser() {
    return mUser == null ? sUserManager.get(mUserId) : sUserManager.validate(mUser);
  }

  @Override
  public Role getRole() {
    return mRole == null ? (StringUtils.isEmpty(mRoleId) ? null : sRoleManager.get(mRoleId))
        : sRoleManager.validate(mRole);
  }

  @Override
  public Integer getRoleId() {
    return mRoleId;
  }

  @Override
  public Set<String> getPermission() {
    Role role = getRole();
    if(role != null && role.getPermissions() != null) {
      if(mPermission == null) {
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
  public void setUserId(String pUserId) {
    mUserId = pUserId;
  }

  @Override
  public String getUserId() {
    return mUserId;
  }

  @Override
  public void setAssignedBy(User pUser) {
    mAssignedBy = pUser;
  }

  @Override
  public User getAssignedBy() {
    return mAssignedBy == null ? sUserManager.get(mAssignedByUserId) : sUserManager
        .validate(mAssignedBy);
  }

  @Override
  public String getAssignedByUserId() {
    return mAssignedByUserId;
  }

  public void setAssignedByUserId(String pAssignedByUserId) {
    mAssignedByUserId = pAssignedByUserId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Long create() {
    return sAdditionalRolePermissionsManager.create(this);
  }

  @Override
  public void update() {
    sAdditionalRolePermissionsManager.update(this);
  }

  @Override
  public void delete() {
    sAdditionalRolePermissionsManager.delete(this);
  }

  @Override
  public MutableAdditionalRolePermissions edit() {
    return new PersistentAdditionalRolePermissions(this);
  }

}
