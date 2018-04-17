package org.ums.usermanagement.application;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

public class PersistentApplication implements MutableApplication {
  private static ApplicationManager sApplicationManager;
  private Long mId;
  private String mName;
  private String mDescription;
  private String mLastModified;

  public PersistentApplication() {}

  public PersistentApplication(final Application pApplication) {
    setId(pApplication.getId());
    setName(pApplication.getName());
    setDescription(pApplication.getDescription());
    setLastModified(pApplication.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sApplicationManager = applicationContext.getBean("applicationManager", ApplicationManager.class);
  }

  @Override
  public MutableApplication edit() {
    return new PersistentApplication(this);
  }

  @Override
  public Long create() {
    return sApplicationManager.create(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void update() {
    sApplicationManager.update(this);
  }

  @Override
  public void delete() {
    sApplicationManager.delete(this);
  }

  @Override
  public void setName(String pName) {
    mName = pName;
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public String getDescription() {
    return mDescription;
  }

  @Override
  public void setDescription(String pDescription) {
    mDescription = pDescription;
  }
}
