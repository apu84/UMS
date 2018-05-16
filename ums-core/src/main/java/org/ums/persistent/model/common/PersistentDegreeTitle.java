package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.common.MutableDegreeTitle;
import org.ums.enums.common.DegreeLevel;
import org.ums.manager.common.DegreeTitleManager;

public class PersistentDegreeTitle implements MutableDegreeTitle {

  private static DegreeTitleManager sDegreeTitleManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDegreeTitleManager = applicationContext.getBean("degreeTitleManager", DegreeTitleManager.class);
  }

  private Integer mId;
  private String mTitle;
  private Integer mDegreeLevelId;
  private DegreeLevel mDegreeLevel;
  private String mLastModified;

  public PersistentDegreeTitle() {}

  public PersistentDegreeTitle(PersistentDegreeTitle pPersistentDegreeTitle) {
    mId = pPersistentDegreeTitle.getId();
    mTitle = pPersistentDegreeTitle.getTitle();
    mDegreeLevel = pPersistentDegreeTitle.getDegreeLevel();
    mDegreeLevelId = pPersistentDegreeTitle.getDegreeLevelId();
    mLastModified = pPersistentDegreeTitle.getLastModified();
  }

  @Override
  public MutableDegreeTitle edit() {
    return new PersistentDegreeTitle(this);
  }

  @Override
  public Integer create() {
    return sDegreeTitleManager.create(this);
  }

  @Override
  public void update() {
    sDegreeTitleManager.update(this);
  }

  @Override
  public void delete() {
    sDegreeTitleManager.delete(this);
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
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
  public void setTitle(String pTitle) {
    mTitle = pTitle;
  }

  @Override
  public void setDegreeLevel(DegreeLevel pDegreeLevel) {
    mDegreeLevel = pDegreeLevel;
  }

  @Override
  public void setDegreeLevelId(Integer pDegreeLevelId) {
    mDegreeLevelId = pDegreeLevelId;
  }

  @Override
  public String getTitle() {
    return mTitle;
  }

  @Override
  public DegreeLevel getDegreeLevel() {
    return mDegreeLevel;
  }

  @Override
  public Integer getDegreeLevelId() {
    return mDegreeLevelId;
  }
}
