package org.ums.academic.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ums.domain.model.MutableProgramType;
import org.ums.domain.model.MutableSemester;
import org.ums.domain.model.ProgramType;
import org.ums.domain.model.Semester;
import org.ums.manager.ContentManager;
import org.ums.util.Constants;

import java.util.Date;

public class PersistentSemester implements MutableSemester {

  private static ContentManager<Semester, MutableSemester, Integer> sSemesterManager;
  private static ContentManager<ProgramType, MutableProgramType, Integer> sProgramTypeManager;

  static {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(Constants.SERVICE_CONTEXT);
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
    return mProgramType != null ? mProgramType : sProgramTypeManager.get(mProgramTypeId);
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

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
