package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableCourseGroup;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.manager.CourseGroupManager;
import org.ums.manager.SyllabusManager;

public class PersistentCourseGroup implements MutableCourseGroup {
  private static SyllabusManager sSyllabusManager;
  private static CourseGroupManager sCourseGroupManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSyllabusManager = applicationContext.getBean("syllabusManager", SyllabusManager.class);
    sCourseGroupManager = applicationContext.getBean("courseGroupManager", CourseGroupManager.class);
  }

  private int mId;
  private String mName;
  private String mLastModified;

  public PersistentCourseGroup() {

  }

  public PersistentCourseGroup(final PersistentCourseGroup pPersistentCourseGroup) {
    this.mId = pPersistentCourseGroup.getId();
    this.mName = pPersistentCourseGroup.getName();
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
  public String getName() {
    return mName;
  }

  @Override
  public void setName(String pName) {
    mName = pName;
  }

  @Override
  public MutableCourseGroup edit() {
    return new PersistentCourseGroup(this);
  }

  @Override
  public Integer create() {
    return sCourseGroupManager.create(this);
  }

  @Override
  public void update() {
    sCourseGroupManager.update(this);
  }

  @Override
  public void delete() {
    sCourseGroupManager.delete(this);
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
