package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableSemesterEnrollment;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SemesterEnrollment;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterEnrollmentManager;
import org.ums.manager.SemesterManager;

import java.util.Date;

public class PersistentSemesterEnrollment implements MutableSemesterEnrollment {
  private static SemesterManager sSemesterManager;
  private static ProgramManager sProgramManager;
  private static SemesterEnrollmentManager sSemesterEnrollmentManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sSemesterEnrollmentManager =
        applicationContext.getBean("semesterEnrollmentManager", SemesterEnrollmentManager.class);
  }

  private Long mId;
  private Integer mSemesterId;
  private Semester mSemester;
  private Integer mProgramId;
  private Program mProgram;
  private Integer mYear;
  private Integer mAcademicSemester;
  private Date mEnrollmentDate;
  private SemesterEnrollment.Type mType;
  private String mLastModified;

  public PersistentSemesterEnrollment() {}

  public PersistentSemesterEnrollment(final PersistentSemesterEnrollment pEnrollment) {
    setId(pEnrollment.getId());
    setSemesterId(pEnrollment.getSemesterId());
    setProgramId(pEnrollment.getProgramId());
    setYear(pEnrollment.getYear());
    setAcademicSemester(pEnrollment.getAcademicSemester());
    setEnrollmentDate(pEnrollment.getEnrollmentDate());
    setType(pEnrollment.getType());
    setLastModified(pEnrollment.getLastModified());
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
  public void setProgramId(Integer pProgramId) {
    mProgramId = pProgramId;
  }

  @Override
  public void setProgram(Program pProgram) {
    mProgram = pProgram;
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
  public void setEnrollmentDate(Date pEnrollmentDate) {
    mEnrollmentDate = pEnrollmentDate;
  }

  @Override
  public void setType(Type pType) {
    mType = pType;
  }

  @Override
  public Long create() {
    return sSemesterEnrollmentManager.create(this);
  }

  @Override
  public void update() {
    sSemesterEnrollmentManager.update(this);
  }

  @Override
  public void delete() {
    sSemesterEnrollmentManager.delete(this);
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
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public Integer getProgramId() {
    return mProgramId;
  }

  @Override
  public Program getProgram() {
    return mProgram == null ? sProgramManager.get(mProgramId) : sProgramManager.validate(mProgram);
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
  public Date getEnrollmentDate() {
    return mEnrollmentDate;
  }

  @Override
  public Type getType() {
    return mType;
  }

  @Override
  public MutableSemesterEnrollment edit() {
    return new PersistentSemesterEnrollment(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }
}
