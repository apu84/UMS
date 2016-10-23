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
    sCourseGroupManager =
        applicationContext.getBean("courseGroupManager", CourseGroupManager.class);
  }

  private int mId;
  private String mName;
  private Syllabus mSyllabus;
  private String mSyllabusId;
  private String mLastModified;

  public PersistentCourseGroup() {

  }

  public PersistentCourseGroup(final PersistentCourseGroup pPersistentCourseGroup) throws Exception {
    this.mId = pPersistentCourseGroup.getId();
    this.mName = pPersistentCourseGroup.getName();
    this.mSyllabus = pPersistentCourseGroup.getSyllabus();
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
  public Syllabus getSyllabus() throws Exception {
    return mSyllabus == null ? sSyllabusManager.get(mSyllabusId) : sSyllabusManager
        .validate(mSyllabus);
  }

  @Override
  public void setSyllabus(Syllabus pSyllabus) {
    mSyllabus = pSyllabus;
  }

  @Override
  public String getSyllabusId() {
    return mSyllabusId;
  }

  public void setSyllabusId(String pSyllabusId) {
    mSyllabusId = pSyllabusId;
  }

  @Override
  public MutableCourseGroup edit() throws Exception {
    return new PersistentCourseGroup(this);
  }

  @Override
  public void commit(boolean update) throws Exception {
    if(update) {
      sCourseGroupManager.update(this);
    }
    else {
      sCourseGroupManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
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
