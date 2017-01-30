package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.enums.MigrationStatus;
import org.ums.enums.PresentStatus;
import org.ums.enums.ProgramType;
import org.ums.manager.AdmissionStudentManager;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;

/**
 * Created by Monjur-E-Morshed on 12-Dec-16.
 */
public class PersistentAdmissionStudent implements MutableAdmissionStudent {

  private static SemesterManager sSemesterManager;
  private static ProgramManager sProgramManager;
  private static AdmissionStudentManager sAdmissionStudentManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
    sAdmissionStudentManager =
        applicationContext.getBean("admissionStudentManager", AdmissionStudentManager.class);
  }

  private String mId;
  private Semester mSemester;
  private String mReceiptId;
  private int mSemesterId;
  private String mPin;
  private String mHSCBoard;
  private String mHSCRoll;
  private String mHSCRegNo;
  private int mHSCYear;
  private String mHSCGroup;
  private double mHSCGPA;
  private String mSSCBoard;
  private String mSSCRoll;
  private int mSSCYear;
  private String mSSCGroup;
  private double mSSCGPA;
  private String mGender;
  private String mDateOfBirth;
  private String mStudentName;
  private String mFatherName;
  private String mMotherName;
  private String mQuota;
  private String mUnit;
  private String mAdmissionRoll;
  private int mMeritSerialNo;
  private String mStudentId;
  private Program mAllocatedProgram;
  private int mAllocatedProgramId;
  private MigrationStatus mMigrationStatus;
  private String mLastModified;
  private ProgramType mProgramType;
  private PresentStatus mPresentStatus;
  private int mVerificationStatus;
  private Program mProgramByMerit;
  private int mProgramIdByMerit;
  private Program mProgramByTransfer;
  private int mProgramIdByTransfer;
  private String mNID;
  private String mBirthRegNo;
  private String mPassport;
  private String mDeadline;
  private String mUndertakenDeadline;

  public PersistentAdmissionStudent() {}

  public PersistentAdmissionStudent(final PersistentAdmissionStudent pAdmissionStudent) {
    mId = pAdmissionStudent.getId();
    mReceiptId = pAdmissionStudent.getReceiptId();
    mSemester = pAdmissionStudent.getSemester();
    mSemesterId = pAdmissionStudent.getSemesterId();
    mPin = pAdmissionStudent.getPin();
    mHSCBoard = pAdmissionStudent.getHSCBoard();
    mHSCRoll = pAdmissionStudent.getHSCRoll();
    mHSCRegNo = pAdmissionStudent.getHSCRegNo();
    mHSCYear = pAdmissionStudent.getHSCYear();
    mHSCGroup = pAdmissionStudent.getHSCGroup();
    mHSCGPA = pAdmissionStudent.getHSCGpa();
    mSSCBoard = pAdmissionStudent.getSSCBoard();
    mSSCRoll = pAdmissionStudent.getSSCRoll();
    mSSCYear = pAdmissionStudent.getSSCYear();
    mSSCGroup = pAdmissionStudent.getSSCGroup();
    mSSCGPA = pAdmissionStudent.getSSCGpa();
    mGender = pAdmissionStudent.getGender();
    mDateOfBirth = pAdmissionStudent.getBirthDate();
    mStudentName = pAdmissionStudent.getStudentName();
    mFatherName = pAdmissionStudent.getFatherName();
    mMotherName = pAdmissionStudent.getMotherName();
    mQuota = pAdmissionStudent.getQuota();
    mUnit = pAdmissionStudent.getUnit();
    mAdmissionRoll = pAdmissionStudent.getAdmissionRoll();
    mMeritSerialNo = pAdmissionStudent.getMeritSerialNo();
    mStudentId = pAdmissionStudent.getStudentId();
    mAllocatedProgram = pAdmissionStudent.getAllocatedProgram();
    mAllocatedProgramId = pAdmissionStudent.getAllocatedProgramId();
    mMigrationStatus = pAdmissionStudent.getMigrationStatus();
    mLastModified = pAdmissionStudent.getLastModified();
    mProgramType = pAdmissionStudent.getProgramType();
    mPresentStatus = pAdmissionStudent.getPresentStatus();
    mVerificationStatus = pAdmissionStudent.getVerificationStatus();
    mProgramByMerit = pAdmissionStudent.getProgramByMerit();
    mProgramIdByMerit = pAdmissionStudent.getProgramIdByMerit();
    mProgramByTransfer = pAdmissionStudent.getProgramByTransfer();
    mProgramIdByTransfer = pAdmissionStudent.getProgramIdByTransfer();
    mNID = pAdmissionStudent.getNID();
    mBirthRegNo = pAdmissionStudent.getBirthRegNo();
    mPassport = pAdmissionStudent.getPassportNo();
    mDeadline = pAdmissionStudent.getDeadline();
    mUndertakenDeadline = pAdmissionStudent.getUndertakenDeadline();
  }

  @Override
  public String getDeadline() {
    return mDeadline;
  }

  @Override
  public void setDeadline(String pDeadline) {
    mDeadline = pDeadline;
  }

  @Override
  public String getNID() {
    return mNID;
  }

  @Override
  public String getBirthRegNo() {
    return mBirthRegNo;
  }

  @Override
  public String getPassportNo() {
    return mPassport;
  }

  @Override
  public void setNID(String pNID) {

  }

  @Override
  public void setBirthReg(String pBirthReg) {

  }

  @Override
  public void setPassportNo(String pPassportNo) {

  }

  @Override
  public Program getProgramByMerit() {
    return mProgramByMerit == null ? sProgramManager.get(mProgramIdByMerit) : sProgramManager
        .validate(mProgramByMerit);
  }

  @Override
  public Integer getProgramIdByMerit() {
    return mProgramIdByMerit;
  }

  @Override
  public Program getProgramByTransfer() {
    return mProgramByTransfer == null ? sProgramManager.get(mProgramIdByTransfer) : sProgramManager
        .validate(mProgramByTransfer);
  }

  @Override
  public Integer getProgramIdByTransfer() {
    return mProgramIdByTransfer;
  }

  @Override
  public void setProgramByMerit(Program pProgramByMerit) {
    mProgramByMerit = pProgramByMerit;
  }

  @Override
  public void setProgramIdByMerit(int pProgramIdByMerit) {
    mProgramIdByMerit = pProgramIdByMerit;
  }

  @Override
  public void setProgramByTransfer(Program pProgramByTransfer) {
    mProgramByTransfer = pProgramByTransfer;
  }

  @Override
  public void setProgramIdByTransfer(int pProgramIdByTransfer) {
    mProgramIdByTransfer = pProgramIdByTransfer;
  }

  @Override
  public PresentStatus getPresentStatus() {
    return mPresentStatus;
  }

  @Override
  public Integer getVerificationStatus() {
    return mVerificationStatus;
  }

  @Override
  public String getUndertakenDeadline() {
    return mUndertakenDeadline;
  }

  @Override
  public void setPresentStatus(PresentStatus pPresentStatus) {
    mPresentStatus = pPresentStatus;
  }

  @Override
  public void setVerificationStatus(int pVerificationStatus) {
    mVerificationStatus = pVerificationStatus;
  }

  @Override
  public void setUndertakenDeadline(String pDeadline) {
    mUndertakenDeadline = pDeadline;
  }

  @Override
  public ProgramType getProgramType() {
    return mProgramType;
  }

  @Override
  public void setProgramType(ProgramType pProgramType) {
    mProgramType = pProgramType;
  }

  @Override
  public void setUnit(String pUnit) {
    mUnit = pUnit;
  }

  @Override
  public String getUnit() {
    return mUnit;
  }

  public Integer getAllocatedProgramId() {
    return mAllocatedProgramId;
  }

  public void setAllocatedProgramId(int pAllocatedProgramId) {
    mAllocatedProgramId = pAllocatedProgramId;
  }

  public Integer getSemesterId() {
    return mSemesterId;
  }

  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sAdmissionStudentManager.update(this);
    }
    else {
      sAdmissionStudentManager.create(this);
    }
  }

  @Override
  public MutableAdmissionStudent edit() {
    return new PersistentAdmissionStudent(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void setId(String pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void delete() {
    sAdmissionStudentManager.delete(this);
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager
        .validate(mSemester);
  }

  @Override
  public String getReceiptId() {
    return mId;
  }

  @Override
  public void setPin(String pPin) {
    mPin = pPin;
  }

  @Override
  public String getPin() {
    return mPin;
  }

  @Override
  public void setHSCBoard(String pHSCBoard) {
    mHSCBoard = pHSCBoard;
  }

  @Override
  public String getHSCBoard() {
    return mHSCBoard;
  }

  @Override
  public String getHSCRoll() {
    return mHSCRoll;
  }

  @Override
  public void setHSCRoll(String pHSCRoll) {
    mHSCRoll = pHSCRoll;
  }

  @Override
  public String getHSCRegNo() {
    return mHSCRegNo;
  }

  @Override
  public Integer getHSCYear() {
    return mHSCYear;
  }

  @Override
  public void setHSCRegNo(String pHSCRegNo) {
    mHSCRegNo = pHSCRegNo;
  }

  @Override
  public String getHSCGroup() {
    return mHSCGroup;
  }

  @Override
  public Double getHSCGpa() {
    return mHSCGPA;
  }

  @Override
  public void setHSCYear(int pHSCYear) {
    mHSCYear = pHSCYear;
  }

  @Override
  public String getSSCBoard() {
    return mSSCBoard;
  }

  @Override
  public String getSSCRoll() {
    return mSSCRoll;
  }

  @Override
  public void setHSCGroup(String pHSCGroup) {
    mHSCGroup = pHSCGroup;
  }

  @Override
  public Integer getSSCYear() {
    return mSSCYear;
  }

  @Override
  public void setHSCGpa(double pHSCGpa) {
    mHSCGPA = pHSCGpa;
  }

  @Override
  public String getSSCGroup() {
    return mSSCGroup;
  }

  @Override
  public void setSSCBoard(String pSSCBoard) {
    mSSCBoard = pSSCBoard;
  }

  @Override
  public Double getSSCGpa() {
    return mSSCGPA;
  }

  @Override
  public String getGender() {
    return mGender;
  }

  @Override
  public void setSSCRoll(String pSSCRoll) {
    mSSCRoll = pSSCRoll;
  }

  @Override
  public String getBirthDate() {
    return mDateOfBirth;
  }

  @Override
  public String getStudentName() {
    return mStudentName;
  }

  @Override
  public String getFatherName() {
    return mFatherName;
  }

  @Override
  public String getMotherName() {
    return mMotherName;
  }

  @Override
  public void setSSCYear(int pSSCYear) {
    mSSCYear = pSSCYear;
  }

  @Override
  public String getQuota() {
    return mQuota;
  }

  @Override
  public void setSSCGroup(String pSSCGroup) {
    mSSCGroup = pSSCGroup;
  }

  @Override
  public String getAdmissionRoll() {
    return mAdmissionRoll;
  }

  @Override
  public Integer getMeritSerialNo() {
    return mMeritSerialNo;
  }

  @Override
  public void setSSCGpa(double pSSCGpa) {
    mSSCGPA = pSSCGpa;
  }

  @Override
  public String getStudentId() {
    return mStudentId;
  }

  @Override
  public void setGender(String pGender) {
    mGender = pGender;
  }

  @Override
  public Program getAllocatedProgram() {
    return mAllocatedProgram == null ? sProgramManager.get(mAllocatedProgramId) : sProgramManager
        .validate(mAllocatedProgram);
  }

  @Override
  public void setDateOfBirth(String pDateOfBirth) {
    mDateOfBirth = pDateOfBirth;
  }

  @Override
  public MigrationStatus getMigrationStatus() {
    return mMigrationStatus;
  }

  @Override
  public void setStudentName(String pStudentName) {
    mStudentName = pStudentName;
  }

  @Override
  public void setFatherName(String pFatherName) {
    mFatherName = pFatherName;
  }

  @Override
  public void setMotherName(String pMotherName) {
    mMotherName = pMotherName;
  }

  @Override
  public void setQuota(String pQuota) {
    mQuota = pQuota;
  }

  @Override
  public void setAdmissionRoll(String pAdmissionRoll) {
    mAdmissionRoll = pAdmissionRoll;
  }

  @Override
  public void setMeritSerialNo(int pMeritSerialNo) {
    mMeritSerialNo = pMeritSerialNo;
  }

  @Override
  public void setStudentId(String pStudentId) {
    mStudentId = pStudentId;
  }

  @Override
  public void setAllocatedProgram(Program pAllocatedProgram) {
    mAllocatedProgram = pAllocatedProgram;
  }

  @Override
  public void setMigrationStatus(MigrationStatus pMigrationStatus) {
    mMigrationStatus = pMigrationStatus;
  }
}
