package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableDepartmentSelectionDeadline;
import org.ums.manager.DepartmentSelectionDeadlineManager;
import org.ums.manager.SemesterManager;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 27-Apr-17.
 */
public class PersistentDepartmentSelectionDeadline implements MutableDepartmentSelectionDeadline {

  private static SemesterManager sSemesterManager;
  private static DepartmentSelectionDeadlineManager sDepartmentSelectionDeadlineManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sDepartmentSelectionDeadlineManager =
        applicationContext.getBean("departmentSelectionDeadlineManager", DepartmentSelectionDeadlineManager.class);
  }

  private int mId;
  private int mSemesterId;
  private Semester mSemester;
  private String mUnit;
  private String mQuota;
  private int mFromMeritSerialNumber;
  private int mToMeritSerialNumber;
  private Date mDeadline;
  private String mLastModified;

  public PersistentDepartmentSelectionDeadline() {

  }

  public PersistentDepartmentSelectionDeadline(
      final PersistentDepartmentSelectionDeadline pPersistentDepartmentSelectionDeadline) {
    mId = pPersistentDepartmentSelectionDeadline.getId();
    mSemesterId = pPersistentDepartmentSelectionDeadline.getSemesterId();
    mSemester = pPersistentDepartmentSelectionDeadline.getSemester();
    mUnit = pPersistentDepartmentSelectionDeadline.getUnit();
    mQuota = pPersistentDepartmentSelectionDeadline.getQuota();
    mFromMeritSerialNumber = pPersistentDepartmentSelectionDeadline.getFromMeritSerialNumber();
    mToMeritSerialNumber = pPersistentDepartmentSelectionDeadline.getToMeritSerialNumber();
    mDeadline = pPersistentDepartmentSelectionDeadline.getDeadline();
    mLastModified = pPersistentDepartmentSelectionDeadline.getLastModified();
  }

  @Override
  public Integer create() {
    return sDepartmentSelectionDeadlineManager.create(this);
  }

  @Override
  public MutableDepartmentSelectionDeadline edit() {
    return new PersistentDepartmentSelectionDeadline(this);
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
  public void update() {
    sDepartmentSelectionDeadlineManager.update(this);
  }

  @Override
  public void delete() {
    sDepartmentSelectionDeadlineManager.delete(this);
  }

  @Override
  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public int getSemesterId() {
    return mSemesterId;
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public Semester getSemester() {
    return mSemester != null ? sSemesterManager.validate(mSemester) : sSemesterManager.get(mSemesterId);
  }

  @Override
  public String getUnit() {
    return mUnit;
  }

  @Override
  public void setUnit(String pUnit) {
    mUnit = pUnit;
  }

  @Override
  public String getQuota() {
    return mQuota;
  }

  @Override
  public int getFromMeritSerialNumber() {
    return mFromMeritSerialNumber;
  }

  @Override
  public void setQuota(String pQuota) {
    mQuota = pQuota;
  }

  @Override
  public int getToMeritSerialNumber() {
    return mToMeritSerialNumber;
  }

  @Override
  public void setFromMeritSerialNumber(int pFromMeritSerialNumber) {
    mFromMeritSerialNumber = pFromMeritSerialNumber;
  }

  @Override
  public Date getDeadline() {
    return mDeadline;
  }

  @Override
  public void setToMeritSerialNumber(int pToMeritSerialNumber) {
    mToMeritSerialNumber = pToMeritSerialNumber;
  }

  @Override
  public void setDeadline(Date pDeadline) {
    mDeadline = pDeadline;
  }
}
