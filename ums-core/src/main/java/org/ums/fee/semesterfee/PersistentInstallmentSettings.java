package org.ums.fee.semesterfee;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Semester;
import org.ums.manager.SemesterManager;

public class PersistentInstallmentSettings implements MutableInstallmentSettings {

  private static SemesterManager sSemesterManager;
  private static InstallmentSettingsManager sInstallmentSettingsManager;
  private Long mId;
  private Boolean mEnabled;
  private Semester mSemester;
  private Integer mSemesterId;
  private String mLastModified;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public Boolean isEnabled() {
    return mEnabled;
  }

  @Override
  public void setEnabled(Boolean pEnabled) {
    this.mEnabled = pEnabled;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public void setSemester(Semester pSemester) {
    this.mSemester = pSemester;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    this.mSemesterId = pSemesterId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public Long create() {
    return sInstallmentSettingsManager.create(this);
  }

  @Override
  public void update() {
    sInstallmentSettingsManager.update(this);
  }

  @Override
  public MutableInstallmentSettings edit() {
    return new PersistentInstallmentSettings(this);
  }

  @Override
  public void delete() {
    sInstallmentSettingsManager.delete(this);
  }

  public PersistentInstallmentSettings() {}

  public PersistentInstallmentSettings(MutableInstallmentSettings pInstallmentSettings) {
    setId(pInstallmentSettings.getId());
    setEnabled(pInstallmentSettings.isEnabled());
    setSemester(pInstallmentSettings.getSemester());
    setSemesterId(pInstallmentSettings.getSemesterId());
    setLastModified(pInstallmentSettings.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sInstallmentSettingsManager =
        applicationContext.getBean("installmentSettingsManager", InstallmentSettingsManager.class);
  }
}
