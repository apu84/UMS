package org.ums.persistent.model.registrar;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.enums.common.AcademicDegreeType;
import org.ums.manager.registrar.AcademicInformationManager;

public class PersistentAcademicInformation implements MutableAcademicInformation {

  private static AcademicInformationManager sAcademicInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAcademicInformationManager =
        applicationContext.getBean("academicInformationManager", AcademicInformationManager.class);
  }

  private Long mId;
  private String mEmployeeId;
  private AcademicDegreeType mDegree;
  private int mDegreeId;
  private String mInstitute;
  private int mPassingYear;
  private String mLastModified;

  public PersistentAcademicInformation() {}

  public PersistentAcademicInformation(PersistentAcademicInformation pPersistentAcademicInformation) {
    mId = pPersistentAcademicInformation.getId();
    mEmployeeId = pPersistentAcademicInformation.getEmployeeId();
    mDegree = pPersistentAcademicInformation.getDegree();
    mDegreeId = pPersistentAcademicInformation.getDegreeId();
    mInstitute = pPersistentAcademicInformation.getInstitute();
    mPassingYear = pPersistentAcademicInformation.getPassingYear();
    mLastModified = pPersistentAcademicInformation.getLastModified();
  }

  @Override
  public MutableAcademicInformation edit() {
    return new PersistentAcademicInformation(this);
  }

  @Override
  public Long create() {
    return sAcademicInformationManager.create(this);
  }

  @Override
  public void update() {
    sAcademicInformationManager.update(this);
  }

  @Override
  public void delete() {
    sAcademicInformationManager.delete(this);
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
  public void setEmployeeId(String pEmployeeId) {
    mEmployeeId = pEmployeeId;
  }

  @Override
  public void setDegree(AcademicDegreeType pDegree) {
    mDegree = pDegree;
  }

  @Override
  public void setDegreeId(int pDegreeId) {
    mDegreeId = pDegreeId;
  }

  @Override
  public void setInstitute(String pInstitute) {
    mInstitute = pInstitute;
  }

  @Override
  public void setPassingYear(int pPassingYear) {
    mPassingYear = pPassingYear;
  }

  @Override
  public String getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public AcademicDegreeType getDegree() {
    return mDegree;
  }

  @Override
  public int getDegreeId() {
    return mDegreeId;
  }

  @Override
  public String getInstitute() {
    return mInstitute;
  }

  @Override
  public int getPassingYear() {
    return mPassingYear;
  }
}
