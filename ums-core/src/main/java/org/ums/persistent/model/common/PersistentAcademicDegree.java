package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.common.MutableAcademicDegree;
import org.ums.manager.common.AcademicDegreeManager;

public class PersistentAcademicDegree implements MutableAcademicDegree {

  private static AcademicDegreeManager sAcademicDegreeManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAcademicDegreeManager = applicationContext.getBean("academicDegreeManager", AcademicDegreeManager.class);
  }

  private Integer mId;
  private int mDegreeType;
  private String mDegreeName;
  private String mDegreeShortName;
  private String mLastModified;

  public PersistentAcademicDegree() {}

  public PersistentAcademicDegree(PersistentAcademicDegree pPersistentAcademicDegree) {
    mId = pPersistentAcademicDegree.getId();
    mDegreeType = pPersistentAcademicDegree.getDegreeType();
    mDegreeName = pPersistentAcademicDegree.getDegreeName();
    mDegreeShortName = pPersistentAcademicDegree.getDegreeShortName();
    mLastModified = pPersistentAcademicDegree.getLastModified();
  }

  @Override
  public void setDegreeType(int pDegreeType) {
    mDegreeType = pDegreeType;
  }

  @Override
  public void setDegreeName(String pDegreeName) {
    mDegreeName = pDegreeName;
  }

  @Override
  public void setDegreeShortName(String pDegreeShortName) {
    mDegreeShortName = pDegreeShortName;
  }

  @Override
  public MutableAcademicDegree edit() {
    return new PersistentAcademicDegree(this);
  }

  @Override
  public Integer create() {
    return sAcademicDegreeManager.create(this);
  }

  @Override
  public void update() {
    sAcademicDegreeManager.update(this);
  }

  @Override
  public void delete() {
    sAcademicDegreeManager.delete(this);
  }

  @Override
  public int getDegreeType() {
    return mDegreeType;
  }

  @Override
  public String getDegreeName() {
    return mDegreeName;
  }

  @Override
  public String getDegreeShortName() {
    return mDegreeShortName;
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
}
