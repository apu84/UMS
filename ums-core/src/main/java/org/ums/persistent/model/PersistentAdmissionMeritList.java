package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableAdmissionMeritList;
import org.ums.enums.AdmissionGroupType;
import org.ums.manager.AdmissionMeritListManager;
import org.ums.manager.FacultyManager;
import org.ums.manager.SemesterManager;

/**
 * Created by Monjur-E-Morshed on 10-Dec-16.
 */
public class PersistentAdmissionMeritList implements MutableAdmissionMeritList {

  private static SemesterManager sSemesterManager;
  private static FacultyManager sFacultyManager;
  private static AdmissionMeritListManager sAdmissionMeritListManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sFacultyManager = applicationContext.getBean("facultyManager", FacultyManager.class);
    sAdmissionMeritListManager =
        applicationContext.getBean("admissionMeritListManager", AdmissionMeritListManager.class);
  }

  private int mId;
  private Semester mSemester;
  private int mSemesterId;
  private int mMeritSlNo;
  private int mReceiptId;
  private int mAdmissionRoll;
  private String mCandidateName;
  private AdmissionGroupType mAdmissionGroupType;
  private Faculty mFaculty;
  private int mFacultyId;
  private String mLastModified;

  public PersistentAdmissionMeritList() {}

  public PersistentAdmissionMeritList(
      final PersistentAdmissionMeritList pPersistentAdmissionMeritList) {
    mId = pPersistentAdmissionMeritList.getId();
    mSemester = pPersistentAdmissionMeritList.getSemester();
    mSemesterId = pPersistentAdmissionMeritList.getSemesterId();
    mMeritSlNo = pPersistentAdmissionMeritList.getMeritListSerialNo();
    mReceiptId = pPersistentAdmissionMeritList.getReceiptId();
    mAdmissionRoll = pPersistentAdmissionMeritList.getAdmissionRoll();
    mCandidateName = pPersistentAdmissionMeritList.getCandidateName();
    mAdmissionGroupType = pPersistentAdmissionMeritList.getAdmissionGroup();
    mFaculty = pPersistentAdmissionMeritList.getFaculty();
    mFacultyId = pPersistentAdmissionMeritList.getFacultyId();
    mLastModified = pPersistentAdmissionMeritList.getLastModified();
  }

  public int getSemesterId() {
    return mSemesterId;
  }

  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  public int getFacultyId() {
    return mFacultyId;
  }

  public void setFacultyId(int pFacultyId) {
    mFacultyId = pFacultyId;
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sAdmissionMeritListManager.update(this);
    }
    else {
      sAdmissionMeritListManager.create(this);
    }
  }

  @Override
  public MutableAdmissionMeritList edit() {
    return new PersistentAdmissionMeritList(this);
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
    sAdmissionMeritListManager.delete(this);
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager
        .validate(mSemester);
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public int getMeritListSerialNo() {
    return mMeritSlNo;
  }

  @Override
  public int getReceiptId() {
    return mReceiptId;
  }

  @Override
  public void setMeritListSerialNo(int pMeritListSerialNo) {
    mMeritSlNo = pMeritListSerialNo;
  }

  @Override
  public int getAdmissionRoll() {
    return mAdmissionRoll;
  }

  @Override
  public String getCandidateName() {
    return mCandidateName;
  }

  @Override
  public void setReceiptId(int pReceiptId) {
    mReceiptId = pReceiptId;
  }

  @Override
  public AdmissionGroupType getAdmissionGroup() {
    return mAdmissionGroupType;
  }

  @Override
  public void setAdmissionRoll(int pAdmissionRoll) {
    mAdmissionRoll = pAdmissionRoll;
  }

  @Override
  public Faculty getFaculty() {
    return mFaculty == null ? sFacultyManager.get(mFacultyId) : sFacultyManager.validate(mFaculty);
  }

  @Override
  public void setCandidateName(String pCandidateName) {
    mCandidateName = pCandidateName;
  }

  @Override
  public void setAdmissionGroup(AdmissionGroupType pAdmissionGroup) {
    mAdmissionGroupType = pAdmissionGroup;
  }

  @Override
  public void setFaculty(Faculty pFaculty) {
    mFaculty = pFaculty;
  }
}
