package org.ums.persistent.model.routine;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.manager.ClassRoomManager;
import org.ums.manager.CourseManager;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.routine.RoutineManager;
import org.ums.serializer.UmsTimeDeserializer;
import org.ums.serializer.UmsTimeSerializer;

import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersistentRoutine implements MutableRoutine {
  @JsonIgnore
  private static SemesterManager sSemesterManager;
  @JsonIgnore
  private static ProgramManager sProgramManager;
  @JsonIgnore
  private static RoutineManager sRoutineManager;
  @JsonIgnore
  private static CourseManager sCourseManager;
  @JsonIgnore
  private static ClassRoomManager sClassRoomManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sClassRoomManager = applicationContext.getBean("classRoomManager", ClassRoomManager.class);
    sRoutineManager = applicationContext.getBean("routineManager", RoutineManager.class);
  }

  private Long mId;
  @JsonIgnore
  private Semester mSemester;
  private Integer mSemesterId;
  @JsonIgnore
  private Program mProgram;
  private Integer mProgramId;
  private String mSection;
  private int mAcademicYear;
  private int mAcademicSemester;
  private LocalTime mStartTime;
  private LocalTime mEndTime;
  private String mLastModified;
  private String mCourseId;
  @JsonIgnore
  private Course mCourse;
  private int mDuration;
  private Long mRoomId;
  @JsonIgnore
  private ClassRoom mClassRoom;
  private int mDay;
  private String mStatus;
  private int mSlotGroup;

  @Override
  public int getSlotGroup() {
    return mSlotGroup;
  }

  @Override
  public void setSlotGroup(int pSlotGroup) {
    mSlotGroup = pSlotGroup;
  }

  public PersistentRoutine() {

  }

  public PersistentRoutine(final PersistentRoutine pPersistentRoutine) {
    mId = pPersistentRoutine.getId();
    mSemester = pPersistentRoutine.getSemester();
    mProgram = pPersistentRoutine.getProgram();
    mCourseId = pPersistentRoutine.getCourseId();
    mSection = pPersistentRoutine.getSection();
    mAcademicYear = pPersistentRoutine.getAcademicYear();
    mAcademicSemester = pPersistentRoutine.getAcademicSemester();
    mStartTime = pPersistentRoutine.getStartTime();
    mEndTime = pPersistentRoutine.getEndTime();
    mLastModified = pPersistentRoutine.getLastModified();
    mSemesterId = pPersistentRoutine.getSemesterId();
    mCourseId = pPersistentRoutine.getCourseId();
    mDuration = pPersistentRoutine.getDuration();
    mRoomId = pPersistentRoutine.getRoomId();
    mDay = pPersistentRoutine.getDay();
    mStatus = pPersistentRoutine.getStatus();
  }

  @Override
  public int getDuration() {
    return mDuration;
  }

  @Override
  public void setProgramId(Integer pProgramId) {
    mProgramId = pProgramId;
  }

  @Override
  @JsonIgnore
  public void setCourse(Course pCourse) {
    mCourse = pCourse;
  }

  @Override
  @JsonIgnore
  public void setRoom(ClassRoom pClassRoom) {
    mClassRoom = pClassRoom;
  }

  @Override
  @JsonProperty
  public Course getCourse() {
    return mCourse == null ? sCourseManager.get(mCourseId) : mCourse;
  }

  @Override
  @JsonProperty
  public ClassRoom getRoom() {
    return mClassRoom == null ? sClassRoomManager.get(mRoomId) : mClassRoom;
  }

  @Override
  public String getStatus() {
    return mStatus;
  }

  @Override
  public void setStatus(String pStatus) {
    mStatus = pStatus;
  }

  @Override
  public void setDuration(int pDuration) {
    mDuration = pDuration;
  }

  @Override
  public int getDay() {
    return mDay;
  }

  @Override
  public void setDay(int pDay) {
    mDay = pDay;
  }

  public Integer getProgramId() {
    return mProgramId;
  }

  public void setProgramId(int pProgramId) {
    mProgramId = pProgramId;
  }

  @Override
  @JsonIgnore
  public void setProgram(Program pProgram) {
    mProgram = pProgram;
  }

  @Override
  @JsonProperty
  public Program getProgram() {
    return mProgram == null ? sProgramManager.get(mProgramId) : sProgramManager.validate(mProgram);

  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public Long getRoomId() {
    return mRoomId;
  }

  @Override
  @JsonDeserialize(as = Long.class)
  public void setRoomId(Long pRoomId) {
    mRoomId = pRoomId;
  }

  public String getCourseId() {
    return mCourseId;
  }

  public void setCourseId(String pCourseId) {
    mCourseId = pCourseId;
  }

  public Integer getSemesterId() {
    return mSemesterId;
  }

  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setSection(String pSection) {
    mSection = pSection;
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
  @JsonDeserialize(using = UmsTimeDeserializer.class)
  public void setStartTime(LocalTime pStartTime) {
    mStartTime = pStartTime;
  }

  @Override
  @JsonDeserialize(using = UmsTimeDeserializer.class)
  public void setEndTime(LocalTime pEndTime) {
    mEndTime = pEndTime;
  }

  @Override
  public Long create() {
    return sRoutineManager.create(this);
  }

  @Override
  public void update() {
    sRoutineManager.update(this);
  }

  @Override
  public void delete() {
    sRoutineManager.delete(this);
  }

  @Override
  @JsonDeserialize(as = Long.class)
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public String getSection() {
    return mSection;
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
  @JsonSerialize(using = UmsTimeSerializer.class)
  public LocalTime getStartTime() {
    return mStartTime;
  }

  @Override
  @JsonSerialize(using = UmsTimeSerializer.class)
  public LocalTime getEndTime() {
    return mEndTime;
  }

  @Override
  public MutableRoutine edit() {
    return new PersistentRoutine(this);
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
