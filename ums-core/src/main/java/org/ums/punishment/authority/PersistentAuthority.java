package org.ums.punishment.authority;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentAuthority implements MutableAuthority {

  private static AuthorityManager sAuthorityManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAuthorityManager = applicationContext.getBean("authorityManager", AuthorityManager.class);
  }

  private Long mId;
  private String mLastModified;

  public PersistentAuthority() {}

  public PersistentAuthority(PersistentAuthority pPersistentAuthority) {
    mId = pPersistentAuthority.getId();
    mLastModified = pPersistentAuthority.getLastModified();
  }

  @Override
  public MutableAuthority edit() {
    return new PersistentAuthority(this);
  }

  @Override
  public Long create() {
    return sAuthorityManager.create(this);
  }

  @Override
  public void update() {
    sAuthorityManager.update(this);
  }

  @Override
  public void delete() {
    sAuthorityManager.delete(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
