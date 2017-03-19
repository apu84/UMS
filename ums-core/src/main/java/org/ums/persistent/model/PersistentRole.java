package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableRole;
import org.ums.manager.RoleManager;

import java.util.Set;

public class PersistentRole implements MutableRole {
  private static RoleManager sRoleManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sRoleManager = applicationContext.getBean("roleManager", RoleManager.class);
  }

  private Integer mId;
  private String mName;
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
  public void setName(String pName) {
    mName = pName;
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
