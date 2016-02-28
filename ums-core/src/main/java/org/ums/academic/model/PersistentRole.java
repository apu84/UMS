package org.ums.academic.model;


import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableRole;
import org.ums.domain.model.readOnly.Role;
import org.ums.manager.ContentManager;

import java.util.List;
import java.util.Set;

public class PersistentRole implements MutableRole {
  private static ContentManager<Role, MutableRole, Integer> sRoleManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sRoleManager = (ContentManager<Role, MutableRole, Integer>) applicationContext.getBean("roleManager");
  }

  private Integer mId;
  private String mName;
  private Set<String> mPermissions;

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
  public MutableRole edit() throws Exception {
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
  public void commit(boolean update) throws Exception {
    if(update) {
      sRoleManager.update(this);
    }else {
      sRoleManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sRoleManager.delete(this);
  }
}
