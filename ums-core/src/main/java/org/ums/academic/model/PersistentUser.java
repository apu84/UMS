package org.ums.academic.model;


import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableRole;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.regular.Role;
import org.ums.domain.model.regular.User;
import org.ums.manager.ContentManager;

public class PersistentUser implements MutableUser {
  private static ContentManager<User, MutableUser, String> sUserManager;
  private static ContentManager<Role, MutableRole, Integer> sRoleManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sUserManager = (ContentManager<User, MutableUser, String>) applicationContext.getBean("userManager");
    sRoleManager = (ContentManager<Role, MutableRole, Integer>) applicationContext.getBean("roleManager");
  }

  private String mId;
  private char[] mPassword;
  private char[] mTemporaryPassword;
  private Role mRole;
  private Integer mRoleId;
  private boolean mActive;

  public PersistentUser() {

  }

  public PersistentUser(final PersistentUser pPersistentUser) throws Exception {
    mId = pPersistentUser.getId();
    mPassword = pPersistentUser.getPassword();
    mTemporaryPassword = pPersistentUser.getTemporaryPassword();
    mRoleId = pPersistentUser.getRoleId();
    mRole = pPersistentUser.getRole();
    mActive = pPersistentUser.isActive();
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
  public Integer getRoleId() {
    return mRoleId;
  }

  @Override
  public void setRoleId(Integer pRoleId) {
    mRoleId = pRoleId;
  }

  @Override
  public Role getRole() throws Exception {
    return mRole == null ? sRoleManager.get(mRoleId) : sRoleManager.validate(mRole);
  }

  @Override
  public void setRole(Role pRole) {
    mRole = pRole;
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
}
