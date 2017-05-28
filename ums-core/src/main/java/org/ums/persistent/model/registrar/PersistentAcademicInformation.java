package org.ums.persistent.model.registrar;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.manager.registrar.AcademicInformationManager;

public class PersistentAcademicInformation implements MutableAcademicInformation {

  private static AcademicInformationManager sAcademicInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAcademicInformationManager =
        applicationContext.getBean("academicInformationManager", AcademicInformationManager.class);
  }

  private int mId;
  private String mEmployeeId;
  private String mDegreeName;
  private String mDegreeInstitute;
  private String mDegreePassingYear;
  private String mLastModified;

  public PersistentAcademicInformation() {}

  public PersistentAcademicInformation(PersistentAcademicInformation pPersistentAcademicInformation) {
    mId = pPersistentAcademicInformation.getId();
    mEmployeeId = pPersistentAcademicInformation.getEmployeeId();
    mDegreeName = pPersistentAcademicInformation.getDegreeName();
    mDegreeInstitute = pPersistentAcademicInformation.getDegreeInstitute();
    mDegreePassingYear = pPersistentAcademicInformation.getDegreePassingYear();
    mLastModified = pPersistentAcademicInformation.getLastModified();
  }

  @Override
  public MutableAcademicInformation edit() {
    return new PersistentAcademicInformation(this);
  }

  @Override
  public Integer create() {
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
  public void setEmployeeId(String pEmployeeId) {
    mEmployeeId = pEmployeeId;
  }

  @Override
  public void setDegreeName(String pDegreeName) {
    mDegreeName = pDegreeName;
  }

  @Override
  public void setDegreeInstitute(String pDegreeInstitute) {
    mDegreeInstitute = pDegreeInstitute;
  }

  @Override
  public void setDegreePassingYear(String pDegreePassingYear) {
    mDegreePassingYear = pDegreePassingYear;
  }

  @Override
  public String getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public String getDegreeName() {
    return mDegreeName;
  }

  @Override
  public String getDegreeInstitute() {
    return mDegreeInstitute;
  }

  @Override
  public String getDegreePassingYear() {
    return mDegreePassingYear;
  }
}
