package org.ums.punishment.penalty;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentPenalty implements MutablePenalty {

  private static PenaltyManager sPenaltyManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sPenaltyManager = applicationContext.getBean("penaltyManager", PenaltyManager.class);
  }

  private Long mId;
  private String mLastModified;

  public PersistentPenalty() {}

  public PersistentPenalty(PersistentPenalty pPersistentAuthority) {
    mId = pPersistentAuthority.getId();
    mLastModified = pPersistentAuthority.getLastModified();
  }

  @Override
  public MutablePenalty edit() {
    return new PersistentPenalty(this);
  }

  @Override
  public Long create() {
    return sPenaltyManager.create(this);
  }

  @Override
  public void update() {
    sPenaltyManager.update(this);
  }

  @Override
  public void delete() {
    sPenaltyManager.delete(this);
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
