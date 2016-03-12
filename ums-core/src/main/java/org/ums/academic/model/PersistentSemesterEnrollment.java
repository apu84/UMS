package org.ums.academic.model;


import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableSemesterEnrollment;
import org.ums.domain.model.readOnly.Program;
import org.ums.domain.model.readOnly.Semester;
import org.ums.domain.model.readOnly.SemesterEnrollment;
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
    sSemesterEnrollmentManager = applicationContext.getBean("semesterEnrollmentManager", SemesterEnrollmentManager.class);
  }

  private Integer mId;
  private Integer mSemesterId;
  private Semester mSemester;
  private Integer mProgramId;
  private Program mProgram;
  private Integer mYear;
  private Integer mAcademicSemester;
  private Date mEnrollmentDate;
  private SemesterEnrollment.Type mType;
  private String mLastModified;

  public PersistentSemesterEnrollment() {
  }

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
  public void commit(boolean update) throws Exception {
    if (update) {
      sSemesterEnrollmentManager.update(this);
    } else {
      sSemesterEnrollmentManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sSemesterEnrollmentManager.delete(this);
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
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public Semester getSemester() throws Exception {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public Integer getProgramId() {
    return mProgramId;
  }

  @Override
  public Program getProgram() throws Exception {
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
  public MutableSemesterEnrollment edit() throws Exception {
    return new PersistentSemesterEnrollment(this);
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }
}
