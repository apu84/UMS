package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutablePermission;
import org.ums.domain.model.immutable.Role;
import org.ums.manager.PermissionManager;
import org.ums.manager.RoleManager;

import java.util.HashSet;
import java.util.Set;

public class PersistentPermission implements MutablePermission {
  private static RoleManager sRoleManager;
  private static PermissionManager sPermissionManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sRoleManager = applicationContext.getBean("roleManager", RoleManager.class);
    sPermissionManager = (PermissionManager) applicationContext.getBean("permissionManager");
  }

  private Integer mId;
  private Role mRole;
  private Set<String> mPermissions;
  private String mLastModified;

  private Integer mRoleId;

  public PersistentPermission() {}

  public PersistentPermission(final PersistentPermission pPersistentPermission) {
    setId(pPersistentPermission.getId());
    setRoleId(pPersistentPermission.getRoleId());
    setRole(pPersistentPermission.getRole());
    setPermissions(pPersistentPermission.getPermissions());
  }

  @Override
  public void setRole(Role pRole) {
    mRole = pRole;
  }

  @Override
  public void setRoleId(Integer pRoleId) {
    mRoleId = pRoleId;
  }

  @Override
  public void setPermissions(Set<String> pPermissions) {
    mPermissions = pPermissions;
  }

  @Override
  public void addPermission(String pPermission) {
    if(mPermissions == null) {
      mPermissions = new HashSet<>();
    }
    mPermissions.add(pPermission);
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sPermissionManager.update(this);
    }
    else {
      sPermissionManager.create(this);
    }
  }

  @Override
  public void delete() {
    sPermissionManager.delete(this);
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public Role getRole() {
    return mRole == null ? sRoleManager.get(mRoleId) : sRoleManager.validate(mRole);
  }

  @Override
  public Integer getRoleId() {
    return mRoleId;
  }

  @Override
  public Set<String> getPermissions() {
    return mPermissions;
  }

  @Override
  public MutablePermission edit() {
    return new PersistentPermission(this);
  }

  @Override
  public Integer getId() {
    return mId;
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
