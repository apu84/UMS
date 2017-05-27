package org.ums.readmission;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

public class PersistentReadmissionApplication implements MutableReadmissionApplication {
  private static SemesterManager sSemesterManager;
  private static StudentManager sStudentManager;
  private static CourseManager sCourseManager;
  private static ReadmissionApplicationManager sReadmissionApplicationManager;
  private Long mId;
  private Date mAppliedOn;
  private Semester mSemester;
  private Integer mSemesterId;
  private Student mStudent;
  private String mStudentId;
  private Course mCourse;
  private String mCourseId;
  private ReadmissionApplication.Status mApplicationStatus;
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
  public Date getAppliedOn() {
    return mAppliedOn;
  }

  @Override
  public void setAppliedOn(Date pAppliedOn) {
    this.mAppliedOn = pAppliedOn;
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
  public Student getStudent() {
    return mStudent == null ? sStudentManager.get(mStudentId) : sStudentManager.validate(mStudent);
  }

  @Override
  public void setStudent(Student pStudent) {
    this.mStudent = pStudent;
  }

  @Override
  public String getStudentId() {
    return mStudentId;
  }

  @Override
  public void setStudentId(String pStudentId) {
    this.mStudentId = pStudentId;
  }

  @Override
  public Course getCourse() {
    return mCourse == null ? sCourseManager.get(mCourseId) : sCourseManager.validate(mCourse);
  }

  @Override
  public void setCourse(Course pCourse) {
    this.mCourse = pCourse;
  }

  @Override
  public String getCourseId() {
    return mCourseId;
  }

  @Override
  public void setCourseId(String pCourseId) {
    this.mCourseId = pCourseId;
  }

  @Override
  public Status getApplicationStatus() {
    return mApplicationStatus;
  }

  @Override
  public void setApplicationStatus(Status pApplicationStatus) {
    mApplicationStatus = pApplicationStatus;
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
    return sReadmissionApplicationManager.create(this);
  }

  @Override
  public void update() {
    sReadmissionApplicationManager.update(this);
  }

  @Override
  public MutableReadmissionApplication edit() {
    return new PersistentReadmissionApplication(this);
  }

  @Override
  public void delete() {
    sReadmissionApplicationManager.delete(this);
  }

  public PersistentReadmissionApplication() {}

  public PersistentReadmissionApplication(MutableReadmissionApplication pReadmissionApplication) {
    setId(pReadmissionApplication.getId());
    setAppliedOn(pReadmissionApplication.getAppliedOn());
    setSemester(pReadmissionApplication.getSemester());
    setSemesterId(pReadmissionApplication.getSemesterId());
    setStudent(pReadmissionApplication.getStudent());
    setStudentId(pReadmissionApplication.getStudentId());
    setCourse(pReadmissionApplication.getCourse());
    setCourseId(pReadmissionApplication.getCourseId());
    setApplicationStatus(pReadmissionApplication.getApplicationStatus());
    setLastModified(pReadmissionApplication.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sReadmissionApplicationManager =
        applicationContext.getBean("readmissionApplicationManager", ReadmissionApplicationManager.class);
  }
}
