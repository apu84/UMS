package org.ums.persistent.model.registrar.employee;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.registrar.employee.AcademicInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableAcademicInformation;
import org.ums.manager.registrar.employee.AcademicInformationManager;

public class PersistentAcademicInformation implements MutableAcademicInformation {

  private static AcademicInformationManager sAcademicInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAcademicInformationManager =
        applicationContext.getBean("academicInformationManager", AcademicInformationManager.class);
  }

  private int mId;
  private int mEmployeeId;
  private String mDegreeName;
  private String mDegreeInstitute;
  private String mDegreePassingYear;
  private String mLastModified;

  public PersistentAcademicInformation() {}

  public PersistentAcademicInformation(AcademicInformation pAcademicInformation) {
    mEmployeeId = pAcademicInformation.getEmployeeId();
    mDegreeName = pAcademicInformation.getDegreeName();
    mDegreeInstitute = pAcademicInformation.getDegreeInstitute();
    mDegreePassingYear = pAcademicInformation.getDegreePassingYear();
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
  public void setEmployeeId(int pEmployeeId) {
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
  public int getEmployeeId() {
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
