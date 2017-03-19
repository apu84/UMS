package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;
import org.ums.manager.ProgramManager;
import org.ums.manager.RoutineManager;
import org.ums.manager.SeatPlanGroupManager;
import org.ums.manager.SemesterManager;

/**
 * Created by My Pc on 4/20/2016.
 */
public class PersistentSeatPlanGroup implements MutableSeatPlanGroup {

  private static SemesterManager sSemesterManager;
  private static ProgramManager sProgramManager;
  private static SeatPlanGroupManager sSeatPlanGroupManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
    sSeatPlanGroupManager = applicationContext.getBean("seatPlanGroupManager", SeatPlanGroupManager.class);
  }

  private int mId;
  private Semester mSemester;
  private int mSemesterId;
  private Program mProgram;
  private int mProgramId;
  private int mAcademicYear;
  private int mAcademicSemester;
  private String mLastModified;
  private int mGroupNo;
  private String mLastUpdateDate;
  private int mType;
  private int mTotalStudentNumber;
  private String mProgramShortName;

  public PersistentSeatPlanGroup() {

  }

  public PersistentSeatPlanGroup(final PersistentSeatPlanGroup pPersistentSeatPlanGroup) {
    mId = pPersistentSeatPlanGroup.getId();
    mSemester = pPersistentSeatPlanGroup.getSemester();
    mSemesterId = pPersistentSeatPlanGroup.getSemesterId();
    mProgram = pPersistentSeatPlanGroup.getProgram();
    mProgramId = pPersistentSeatPlanGroup.getProgramId();
    mAcademicYear = pPersistentSeatPlanGroup.getAcademicYear();
    mAcademicSemester = pPersistentSeatPlanGroup.getAcademicSemester();
    mGroupNo = pPersistentSeatPlanGroup.getGroupNo();
    mTotalStudentNumber = pPersistentSeatPlanGroup.getTotalStudentNumber();
    mProgramShortName = pPersistentSeatPlanGroup.getProgramName();
    mLastUpdateDate = pPersistentSeatPlanGroup.getLastUpdateDate();
  }

  @Override
  public void setTotalStudentNumber(int pTotalStudentNumber) {
    mTotalStudentNumber = pTotalStudentNumber;
  }

  @Override
  public void setProgramShortName(String pProgramShortName) {
    mProgramShortName = pProgramShortName;
  }

  @Override
  public int getTotalStudentNumber() {
    return mTotalStudentNumber;
  }

  @Override
  public String getProgramName() {
    return mProgramShortName;
  }

  @Override
  public void setLastUpdateDate(String pLastUpdateDate) {
    mLastUpdateDate = pLastUpdateDate;
  }

  @Override
  public void setExamType(int pType) {
    mType = pType;
  }

  @Override
  public int getExamType() {
    return mType;
  }

  @Override
  public String getLastUpdateDate() {
    return mLastUpdateDate;
  }

  @Override
  public void setGroupNo(int pGroupNo) {
    mGroupNo = pGroupNo;
  }

  @Override
  public int getGroupNo() {
    return mGroupNo;
  }

  public int getSemesterId() {
    return mSemesterId;
  }

  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  public int getProgramId() {
    return mProgramId;
  }

  public void setProgramId(int pProgramId) {
    mProgramId = pProgramId;
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setProgram(Program pProgram) {
    mProgram = pProgram;
  }

  @Override
  public void setAcademicYear(int pAcademicYear) {
    mAcademicYear = pAcademicYear;
  }

  @Override
  public void setAcademicSemester(int pAcademicSemester) {
    mAcademicSemester = pAcademicSemester;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Integer create() {
    return sSeatPlanGroupManager.create(this);
  }

  @Override
  public void update() {
    sSeatPlanGroupManager.update(this);
  }

  @Override
  public void delete() {
    sSeatPlanGroupManager.delete(this);
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
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);

  }

  @Override
  public Program getProgram() {
    return mProgram == null ? sProgramManager.get(mProgramId) : sProgramManager.validate(mProgram);

  }

  @Override
  public int getAcademicYear() {
    return mAcademicYear;
  }

  @Override
  public int getAcademicSemester() {
    return mAcademicSemester;
  }

  @Override
  public MutableSeatPlanGroup edit() {
    return new PersistentSeatPlanGroup(this);
  }

  @Override
  public Integer getId() {
    return mId;
  }
}
