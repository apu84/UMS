package org.ums.punishment;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentPunishment implements MutablePunishment {

  private static PunishmentManager sPunishmentManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sPunishmentManager = applicationContext.getBean("punishmentManager", PunishmentManager.class);
  }

  private Long mId;
  private String mLastModified;

  public PersistentPunishment() {}

  public PersistentPunishment(PersistentPunishment pPersistentAuthority) {
    mId = pPersistentAuthority.getId();
    mLastModified = pPersistentAuthority.getLastModified();
  }

  @Override
  public MutablePunishment edit() {
    return new PersistentPunishment(this);
  }

  @Override
  public Long create() {
    return sPunishmentManager.create(this);
  }

  @Override
  public void update() {
    sPunishmentManager.update(this);
  }

  @Override
  public void delete() {
    sPunishmentManager.delete(this);
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
