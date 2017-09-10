package org.ums.employee.experience;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.manager.registrar.ExperienceInformationManager;

public class PersistentExperienceInformation implements MutableExperienceInformation {

  private static ExperienceInformationManager sExperienceInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sExperienceInformationManager =
        applicationContext.getBean("experienceInformationManager", ExperienceInformationManager.class);
  }

  private Long mId;
  private String mEmployeeId;
  private String mExperienceInstitute;
  private String mDesignation;
  private String mExperienceFromDate;
  private String mExperienceToDate;
  private String mLastModified;

  public PersistentExperienceInformation() {}

  public PersistentExperienceInformation(PersistentExperienceInformation pPersistentExperienceInformation) {
    mId = pPersistentExperienceInformation.getId();
    mEmployeeId = pPersistentExperienceInformation.getEmployeeId();
    mExperienceInstitute = pPersistentExperienceInformation.getExperienceInstitute();
    mDesignation = pPersistentExperienceInformation.getDesignation();
    mExperienceFromDate = pPersistentExperienceInformation.getExperienceFromDate();
    mExperienceToDate = pPersistentExperienceInformation.getExperienceToDate();
    mLastModified = pPersistentExperienceInformation.getLastModified();
  }

  @Override
  public MutableExperienceInformation edit() {
    return new PersistentExperienceInformation(this);
  }

  @Override
  public Long create() {
    return sExperienceInformationManager.create(this);
  }

  @Override
  public void update() {
    sExperienceInformationManager.update(this);
  }

  @Override
  public void delete() {
    sExperienceInformationManager.delete(this);
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
  public void setExperienceInstitute(String pExperienceInstitute) {
    mExperienceInstitute = pExperienceInstitute;
  }

  @Override
  public void setDesignation(String pDesignation) {
    mDesignation = pDesignation;
  }

  @Override
  public void setExperienceFromDate(String pExperienceFromDate) {
    mExperienceFromDate = pExperienceFromDate;
  }

  @Override
  public void setExperienceToDate(String pExperienceToDate) {
    mExperienceToDate = pExperienceToDate;
  }

  @Override
  public String getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public String getExperienceInstitute() {
    return mExperienceInstitute;
  }

  @Override
  public String getDesignation() {
    return mDesignation;
  }

  @Override
  public String getExperienceFromDate() {
    return mExperienceFromDate;
  }

  @Override
  public String getExperienceToDate() {
    return mExperienceToDate;
  }
}
