package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;
import org.ums.manager.StudentRecordManager;

public class PersistentStudentRecord implements MutableStudentRecord {
  private static StudentRecordManager sStudentRecordManager;
  private static StudentManager sStudentManager;
  private static ProgramManager sProgramManager;
  private static SemesterManager sSemesterManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sStudentRecordManager =
        applicationContext.getBean("studentRecordManager", StudentRecordManager.class);
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
  }

  private Integer mId;
  private String mStudentId;
  private Student mStudent;
  private Integer mSemesterId;
  private Semester mSemester;
  private Integer mYear;
  private Integer mAcademicSemester;
  private Double mCGPA;
  private Double mGPA;
  private StudentRecord.Status mStatus;
  private StudentRecord.Type mType;
  private String mLastModified;
  private Integer mProgramId;
  private Program mProgram;

  public PersistentStudentRecord() {}

  public PersistentStudentRecord(final PersistentStudentRecord pPersistentStudentRecord)
      throws Exception {
    setId(pPersistentStudentRecord.getId());
    setStudentId(pPersistentStudentRecord.getStudentId());
    setSemesterId(pPersistentStudentRecord.getSemesterId());
    setYear(pPersistentStudentRecord.getYear());
    setAcademicSemester(pPersistentStudentRecord.getAcademicSemester());
    setCGPA(pPersistentStudentRecord.getCGPA());
    setGPA(pPersistentStudentRecord.getGPA());
    setType(pPersistentStudentRecord.getType());
    setStatus(pPersistentStudentRecord.getStatus());
    setLastModified(pPersistentStudentRecord.getLastModified());
  }

  @Override
  public void setStudentId(String pStudentId) {
    mStudentId = pStudentId;
  }

  @Override
  public void setStudent(Student pStudent) {
    mStudent = pStudent;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setYear(Integer pYear) {
    mYear = pYear;
  }

  @Override
  public void setAcademicSemester(Integer pAcademicSemester) {
    mAcademicSemester = pAcademicSemester;
  }

  @Override
  public void setCGPA(Double pCGPA) {
    mCGPA = pCGPA;
  }

  @Override
  public void setGPA(Double pGPA) {
    mGPA = pGPA;
  }

  @Override
  public void setType(Type pType) {
    mType = pType;
  }

  @Override
  public void setStatus(Status pStatus) {
    mStatus = pStatus;
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void commit(boolean update) throws Exception {
    if(update) {
      sStudentRecordManager.update(this);
    }
    else {
      sStudentRecordManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sStudentRecordManager.delete(this);
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
  public String getStudentId() {
    return mStudentId;
  }

  @Override
  public Student getStudent() throws Exception {
    return mStudent == null ? sStudentManager.get(mStudentId) : sStudentManager.validate(mStudent);
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public Semester getSemester() throws Exception {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager
        .validate(mSemester);
  }

  @Override
  public Integer getYear() {
    return mYear;
  }

  @Override
  public Integer getAcademicSemester() {
    return mAcademicSemester;
  }

  @Override
  public Double getCGPA() {
    return mCGPA;
  }

  @Override
  public Double getGPA() {
    return mGPA;
  }

  @Override
  public Type getType() {
    return mType;
  }

  @Override
  public Status getStatus() {
    return mStatus;
  }

  @Override
  public MutableStudentRecord edit() throws Exception {
    return new PersistentStudentRecord(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setProgramId(Integer pProgramId) {
    mProgramId = pProgramId;
  }

  @Override
  public void setProgram(Program pProgram) {
    mProgram = pProgram;
  }

  @Override
  public Integer getProgramId() {
    return mProgramId;
  }

  @Override
  public Program getProgram() throws Exception {
    return mProgram == null ? sProgramManager.get(mProgramId) : sProgramManager.validate(mProgram);
  }
}
