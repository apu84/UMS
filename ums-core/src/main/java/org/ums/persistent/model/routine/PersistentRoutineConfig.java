package org.ums.persistent.model.routine;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.routine.MutableRoutineConfig;
import org.ums.enums.routine.DayType;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.routine.RoutineConfigManager;

import java.time.LocalTime;

public class PersistentRoutineConfig implements MutableRoutineConfig {

  private static ProgramManager sProgramManager;
  private static SemesterManager sSemesterManager;
  private static RoutineConfigManager sRoutineConfigManager;
  private Long mId;
  private Program mProgram;
  private Integer mProgramId;
  private Semester mSemester;
  private Integer mSemesterId;
  private DayType mDayFrom;
  private DayType mDayTo;
  private LocalTime mStartTime;
  private LocalTime mEndTime;
  private Integer mDuration;
  private String mLastModified;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public Program getProgram() {
    return mProgram == null ? sProgramManager.get(mProgramId) : sProgramManager.validate(mProgram);
  }

  @Override
  public void setProgram(Program pProgram) {
    this.mProgram = pProgram;
  }

  @Override
  public Integer getProgramId() {
    return mProgramId;
  }

  @Override
  public void setProgramId(Integer pProgramId) {
    this.mProgramId = pProgramId;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public void setSemester(Semester pSemester) {
    this.mSemester = pSemester;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    this.mSemesterId = pSemesterId;
  }

  @Override
  public DayType getDayFrom() {
    return mDayFrom;
  }

  @Override
  public void setDayFrom(DayType pDayFrom) {
    this.mDayFrom = pDayFrom;
  }

  @Override
  public DayType getDayTo() {
    return mDayTo;
  }

  @Override
  public void setDayTo(DayType pDayTo) {
    this.mDayTo = pDayTo;
  }

  @Override
  public LocalTime getStartTime() {
    return mStartTime;
  }

  @Override
  public void setStartTime(LocalTime pStartTime) {
    this.mStartTime = pStartTime;
  }

  @Override
  public LocalTime getEndTime() {
    return mEndTime;
  }

  @Override
  public void setEndTime(LocalTime pEndTime) {
    this.mEndTime = pEndTime;
  }

  @Override
  public Integer getDuration() {
    return mDuration;
  }

  @Override
  public void setDuration(Integer pDuration) {
    this.mDuration = pDuration;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public Long create() {
    return sRoutineConfigManager.create(this);
  }

  @Override
  public void update() {
    sRoutineConfigManager.update(this);
  }

  @Override
  public MutableRoutineConfig edit() {
    return new PersistentRoutineConfig(this);
  }

  @Override
  public void delete() {
    sRoutineConfigManager.delete(this);
  }

  public PersistentRoutineConfig() {}

  public PersistentRoutineConfig(MutableRoutineConfig pRoutineConfig) {
    setId(pRoutineConfig.getId());
    setProgram(pRoutineConfig.getProgram());
    setProgramId(pRoutineConfig.getProgramId());
    setSemester(pRoutineConfig.getSemester());
    setSemesterId(pRoutineConfig.getSemesterId());
    setDayFrom(pRoutineConfig.getDayFrom());
    setDayTo(pRoutineConfig.getDayTo());
    setStartTime(pRoutineConfig.getStartTime());
    setEndTime(pRoutineConfig.getEndTime());
    setDuration(pRoutineConfig.getDuration());
    setLastModified(pRoutineConfig.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sRoutineConfigManager = applicationContext.getBean("routineConfigManager", RoutineConfigManager.class);
  }
}
