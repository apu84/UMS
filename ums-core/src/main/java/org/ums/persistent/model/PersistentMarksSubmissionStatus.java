package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.ExamType;
import org.ums.manager.CourseManager;
import org.ums.manager.MarksSubmissionStatusManager;
import org.ums.manager.SemesterManager;

import java.util.Date;

public class PersistentMarksSubmissionStatus implements MutableMarksSubmissionStatus {
  private static MarksSubmissionStatusManager sMarksSubmissionStatusManager;
  private static CourseManager sCourseManager;
  private static SemesterManager sSemesterManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sMarksSubmissionStatusManager =
        applicationContext.getBean("marksSubmissionStatusManager", MarksSubmissionStatusManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
  }

  private Long mId;
  private Integer mSemesterId;
  private Semester mSemester;
  private String mCourseId;
  private Course mCourse;
  private CourseMarksSubmissionStatus mStatus;
  private ExamType mExamType;
  private Integer mPartATotal;
  private Integer mPartBTotal;
  private Date mLastSubmissionDatePrep;
  private Date mLastSubmissionDateScr;
  private Date mLastSubmissionDateHead;
  private Date mLastSubmissionDateCoe;
  private Integer mYear;
  private Integer mAcademicSemester;
  private String mLastModified;
  private Integer mTotalPart;

  public PersistentMarksSubmissionStatus() {}

  public PersistentMarksSubmissionStatus(MutableMarksSubmissionStatus pMarksSubmissionStatus) {
    setId(pMarksSubmissionStatus.getId());
    setSemesterId(pMarksSubmissionStatus.getSemesterId());
    setCourseId(pMarksSubmissionStatus.getCourseId());
    setStatus(pMarksSubmissionStatus.getStatus());
    setExamType(pMarksSubmissionStatus.getExamType());
    setLastSubmissionDatePrep(pMarksSubmissionStatus.getLastSubmissionDatePrep());
    setLastSubmissionDateScr(pMarksSubmissionStatus.getLastSubmissionDateScr());
    setLastSubmissionDateHead(pMarksSubmissionStatus.getLastSubmissionDateHead());
    setYear(pMarksSubmissionStatus.getYear());
    setAcademicSemester(pMarksSubmissionStatus.getAcademicSemester());
    setPartATotal(pMarksSubmissionStatus.getPartATotal());
    setPartBTotal(pMarksSubmissionStatus.getPartBTotal());
    setLastModified(pMarksSubmissionStatus.getLastModified());
    setTotalPart(pMarksSubmissionStatus.getTotalPart());
    setLastSubmissionDateCoe(pMarksSubmissionStatus.getLastSubmissionDateCoe());
  }

  @Override
  public Date getLastSubmissionDateCoe() {
    return mLastSubmissionDateCoe;
  }

  @Override
  public void setLastSubmissionDateCoe(Date pLastSubmissionDateCoe) {
    mLastSubmissionDateCoe = pLastSubmissionDateCoe;
  }

  @Override
  public Date getLastSubmissionDateScr() {
    return mLastSubmissionDateScr;
  }

  @Override
  public Date getLastSubmissionDateHead() {
    return mLastSubmissionDateHead;
  }

  @Override
  public void setLastSubmissionDateScr(Date pLastSubmissionDateScr) {
    mLastSubmissionDateScr = pLastSubmissionDateScr;
  }

  @Override
  public void setLastSubmissionDateHead(Date pLastSubmissionDateHead) {
    mLastSubmissionDateHead = pLastSubmissionDateHead;
  }

  @Override
  public Long create() {
    return sMarksSubmissionStatusManager.create(this);
  }

  @Override
  public void update() {
    sMarksSubmissionStatusManager.update(this);
  }

  @Override
  public MutableMarksSubmissionStatus edit() {
    return new PersistentMarksSubmissionStatus(this);
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
    sMarksSubmissionStatusManager.delete(this);
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
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public String getCourseId() {
    return mCourseId;
  }

  @Override
  public void setCourseId(String pCourseId) {
    mCourseId = pCourseId;
  }

  @Override
  public Course getCourse() {
    return mCourse == null ? sCourseManager.get(mCourseId) : sCourseManager.validate(mCourse);
  }

  @Override
  public void setCourse(Course pCourse) {
    mCourse = pCourse;
  }

  @Override
  public CourseMarksSubmissionStatus getStatus() {
    return mStatus;
  }

  @Override
  public void setStatus(CourseMarksSubmissionStatus pStatus) {
    mStatus = pStatus;
  }

  @Override
  public ExamType getExamType() {
    return mExamType;
  }

  @Override
  public Integer getPartATotal() {
    return mPartATotal;
  }

  @Override
  public void setExamType(ExamType pExamType) {
    mExamType = pExamType;
  }

  @Override
  public Integer getPartBTotal() {
    return mPartBTotal;
  }

  @Override
  public Date getLastSubmissionDatePrep() {
    return mLastSubmissionDatePrep;
  }

  public void setLastSubmissionDatePrep(Date pLastSubmissionDatePrep) {
    mLastSubmissionDatePrep = pLastSubmissionDatePrep;
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
  public void setPartATotal(Integer pPartATotal) {
    mPartATotal = pPartATotal;
  }

  @Override
  public void setPartBTotal(Integer pPartBTotal) {
    mPartBTotal = pPartBTotal;
  }

  @Override
  public void setYear(Integer pYear) {
    mYear = pYear;
  }

  @Override
  public void setAcademicSemester(Integer pSemester) {
    mAcademicSemester = pSemester;
  }

  @Override
  public Integer getTotalPart() {
    return mTotalPart;
  }

  @Override
  public void setTotalPart(Integer pTotalPart) {
    mTotalPart = pTotalPart;
  }
}
