package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableFaculty;
import org.ums.manager.FacultyManager;

/**
 * Created by Monjur-E-Morshed on 06-Dec-16.
 */
public class PersistentFaculty implements MutableFaculty {

  private static FacultyManager sFacultyManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFacultyManager = applicationContext.getBean("facultyManager", FacultyManager.class);
  }

  private int mId;
  private String mLongName;
  private String mShortName;
  private String mLastModified;

  public PersistentFaculty() {}

  public PersistentFaculty(final PersistentFaculty pPersistentFaculty) {
    mId = pPersistentFaculty.getId();
    mLongName = pPersistentFaculty.getLongName();
    mShortName = pPersistentFaculty.getShortName();
    mLastModified = pPersistentFaculty.getLastModified();
  }

  @Override
  public Integer create() {
    return sFacultyManager.create(this);
  }

  @Override
  public void update() {
    sFacultyManager.update(this);
  }

  @Override
  public MutableFaculty edit() {
    return new PersistentFaculty(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
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
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void delete() {
    sFacultyManager.delete(this);
  }

  @Override
  public void setLongName(String pLongName) {
    mLongName = pLongName;
  }

  @Override
  public void setShortName(String pShortName) {
    mShortName = pShortName;
  }

  @Override
  public String getLongName() {
    return mLongName;
  }

  @Override
  public String getShortName() {
    return mShortName;
  }
}
