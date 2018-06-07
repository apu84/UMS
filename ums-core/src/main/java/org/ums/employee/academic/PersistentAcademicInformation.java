package org.ums.employee.academic;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.common.DegreeTitle;
import org.ums.enums.common.DegreeLevel;

public class PersistentAcademicInformation implements MutableAcademicInformation {

  private static AcademicInformationManager sAcademicInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAcademicInformationManager =
        applicationContext.getBean("academicInformationManager", AcademicInformationManager.class);
  }

  private Long mId;
  private String mEmployeeId;
  private DegreeLevel mDegreeLevel;
  private Integer mDegreeLevelId;
  private DegreeTitle mDegreeTitle;
  private Integer mDegreeTitleId;
  private String mInstitute;
  private int mPassingYear;
  private String mResult;
  private String mMajor;
  private Integer mDuration;
  private String mLastModified;

  public PersistentAcademicInformation() {}

  public PersistentAcademicInformation(PersistentAcademicInformation pPersistentAcademicInformation) {
    mId = pPersistentAcademicInformation.getId();
    mEmployeeId = pPersistentAcademicInformation.getEmployeeId();
    mDegreeLevel = pPersistentAcademicInformation.getDegreeLevel();
    mDegreeLevelId = pPersistentAcademicInformation.getDegreeLevelId();
    mDegreeTitle = pPersistentAcademicInformation.getDegreeTitle();
    mDegreeTitleId = pPersistentAcademicInformation.getDegreeTitleId();
    mInstitute = pPersistentAcademicInformation.getInstitute();
    mPassingYear = pPersistentAcademicInformation.getPassingYear();
    mResult = pPersistentAcademicInformation.getResult();
    mMajor = pPersistentAcademicInformation.getMajor();
    mDuration = pPersistentAcademicInformation.getDuration();
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
  public void setDegreeLevel(DegreeLevel pDegreeLevel) {
    mDegreeLevel = pDegreeLevel;
  }

  @Override
  public void setDegreeLevelId(Integer pDegreeLevelId) {
    mDegreeLevelId = pDegreeLevelId;
  }

  @Override
  public void setDegreeTitle(DegreeTitle pDegreeTitle) {
    mDegreeTitle = pDegreeTitle;
  }

  @Override
  public void setDegreeTitleId(Integer pDegreeTitleId) {
    mDegreeTitleId = pDegreeTitleId;
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
  public void setResult(String pResult) {
    mResult = pResult;
  }

  @Override
  public void setMajor(String pMajor) {
    mMajor = pMajor;
  }

  @Override
  public void setDuration(Integer pDuration) {
    mDuration = pDuration;
  }

  @Override
  public String getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public DegreeLevel getDegreeLevel() {
    return mDegreeLevel;
  }

  @Override
  public Integer getDegreeLevelId() {
    return mDegreeLevelId;
  }

  @Override
  public DegreeTitle getDegreeTitle() {
    return mDegreeTitle;
  }

  @Override
  public Integer getDegreeTitleId() {
    return mDegreeTitleId;
  }

  @Override
  public String getInstitute() {
    return mInstitute;
  }

  @Override
  public Integer getPassingYear() {
    return mPassingYear;
  }

  @Override
  public String getResult() {
    return mResult;
  }

  @Override
  public String getMajor() {
    return mMajor;
  }

  @Override
  public Integer getDuration() {
    return mDuration;
  }
}
