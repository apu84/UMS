package org.ums.lock;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentLock implements MutableLock {
  private static LockManager sLockManager;
  private String mId;
  private String mLastModified;

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void setId(String pId) {
    this.mId = pId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public String create() {
    return sLockManager.create(this);
  }

  @Override
  public void update() {
    sLockManager.update(this);
  }

  @Override
  public MutableLock edit() {
    return new PersistentLock(this);
  }

  @Override
  public void delete() {
    sLockManager.delete(this);
  }

  public PersistentLock() {}

  public PersistentLock(MutableLock pLock) {
    setId(pLock.getId());
    setLastModified(pLock.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sLockManager = applicationContext.getBean("lockManager", LockManager.class);
  }
}
