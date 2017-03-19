package org.ums.persistent.model;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.ResultPublish;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableResultPublish;
import org.ums.manager.ProgramManager;
import org.ums.manager.ResultPublishManager;
import org.ums.manager.SemesterManager;

public class PersistentResultPublish implements MutableResultPublish {
  private static ProgramManager sProgramManager;
  private static SemesterManager sSemesterManager;
  private static ResultPublishManager sResultPublishManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sResultPublishManager =
        applicationContext.getBean("resultPublishManager", ResultPublishManager.class);
  }

  private Long mId;
  private Integer mSemesterId, mProgramId;
  private Semester mSemester;
  private Program mProgram;
  private Date mDate;
  private String mLastModified;

  public PersistentResultPublish() {

  }

  private PersistentResultPublish(final ResultPublish pResultPublish) {
    setId(pResultPublish.getId());
    setProgramId(pResultPublish.getProgramId());
    setSemesterId(pResultPublish.getSemesterId());
    setPublishDate(pResultPublish.getPublishedDate());
    setLastModified(pResultPublish.getLastModified());
  }

  @Override
  public Long create() {
    return sResultPublishManager.create(this);
  }

  @Override
  public void update() {
    sResultPublishManager.update(this);
  }

  @Override
  public MutableResultPublish edit() {
    return new PersistentResultPublish(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Long getId() {
    return mId;
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
  public void delete() {
    sResultPublishManager.delete(this);
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
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
  public Integer getProgramId() {
    return mProgramId;
  }

  @Override
  public void setProgramId(Integer pProgramId) {
    mProgramId = pProgramId;
  }

  @Override
  public Program getProgram() {
    return mProgram == null ? sProgramManager.get(mProgramId) : sProgramManager.validate(mProgram);
  }

  @Override
  public Date getPublishedDate() {
    return mDate;
  }

  @Override
  public void setProgram(Program pProgram) {
    mProgram = pProgram;
  }

  @Override
  public void setPublishDate(Date pPublishDate) {
    mDate = pPublishDate;
  }
}
