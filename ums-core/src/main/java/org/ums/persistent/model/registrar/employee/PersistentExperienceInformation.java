package org.ums.persistent.model.registrar.employee;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.registrar.employee.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableExperienceInformation;
import org.ums.manager.registrar.employee.ExperienceInformationManager;

public class PersistentExperienceInformation implements MutableExperienceInformation {

  private static ExperienceInformationManager sExperienceInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sExperienceInformationManager =
        applicationContext.getBean("experienceInformationManager", ExperienceInformationManager.class);
  }

  private int mId;
  private int mEmployeeId;
  private String mExperienceInstitute;
  private String mDesignation;
  private String mExperienceFromDate;
  private String mExperienceToDate;
  private String mLastModified;

  public PersistentExperienceInformation() {}

  public PersistentExperienceInformation(ExperienceInformation pExperienceInformation) {
    mEmployeeId = pExperienceInformation.getEmployeeId();
    mExperienceInstitute = pExperienceInformation.getExperienceInstitute();
    mDesignation = pExperienceInformation.getDesignation();
    mExperienceFromDate = pExperienceInformation.getExperienceFromDate();
    mExperienceToDate = pExperienceInformation.getExperienceToDate();
  }

  @Override
  public MutableExperienceInformation edit() {
    return new PersistentExperienceInformation(this);
  }

  @Override
  public Integer create() {

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
  public int getEmployeeId() {
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
