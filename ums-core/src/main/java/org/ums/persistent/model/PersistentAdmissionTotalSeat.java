package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;
import org.ums.manager.AdmissionTotalSeatManager;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
public class PersistentAdmissionTotalSeat implements MutableAdmissionTotalSeat {

  private static SemesterManager sSemesterManager;
  private static ProgramManager sProgramManager;
  private static AdmissionTotalSeatManager sAdmissionTotalSeatManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
    sAdmissionTotalSeatManager =
        applicationContext.getBean("admissionTotalSeatManager", AdmissionTotalSeatManager.class);
  }

  private int mId;
  private Semester mSemester;
  private int mSemesterId;
  private Program mProgram;
  private int mProgramId;
  private int mTotalSeat;
  private ProgramType mProgramType;
  private QuotaType mQuotaType;
  private String mLastModified;

  public PersistentAdmissionTotalSeat() {}

  public PersistentAdmissionTotalSeat(
      final PersistentAdmissionTotalSeat pPersistentAdmissionTotalSeat) {
    mId = pPersistentAdmissionTotalSeat.getId();
    mSemester = pPersistentAdmissionTotalSeat.getSemester();
    mSemesterId = pPersistentAdmissionTotalSeat.getSemesterId();
    mProgram = pPersistentAdmissionTotalSeat.getProgram();
    mProgramId = pPersistentAdmissionTotalSeat.getProgramId();
    mTotalSeat = pPersistentAdmissionTotalSeat.getTotalSeat();
    mLastModified = pPersistentAdmissionTotalSeat.getLastModified();
    mProgramType = pPersistentAdmissionTotalSeat.getProgramType();
    mQuotaType = pPersistentAdmissionTotalSeat.getQuotaType();
  }

  @Override
  public QuotaType getQuotaType() {
    return mQuotaType;
  }

  @Override
  public void setQuotaType(QuotaType pQuotaType) {
    mQuotaType = pQuotaType;
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
  public Integer create() {
    return sAdmissionTotalSeatManager.create(this);
  }

  @Override
  public void update() {
    sAdmissionTotalSeatManager.update(this);
  }

  @Override
  public MutableAdmissionTotalSeat edit() {
    return new PersistentAdmissionTotalSeat(this);
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
    sAdmissionTotalSeatManager.delete(this);
  }

  @Override
  public int getSemesterId() {
    return mSemesterId;
  }

  @Override
  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
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
  public int getProgramId() {
    return mProgramId;
  }

  @Override
  public void setProgramId(int pProgramid) {
    mProgramId = pProgramid;
  }

  @Override
  public Program getProgram() {
    return mProgram == null ? sProgramManager.get(mProgramId) : sProgramManager.validate(mProgram);
  }

  @Override
  public void setProgram(Program pProgram) {
    mProgram = pProgram;
  }

  @Override
  public int getTotalSeat() {
    return mTotalSeat;
  }

  @Override
  public void setTotalSeat(int pTotalSeat) {
    mTotalSeat = pTotalSeat;
  }
}
