package org.ums.academic.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableProgramType;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.readOnly.ProgramType;
import org.ums.domain.model.readOnly.Semester;
import org.ums.manager.ContentManager;

import java.util.Date;

public class PersistentSemester implements MutableSemester {

  private static ContentManager<Semester, MutableSemester, Integer> sSemesterManager;
  private static ContentManager<ProgramType, MutableProgramType, Integer> sProgramTypeManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = (ContentManager<Semester, MutableSemester, Integer>)applicationContext.getBean("semesterManager");
    sProgramTypeManager = (ContentManager<ProgramType, MutableProgramType, Integer>)applicationContext.getBean("programTypeManager");
  }

  private int mId;
  private String mName;
  private Date mStartDate;
  private Date mEndDate;
  private boolean mStatus;
  private ProgramType mProgramType;
  private String mLastModified;
  private int mProgramTypeId;

  public PersistentSemester() {

  }

  public PersistentSemester(final PersistentSemester pOriginal) throws Exception {
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

  public boolean getStatus() {
    return mStatus;
  }

  public void setStatus(final boolean pStatus) {
    mStatus = pStatus;
  }

  public ProgramType getProgramType() throws Exception {
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

  public void delete() throws Exception {
    sSemesterManager.delete(this);
  }

  public void commit(final boolean update) throws Exception {
    if (update) {
      sSemesterManager.update(this);
    } else {
      sSemesterManager.create(this);
    }
  }

  public MutableSemester edit() throws Exception {
    return new PersistentSemester(this);
  }
}
