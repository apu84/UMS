package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Parameter;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableParameterSetting;
import org.ums.manager.ParameterManager;
import org.ums.manager.ParameterSettingManager;
import org.ums.manager.SemesterManager;

import java.util.Date;

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
  private Integer mSemesterId;
  private Parameter mParameter;
  private String mParameterId;
  private Date mStartDate;
  private Date mEndDate;
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

  public Integer getSemesterId() {
    return mSemesterId;
  }

  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  public String getParameterId() {
    return mParameterId;
  }

  public void setParameterId(String pParameterId) {
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
  public void setStartDate(Date pStartDate) {
    mStartDate = pStartDate;
  }

  @Override
  public void setEndDate(Date pEndDate) {
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
  public Date getStartDate() {
    return mStartDate;
  }

  @Override
  public Date getEndDate() {
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
