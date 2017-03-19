package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableSpStudent;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.SpStudentManager;

/**
 * Created by My Pc on 4/27/2016.
 */
public class PersistentSpStudent implements MutableSpStudent {

  private static ProgramManager sProgramManager;
  private static SemesterManager sSemesterManager;
  private static SpStudentManager sSpStudentManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sSpStudentManager = applicationContext.getBean("spStudentManager", SpStudentManager.class);
  }

  private String mStudentId;
  private Program mProgram;
  private int mProgramId;
  private Semester mSemester;
  private int mSemesterId;
  private int mAcademicYear;
  private int mAcademicSemester;
  private int mStatus;
  private String mProgramShortname;
  private Integer mApplicationType;
  private String mLastModified;

  public PersistentSpStudent() {

  }

  public PersistentSpStudent(final PersistentSpStudent pPersistentSpStudent) {
    mStudentId = pPersistentSpStudent.getId();
    mProgram = pPersistentSpStudent.getProgram();
    mProgramId = pPersistentSpStudent.getProgramId();
    mSemester = pPersistentSpStudent.getSemester();
    mSemesterId = pPersistentSpStudent.getSemesterId();
    mAcademicYear = pPersistentSpStudent.getAcademicYear();
    mAcademicSemester = pPersistentSpStudent.getAcademicSemester();
    mStatus = pPersistentSpStudent.getStatus();
    mProgramShortname = pPersistentSpStudent.getProgramShortName();
    mApplicationType = pPersistentSpStudent.getApplicationType();
    mLastModified = pPersistentSpStudent.getLastModified();

  }

  @Override
  public void setProgramShortName(String pProgramShortName) {
    mProgramShortname = pProgramShortName;
  }

  @Override
  public void setApplicationType(Integer pApplicationType) {
    mApplicationType = pApplicationType;
  }

  @Override
  public String getProgramShortName() {
    return mProgramShortname;
  }

  @Override
  public int getApplicationType() {
    return mApplicationType;
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
  public void setProgram(Program pProgram) {
    mProgram = pProgram;
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
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
  public void setStatus(int pStatus) {
    mStatus = pStatus;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sSpStudentManager.update(this);
    }
    else {
      sSpStudentManager.create(this);
    }
  }

  @Override
  public void delete() {
    sSpStudentManager.delete(this);
  }

  @Override
  public void setId(String pId) {
    mStudentId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public Program getProgram() {
    return mProgram == null ? sProgramManager.get(mProgramId) : sProgramManager.validate(mProgram);
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
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
  public int getStatus() {
    return mStatus;
  }

  @Override
  public MutableSpStudent edit() {
    return new PersistentSpStudent(this);
  }

  @Override
  public String getId() {
    return mStudentId;
  }
}
