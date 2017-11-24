package org.ums.employee.training;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.enums.registrar.TrainingCategory;

public class PersistentTrainingInformation implements MutableTrainingInformation {

  private static TrainingInformationManager sTrainingInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sTrainingInformationManager =
        applicationContext.getBean("trainingInformationManager", TrainingInformationManager.class);
  }

  private Long mId;
  private String mEmployeeId;
  private String mTrainingName;
  private String mTrainingInstitute;
  private String mTrainingFromDate;
  private String mTrainingToDate;
  private int mTrainingDuration;
  private String mTrainingDurationString;
  private TrainingCategory mTrainingCategory;
  private int mTrainingCategoryId;
  private String mLastModified;

  public PersistentTrainingInformation() {}

  public PersistentTrainingInformation(PersistentTrainingInformation pPersistentTrainingInformation) {
    mId = pPersistentTrainingInformation.getId();
    mEmployeeId = pPersistentTrainingInformation.getEmployeeId();
    mTrainingName = pPersistentTrainingInformation.getTrainingName();
    mTrainingInstitute = pPersistentTrainingInformation.getTrainingInstitute();
    mTrainingFromDate = pPersistentTrainingInformation.getTrainingFromDate();
    mTrainingToDate = pPersistentTrainingInformation.getTrainingToDate();
    mTrainingDuration = pPersistentTrainingInformation.getTrainingDuration();
    mLastModified = pPersistentTrainingInformation.getLastModified();
  }

  @Override
  public MutableTrainingInformation edit() {
    return new PersistentTrainingInformation(this);
  }

  @Override
  public Long create() {
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
  public void setTrainingDuration(int pTrainingDuration) {
    mTrainingDuration = pTrainingDuration;
  }

  @Override
  public void setTrainingDurationString(String pTrainingDurationString) {
    mTrainingDurationString = pTrainingDurationString;
  }

  @Override
  public void setTrainingCategory(TrainingCategory pTrainingCategory) {
    mTrainingCategory = pTrainingCategory;
  }

  @Override
  public void setTrainingCategoryId(int pTrainingCategoryId) {
    mTrainingCategoryId = pTrainingCategoryId;
  }

  @Override
  public String getEmployeeId() {
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

  @Override
  public int getTrainingDuration() {
    return mTrainingDuration;
  }

  @Override
  public String getTrainingDurationString() {
    return mTrainingDurationString;
  }

  @Override
  public TrainingCategory getTrainingCategory() {
    return mTrainingCategory;
  }

  @Override
  public int getTrainingCategoryId() {
    return mTrainingCategoryId;
  }
}
