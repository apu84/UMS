package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.manager.ProgramTypeManager;
import org.ums.manager.SemesterManager;

import java.util.Date;

public class PersistentSemester implements MutableSemester {

  private static SemesterManager sSemesterManager;
  private static ProgramTypeManager sProgramTypeManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sProgramTypeManager = applicationContext.getBean("programTypeManager", ProgramTypeManager.class);
  }

  private int mId;
  private String mName;
  private Date mStartDate;
  private Date mEndDate;
  private Status mStatus;
  private ProgramType mProgramType;
  private String mLastModified;
  private int mProgramTypeId;

  public PersistentSemester() {

  }

  public PersistentSemester(final PersistentSemester pOriginal) {
    mId = pOriginal.getId();
    mName = pOriginal.getName();
    mStartDate = pOriginal.getStartDate();
    mStatus = pOriginal.getStatus();
    mProgramType = pOriginal.getProgramType();
  }

  public Integer getId() {
    return mId;
  }

  public void setId(final Integer pId) {
    mId = pId;
  }

  public String getName() {
    return mName;
  }

  public void setName(final String pName) {
    mName = pName;
  }

  public Date getStartDate() {
    return mStartDate;
  }

  public void setStartDate(final Date pStartDate) {
    mStartDate = pStartDate;
  }

  public Date getEndDate() {
    return mEndDate;
  }

  public void setEndDate(final Date pEndDate) {
    mEndDate = pEndDate;
  }

  public Status getStatus() {
    return mStatus;
  }

  public void setStatus(final Status pStatus) {
    mStatus = pStatus;
  }

  public ProgramType getProgramType() {
    return mProgramType != null ? sProgramTypeManager.validate(mProgramType) : sProgramTypeManager.get(mProgramTypeId);
  }

  public void setProgramType(final ProgramType pProgram) {
    mProgramType = pProgram;
  }

  @Override
  public int getProgramTypeId() {
    return mProgramTypeId;
  }

  public void setProgramTypeId(final int pProgramTypeId) {
    mProgramTypeId = pProgramTypeId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  public void delete() {
    sSemesterManager.delete(this);
  }

  @Override
  public Integer create() {
    return sSemesterManager.create(this);
  }

  @Override
  public void update() {
    sSemesterManager.update(this);
  }

  public MutableSemester edit() {
    return new PersistentSemester(this);
  }
}
