package org.ums.persistent.model.registrar.Employee;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.registrar.Employee.TrainingInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableTrainingInformation;
import org.ums.manager.registrar.Employee.TrainingInformationManager;

import java.util.Date;

public class PersistentTrainingInformation implements MutableTrainingInformation {

  private static TrainingInformationManager sTrainingInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sTrainingInformationManager =
        applicationContext.getBean("trainingInformationManager", TrainingInformationManager.class);
  }

  private int mId;
  private int mEmployeeId;
  private String mTrainingName;
  private String mTrainingInstitute;
  private String mTrainingFromDate;
  private String mTrainingToDate;
  private String mLastModified;

  public PersistentTrainingInformation() {}

  public PersistentTrainingInformation(TrainingInformation pTrainingInformation) {
    mEmployeeId = pTrainingInformation.getEmployeeId();
    mTrainingName = pTrainingInformation.getTrainingName();
    mTrainingInstitute = pTrainingInformation.getTrainingInstitute();
    mTrainingFromDate = pTrainingInformation.getTrainingFromDate();
    mTrainingToDate = pTrainingInformation.getTrainingToDate();
  }

  @Override
  public MutableTrainingInformation edit() {
    return new PersistentTrainingInformation(this);
  }

  @Override
  public Integer create() {
    return sTrainingInformationManager.create(this);
  }

  @Override
  public void update() {
    sTrainingInformationManager.update(this);
  }

  @Override
  public void delete() {
    sTrainingInformationManager.delete(this);
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
  public void setTrainingName(String pTrainingName) {
    mTrainingName = pTrainingName;
  }

  @Override
  public void setTrainingInstitute(String pTrainingInstitute) {
    mTrainingInstitute = pTrainingInstitute;
  }

  @Override
  public void setTrainingFromDate(String pTrainingFromDate) {
    mTrainingFromDate = pTrainingFromDate;
  }

  @Override
  public void setTrainingToDate(String pTrainingToDate) {
    mTrainingToDate = pTrainingToDate;
  }

  @Override
  public int getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public String getTrainingName() {
    return mTrainingName;
  }

  @Override
  public String getTrainingInstitute() {
    return mTrainingInstitute;
  }

  @Override
  public String getTrainingFromDate() {
    return mTrainingFromDate;
  }

  @Override
  public String getTrainingToDate() {
    return mTrainingToDate;
  }
}
