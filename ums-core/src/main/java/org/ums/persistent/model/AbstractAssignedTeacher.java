package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Teacher;
import org.ums.domain.model.mutable.MutableAssignedTeacher;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.TeacherManager;

public abstract class AbstractAssignedTeacher implements MutableAssignedTeacher {
  protected static TeacherManager sTeacherManager;
  protected static CourseManager sCourseManager;
  protected static SemesterManager sSemesterManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sTeacherManager = applicationContext.getBean("teacherManager", TeacherManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
  }

  protected Long mId;
  protected Semester mSemester;
  protected Course mCourse;
  protected String mLastModified;

  protected Integer mSemesterId;
  protected String mCourseId;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
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
  public Course getCourse() {
    return mCourse == null ? sCourseManager.get(mCourseId) : sCourseManager.validate(mCourse);
  }

  @Override
  public void setCourse(Course pCourse) {
    mCourse = pCourse;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  public Integer getSemesterId() {
    return mSemesterId;
  }

  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  public String getCourseId() {
    return mCourseId;
  }

  public void setCourseId(String pCourseId) {
    mCourseId = pCourseId;
  }
}
