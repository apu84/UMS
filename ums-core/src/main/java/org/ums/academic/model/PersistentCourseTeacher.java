package org.ums.academic.model;


import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.readOnly.Course;
import org.ums.domain.model.readOnly.Semester;
import org.ums.domain.model.readOnly.Teacher;
import org.ums.manager.CourseManager;
import org.ums.manager.CourseTeacherManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.TeacherManager;

public class PersistentCourseTeacher implements MutableCourseTeacher {
  private static TeacherManager sTeacherManager;
  private static CourseManager sCourseManager;
  private static SemesterManager sSemesterManager;
  private static CourseTeacherManager sCourseTeacherManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sTeacherManager = (TeacherManager) applicationContext.getBean("teacherManager");
    sCourseManager = (CourseManager) applicationContext.getBean("courseManager");
    sSemesterManager = (SemesterManager) applicationContext.getBean("semesterManager");
    sCourseTeacherManager = (CourseTeacherManager) applicationContext.getBean("courseTeacherManager");
  }

  private String mId;
  private Semester mSemester;
  private Course mCourse;
  private Teacher mTeacher;
  private String mSection;
  private String mLastModified;

  private Integer mSemesterId;
  private String mCourseId;
  private String mTeacherId;

  public PersistentCourseTeacher() {
  }

  public PersistentCourseTeacher(final PersistentCourseTeacher pPersistentCourseTeacher) throws Exception {
    this.mId = pPersistentCourseTeacher.getId();
    this.mSemester = pPersistentCourseTeacher.getSemester();
    this.mTeacher = pPersistentCourseTeacher.getTeacher();
    this.mCourse = pPersistentCourseTeacher.getCourse();
    this.mSection = pPersistentCourseTeacher.getSection();
    this.mLastModified = pPersistentCourseTeacher.getLastModified();
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void setId(String pId) {
    mId = pId;
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
  public Course getCourse() throws Exception {
    return mCourse == null ? sCourseManager.get(mCourseId) : sCourseManager.validate(mCourse);
  }

  @Override
  public void setCourse(Course pCourse) {
    mCourse = pCourse;
  }

  @Override
  public Teacher getTeacher() throws Exception {
    return mTeacher == null ? sTeacherManager.get(mTeacherId) : sTeacherManager.validate(mTeacher);
  }

  @Override
  public void setTeacher(Teacher pTeacher) {
    mTeacher = pTeacher;
  }

  @Override
  public String getSection() {
    return mSection;
  }

  @Override
  public void setSection(String pSection) {
    mSection = pSection;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public MutableCourseTeacher edit() throws Exception {
    return new PersistentCourseTeacher(this);
  }

  @Override
  public void delete() throws Exception {
    sCourseTeacherManager.delete(this);
  }

  @Override
  public void commit(boolean update) throws Exception {
    if (update) {
      sCourseTeacherManager.update(this);
    } else {
      sCourseTeacherManager.create(this);
    }
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

  public String getTeacherId() {
    return mTeacherId;
  }

  public void setTeacherId(String pTeacherId) {
    mTeacherId = pTeacherId;
  }
}
