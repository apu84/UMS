package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.EquivalentCourse;
import org.ums.domain.model.mutable.MutableEquivalentCourse;
import org.ums.manager.CourseManager;
import org.ums.manager.EquivalentCourseManager;

public class PersistentEquivalentCourse implements MutableEquivalentCourse {
  private static EquivalentCourseManager sEquivalentCourseManager;
  private static CourseManager sCourseManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sEquivalentCourseManager =
        applicationContext.getBean("equivalentManager", EquivalentCourseManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
  }

  private Long mId;
  private String mOldCourseId;
  private Course mOldCourse;
  private String mNewCourseId;
  private Course mNewCourse;
  private String mLastModified;

  public PersistentEquivalentCourse() {}

  public PersistentEquivalentCourse(final EquivalentCourse pEquivalentCourse) {
    setId(pEquivalentCourse.getId());
    setOldCourseId(pEquivalentCourse.getOldCourseId());
    setNewCourseId(pEquivalentCourse.getNewCourseId());
    setLastModified(pEquivalentCourse.getLastModified());
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sEquivalentCourseManager.update(this);
    }
    else {
      sEquivalentCourseManager.create(this);
    }
  }

  @Override
  public MutableEquivalentCourse edit() {
    return new PersistentEquivalentCourse(this);
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
    sEquivalentCourseManager.delete(this);
  }

  @Override
  public void setOldCourseId(String pOldCourseId) {
    mOldCourseId = pOldCourseId;
  }

  @Override
  public String getOldCourseId() {
    return mOldCourseId;
  }

  @Override
  public void setOldCourse(Course pOldCourse) {
    mOldCourse = pOldCourse;
  }

  @Override
  public Course getOldCourse() {
    return mOldCourse == null ? sCourseManager.get(mOldCourseId) : sCourseManager
        .validate(mOldCourse);
  }

  @Override
  public void setNewCourseId(String pNewCourseId) {
    mNewCourseId = pNewCourseId;
  }

  @Override
  public String getNewCourseId() {
    return mNewCourseId;
  }

  @Override
  public Course getNewCourse() {
    return mNewCourse == null ? sCourseManager.get(mNewCourseId) : sCourseManager
        .validate(mNewCourse);
  }

  @Override
  public void setNewCourse(Course pNewCourse) {
    mNewCourse = pNewCourse;
  }
}
