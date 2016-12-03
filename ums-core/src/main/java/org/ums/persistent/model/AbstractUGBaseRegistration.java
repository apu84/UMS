package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGBaseRegistration;
import org.ums.domain.model.mutable.MutableUGBaseRegistration;
import org.ums.enums.CourseRegType;
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
  private String mCourseTitle;
  private Course mCourse;
  private String mCourseNo;
  private Integer mSemesterId;
  private Semester mSemester;
  private String mStudentId;
  private Student mStudent;
  private String mGradeLetter;
  private ExamType mExamType;
  private CourseRegType mType;
  private String mExamDate;
  private String mLastModified;
  private String mMessage;

  public AbstractUGBaseRegistration() {}

  public AbstractUGBaseRegistration(final MutableUGBaseRegistration pMutableUGBaseRegistration) {
    setCourseId(pMutableUGBaseRegistration.getCourseId());
    setCourseNo(pMutableUGBaseRegistration.getCourseNo());
    setSemesterId(pMutableUGBaseRegistration.getSemesterId());
    setStudentId(pMutableUGBaseRegistration.getStudentId());
    setGradeLetter(pMutableUGBaseRegistration.getGradeLetter());
    setExamType(pMutableUGBaseRegistration.getExamType());
    setType(pMutableUGBaseRegistration.getType());
    setExamDate(pMutableUGBaseRegistration.getExamDate());
  }

  @Override
  public String getMessage() {
    if(mMessage == null) {
      mMessage = "null";
    }
    return mMessage;
  }

  @Override
  public void setMessage(String pMessage) {
    mMessage = pMessage;
  }

  @Override
  public void setExamDate(String pExamDate) {
    mExamDate = pExamDate;
  }

  @Override
  public String getExamDate() {
    return mExamDate;
  }

  @Override
  public String getCourseTitle() {
    return mCourseTitle;
  }

  @Override
  public void setCourseTitle(String pCourseTitle) {
    mCourseTitle = pCourseTitle;
  }

  @Override
  public void setCourseNo(String pCourseNo) {
    mCourseNo = pCourseNo;
  }

  @Override
  public String getCourseNo() {
    return mCourseNo;
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void setType(CourseRegType pType) {
    mType = pType;
  }

  public CourseRegType getType() {
    return mType;
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
  public Course getCourse() {
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
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager
        .validate(mSemester);
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
  public Student getStudent() {
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

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
