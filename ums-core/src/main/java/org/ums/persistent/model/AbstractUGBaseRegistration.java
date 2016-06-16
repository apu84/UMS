package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGBaseRegistration;
import org.ums.domain.model.mutable.MutableUGBaseRegistration;
import org.ums.enums.ExamType;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

public abstract class AbstractUGBaseRegistration implements MutableUGBaseRegistration {
  protected static StudentManager sStudentManager;
  protected static CourseManager sCourseManager;
  protected static SemesterManager sSemesterManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
  }

  private Integer mId;
  private String mCourseId;
  private Course mCourse;
  private Integer mSemesterId;
  private Semester mSemester;
  private String mStudentId;
  private Student mStudent;
  private String mGradeLetter;
  private ExamType mExamType;
  private UGBaseRegistration.Status mStatus;
  private String mLastModified;

  public AbstractUGBaseRegistration() {
  }

  public AbstractUGBaseRegistration(final MutableUGBaseRegistration pMutableUGBaseRegistration) {
    setCourseId(pMutableUGBaseRegistration.getCourseId());
    setSemesterId(pMutableUGBaseRegistration.getSemesterId());
    setStudentId(pMutableUGBaseRegistration.getStudentId());
    setGradeLetter(pMutableUGBaseRegistration.getGradeLetter());
    setStatus(pMutableUGBaseRegistration.getStatus());
    setExamType(pMutableUGBaseRegistration.getExamType());
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
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
  public Course getCourse() throws Exception {
    return mCourse == null ? sCourseManager.get(mCourseId) : sCourseManager.validate(mCourse);
  }

  @Override
  public void setCourse(Course pCourse) {
    mCourse = pCourse;
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
  public Semester getSemester() throws Exception {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public String getStudentId() {
    return mStudentId;
  }

  @Override
  public void setStudentId(String pStudentId) {
    mStudentId = pStudentId;
  }

  @Override
  public Student getStudent() throws Exception {
    return mStudent == null ? sStudentManager.get(mStudentId) : sStudentManager.validate(mStudent);
  }

  @Override
  public void setStudent(Student pStudent) {
    mStudent = pStudent;
  }

  @Override
  public String getGradeLetter() {
    return mGradeLetter;
  }

  @Override
  public void setGradeLetter(String pGradeLetter) {
    mGradeLetter = pGradeLetter;
  }

  @Override
  public ExamType getExamType() {
    return mExamType;
  }

  public void setExamType(ExamType pExamType) {
    mExamType = pExamType;
  }

  public Status getStatus() {
    return mStatus;
  }

  @Override
  public void setStatus(Status pStatus) {
    mStatus = pStatus;
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