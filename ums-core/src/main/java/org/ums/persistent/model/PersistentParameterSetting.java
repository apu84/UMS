package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Parameter;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableParameterSetting;
import org.ums.manager.ParameterManager;
import org.ums.manager.ParameterSettingManager;
import org.ums.manager.SemesterManager;

public class PersistentParameterSetting implements MutableParameterSetting {
  private static SemesterManager sSemesterManager;
  private static ParameterManager sParameterManager;
  private static ParameterSettingManager sParameterSettingManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sParameterManager = applicationContext.getBean("parameterManager", ParameterManager.class);
    sParameterSettingManager = applicationContext.getBean("parameterSettingManager", ParameterSettingManager.class);
  }

  private Long mId;
  private Semester mSemester;
  private int mSemesterId;
  private Parameter mParameter;
  private Long mParameterId;
  private String mStartDate;
  private String mEndDate;
  private String mLastModified;

  public PersistentParameterSetting() {

  }

  public PersistentParameterSetting(final PersistentParameterSetting pPersistentParameterSetting) {
    mId = pPersistentParameterSetting.getId();
    mSemester = pPersistentParameterSetting.getSemester();
    mSemesterId = pPersistentParameterSetting.getSemesterId();
    mParameter = pPersistentParameterSetting.getParameter();
    mParameterId = pPersistentParameterSetting.getParameterId();
    mStartDate = pPersistentParameterSetting.getStartDate();
    mEndDate = pPersistentParameterSetting.getEndDate();
    mLastModified = pPersistentParameterSetting.getLastModified();
  }

  public int getSemesterId() {
    return mSemesterId;
  }

  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  public Long getParameterId() {
    return mParameterId;
  }

  public void setParameterId(Long pParameterId) {
    mParameterId = pParameterId;
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setParameter(Parameter pParameter) {
    mParameter = pParameter;
  }

  @Override
  public void setStartDate(String pStartDate) {
    mStartDate = pStartDate;
  }

  @Override
  public void setEndDate(String pEndDate) {
    mEndDate = pEndDate;
  }

  @Override
  public MutableParameterSetting edit() {
    return new PersistentParameterSetting(this);
  }

  @Override
  public Long create() {
    return sParameterSettingManager.create(this);
  }

  @Override
  public void update() {
    sParameterSettingManager.update(this);
  }

  @Override
  public void delete() {
    sParameterSettingManager.delete(this);
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
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public Parameter getParameter() {
    return mParameter == null ? sParameterManager.get(mParameterId) : sParameterManager.validate(mParameter);
  }

  @Override
  public String getStartDate() {
    return mStartDate;
  }

  @Override
  public String getEndDate() {
    return mEndDate;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Long getId() {
    return mId;
  }
}
