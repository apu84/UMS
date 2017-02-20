package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableEnrollmentFromTo;
import org.ums.domain.model.immutable.Program;
import org.ums.manager.EnrollmentFromToManager;
import org.ums.manager.ProgramManager;

public class PersistentEnrollmentFromTo implements MutableEnrollmentFromTo {
  private static EnrollmentFromToManager sEnrollmentFromToManager;
  private static ProgramManager sProgramManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
    sEnrollmentFromToManager =
        applicationContext.getBean("enrollmentFromToManager", EnrollmentFromToManager.class);
  }

  private Long mId;
  private Integer mProgramId;
  private Program mProgram;
  private Integer mFromYear;
  private Integer mFromSemester;
  private Integer mToYear;
  private Integer mToSemester;
  private String mLastModified;

  public PersistentEnrollmentFromTo() {}

  public PersistentEnrollmentFromTo(final PersistentEnrollmentFromTo pEnrollmentFromTo) {
    setId(pEnrollmentFromTo.getId());
    setProgramId(pEnrollmentFromTo.getProgramId());
    setFromYear(pEnrollmentFromTo.getFromYear());
    setFromSemester(pEnrollmentFromTo.getFromSemester());
    setToYear(pEnrollmentFromTo.getToYear());
    setToSemester(pEnrollmentFromTo.getToSemester());
    setLastModified(pEnrollmentFromTo.getLastModified());
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
  public void setFromYear(Integer pFromYear) {
    mFromYear = pFromYear;
  }

  @Override
  public void setFromSemester(Integer pFromSemester) {
    mFromSemester = pFromSemester;
  }

  @Override
  public void setToYear(Integer pToYear) {
    mToYear = pToYear;
  }

  @Override
  public void setToSemester(Integer pToSemester) {
    mToSemester = pToSemester;
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
  public Integer getFromYear() {
    return mFromYear;
  }

  @Override
  public Integer getFromSemester() {
    return mFromSemester;
  }

  @Override
  public Integer getToYear() {
    return mToYear;
  }

  @Override
  public Integer getToSemester() {
    return mToSemester;
  }

  @Override
  public MutableEnrollmentFromTo edit() {
    return new PersistentEnrollmentFromTo(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
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
  public void commit(boolean update) {
    if(update) {
      sEnrollmentFromToManager.update(this);
    }
    else {
      sEnrollmentFromToManager.create(this);
    }
  }

  @Override
  public void delete() {
    sEnrollmentFromToManager.delete(this);
  }
}
