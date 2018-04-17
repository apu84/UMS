package org.ums.punishment.offence;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentOffence implements MutableOffence {

  private static OffenceManager sOffenceManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sOffenceManager = applicationContext.getBean("offenceManager", OffenceManager.class);
  }

  private Long mId;
  private String mLastModified;

  public PersistentOffence() {}

  public PersistentOffence(PersistentOffence pPersistentAuthority) {
    mId = pPersistentAuthority.getId();
    mLastModified = pPersistentAuthority.getLastModified();
  }

  @Override
  public MutableOffence edit() {
    return new PersistentOffence(this);
  }

  @Override
  public Long create() {
    return sOffenceManager.create(this);
  }

  @Override
  public void update() {
    sOffenceManager.update(this);
  }

  @Override
  public void delete() {
    sOffenceManager.delete(this);
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
