package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.immutable.Teacher;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.manager.AssignedTeacherManager;

public class PersistentCourseTeacher extends AbstractAssignedTeacher implements
    MutableCourseTeacher {
  private static AssignedTeacherManager<CourseTeacher, MutableCourseTeacher, Long> sCourseTeacherManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCourseTeacherManager =
        applicationContext.getBean("courseTeacherManager", AssignedTeacherManager.class);
  }

  private String mSection;
  private Teacher mTeacher;
  private String mTeacherId;

  public PersistentCourseTeacher() {}

  public PersistentCourseTeacher(final PersistentCourseTeacher pPersistentCourseTeacher) {
    this.mId = pPersistentCourseTeacher.getId();
    this.mSemester = pPersistentCourseTeacher.getSemester();
    this.mTeacher = pPersistentCourseTeacher.getTeacher();
    this.mCourse = pPersistentCourseTeacher.getCourse();
    this.mSection = pPersistentCourseTeacher.getSection();
    this.mLastModified = pPersistentCourseTeacher.getLastModified();
  }

  @Override
  public Teacher getTeacher() {
    return mTeacher == null ? sTeacherManager.get(mTeacherId) : sTeacherManager.validate(mTeacher);
  }

  @Override
  public void setTeacher(Teacher pTeacher) {
    mTeacher = pTeacher;
  }

  @Override
  public String getTeacherId() {
    return mTeacherId;
  }

  @Override
  public void setTeacherId(String pTeacherId) {
    mTeacherId = pTeacherId;
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
  public MutableCourseTeacher edit() {
    return new PersistentCourseTeacher(this);
  }

  @Override
  public void delete() {
    sCourseTeacherManager.delete(this);
  }

  @Override
  public Long create() {
    return sCourseTeacherManager.create(this);
  }

  @Override
  public void update() {
    sCourseTeacherManager.update(this);
  }

}
