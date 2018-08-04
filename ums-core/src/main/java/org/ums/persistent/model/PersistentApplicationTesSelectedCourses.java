package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableApplicationTesSelectedCourses;
import org.ums.manager.ApplicationTesSelectedCourseManager;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public class PersistentApplicationTesSelectedCourses implements MutableApplicationTesSelectedCourses {
  private static StudentManager sStudentManager;
  private static SemesterManager sSemesterManager;
  private static CourseManager sCourseManager;
  private static ApplicationTesSelectedCourseManager sApplicationTesSelectedCourseManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sApplicationTesSelectedCourseManager =
        applicationContext.getBean("applicationTesSelectedCourseManager", ApplicationTesSelectedCourseManager.class);
  }
  private Long mId;
  private String mApplicationDate;
  private String mTeacherId;
  private String mDeptId;
  private String mCourseId;
  private Integer mSemesterId;
  private String mSection;
  private String mInsertionDate;

  public PersistentApplicationTesSelectedCourses() {

  }

  public PersistentApplicationTesSelectedCourses(
      final PersistentApplicationTesSelectedCourses persistentApplicationTesSelectedCourses) {
    mId = persistentApplicationTesSelectedCourses.getId();
    mApplicationDate = persistentApplicationTesSelectedCourses.getApplicationDate();
    mTeacherId = persistentApplicationTesSelectedCourses.getTeacherId();
    mDeptId = persistentApplicationTesSelectedCourses.getDeptId();
    mCourseId = persistentApplicationTesSelectedCourses.getCourseId();
    mSemesterId = persistentApplicationTesSelectedCourses.getSemester();
    mSection = persistentApplicationTesSelectedCourses.getSection();
    mInsertionDate = persistentApplicationTesSelectedCourses.getInsertionDate();
  }

  @Override
  public void setApplicationDate(String pApplicationDate) {
    mApplicationDate = pApplicationDate;
  }

  @Override
  public void setCourseId(String pReviewEligibleCourses) {
    mCourseId = pReviewEligibleCourses;
  }

  @Override
  public void setTeacherId(String pTeacherId) {
    mTeacherId = pTeacherId;
  }

  @Override
  public void setSection(String pSection) {
    mSection = pSection;
  }

  @Override
  public void setDeptId(String pDeptId) {
    mDeptId = pDeptId;
  }

  @Override
  public void setSemester(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setInsertionDate(String pInsertionDate) {
    mInsertionDate = pInsertionDate;
  }

  @Override
  public MutableApplicationTesSelectedCourses edit() {
    return new PersistentApplicationTesSelectedCourses((this));
  }

  @Override
  public Long create() {
    return sApplicationTesSelectedCourseManager.create(this);
  }

  @Override
  public void update() {
    sApplicationTesSelectedCourseManager.update(this);
  }

  @Override
  public void delete() {
    sApplicationTesSelectedCourseManager.delete(this);
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public String getApplicationDate() {
    return mApplicationDate;
  }

  @Override
  public String getCourseId() {
    return mCourseId;
  }

  @Override
  public String getTeacherId() {
    return mTeacherId;
  }

  @Override
  public String getSection() {
    return mSection;
  }

  @Override
  public String getDeptId() {
    return mDeptId;
  }

  @Override
  public Integer getSemester() {
    return mSemesterId;
  }

  @Override
  public String getInsertionDate() {
    return mInsertionDate;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
