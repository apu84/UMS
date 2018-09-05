package org.ums.usermanagement.role;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.usermanagement.permission.Permission;
import org.ums.usermanagement.permission.PermissionManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersistentRole implements MutableRole {
  private static RoleManager sRoleManager;
  private static PermissionManager sPermissionManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sRoleManager = applicationContext.getBean("roleManager", RoleManager.class);
    sPermissionManager = applicationContext.getBean("permissionManager", PermissionManager.class);
  }

  private Integer mId;
  private String mName;
  private String mLabel;
  private Set<String> mPermissions;
  private String mLastModified;

  public PersistentRole() {

  }

  public PersistentRole(final MutableRole pRole) {
    mId = pRole.getId();
    mName = pRole.getName();
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public String getLabel() {
    return mLabel;
  }

  @Override
  public void setName(String pName) {
    mName = pName;
  }

  @Override
  public void setLabel(String pLabel) {
    mLabel = pLabel;
  }

  @Override
  public MutableRole edit() {
    return new PersistentRole(this);
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
  public void setPermissions(Set<String> pPermissions) {
    mPermissions = pPermissions;
  }

  @Override
  public Set<String> getPermissions() {
    if(mPermissions == null) {
      List<Permission> rolePermissions = sPermissionManager.getPermissionByRole(this);
      Set<String> permissions = new HashSet<>();
      for(Permission permission : rolePermissions) {
        permissions.addAll(permission.getPermissions());
      }
      return permissions;
    }
    return mPermissions;
  }

  @Override
  public Integer create() {
    return sRoleManager.create(this);
  }

  @Override
  public void update() {
    sRoleManager.update(this);
  }

  @Override
  public void delete() {
    sRoleManager.delete(this);
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
