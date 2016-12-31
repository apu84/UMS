package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableAdmissionDeadline;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.manager.AdmissionDeadlineManager;
import org.ums.manager.SemesterManager;

/**
 * Created by Monjur-E-Morshed on 29-Dec-16.
 */
public class PersistentAdmissionDeadline implements MutableAdmissionDeadline {

  private static SemesterManager sSemesterManager;

  private static AdmissionDeadlineManager sAdmissionDeadlineManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sAdmissionDeadlineManager =
        applicationContext.getBean("admissionDeadlineManager", AdmissionDeadlineManager.class);
  }

  private int mId;
  private Semester mSemester;
  private int mSemesterId;
  private int mMeritListStartNo;
  private int mMeritListEndNo;
  private String mStartDate;
  private String mEndDate;
  private String mLastModified;

  public PersistentAdmissionDeadline() {}

  public PersistentAdmissionDeadline(final PersistentAdmissionDeadline pPersistentAdmissionDeadline) {
    mId = pPersistentAdmissionDeadline.getId();
    mSemester = pPersistentAdmissionDeadline.getSemester();
    mSemesterId = pPersistentAdmissionDeadline.getSemesterId();
    mMeritListStartNo = pPersistentAdmissionDeadline.getMeritListStartNo();
    mMeritListEndNo = pPersistentAdmissionDeadline.getMeritListEndNo();
    mStartDate = pPersistentAdmissionDeadline.getStartDate();
    mEndDate = pPersistentAdmissionDeadline.getEndDate();
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sAdmissionDeadlineManager.update(this);
    }
    else {
      sAdmissionDeadlineManager.create(this);
    }
  }

  @Override
  public MutableAdmissionDeadline edit() {
    return new PersistentAdmissionDeadline(this);
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
  public void delete() {
    sAdmissionDeadlineManager.delete(this);
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setMeritListStartNo(int pMeritListStartNo) {
    mMeritListStartNo = pMeritListStartNo;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager
        .validate(mSemester);
  }

  @Override
  public int getSemesterId() {
    return mSemesterId;
  }

  @Override
  public int getMeritListStartNo() {
    return mMeritListStartNo;
  }

  @Override
  public void setMeritListEndNo(int pMeritListEndNo) {
    mMeritListEndNo = pMeritListEndNo;
  }

  @Override
  public int getMeritListEndNo() {
    return mMeritListEndNo;
  }

  @Override
  public void setStartDate(String pStartDate) {
    mStartDate = pStartDate;
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
  public void setEndDate(String pEndDate) {
    mEndDate = pEndDate;
  }
}
