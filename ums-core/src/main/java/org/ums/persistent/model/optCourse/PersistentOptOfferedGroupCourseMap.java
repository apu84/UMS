package org.ums.persistent.model.optCourse;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupCourseMap;
import org.ums.manager.CourseManager;
import org.ums.manager.DepartmentManager;
import org.ums.manager.optCourse.OptOfferedGroupCourseMapManager;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class PersistentOptOfferedGroupCourseMap implements MutableOptOfferedGroupCourseMap {
  private static DepartmentManager sDepartmentManager;
  private static CourseManager sCourseManager;
  private static OptOfferedGroupCourseMapManager sOptOfferedGroupCourseMapManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sOptOfferedGroupCourseMapManager =
        applicationContext.getBean("optOfferedGroupCourseMapManager", OptOfferedGroupCourseMapManager.class);
  }

  private Long mId;
  private Long mGroupId;
  private String mCourseId;
  private Course mCourses;

  public PersistentOptOfferedGroupCourseMap() {

  }

  public PersistentOptOfferedGroupCourseMap(final PersistentOptOfferedGroupCourseMap pPersistentOptOfferedGroupCourseMap) {
    mId = pPersistentOptOfferedGroupCourseMap.getId();
    mGroupId = pPersistentOptOfferedGroupCourseMap.getGroupId();
    mCourseId = pPersistentOptOfferedGroupCourseMap.getCourseId();
    mCourses = pPersistentOptOfferedGroupCourseMap.getCourses();

  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setGroupId(Long pGroupId) {
    mGroupId = pGroupId;
  }

  @Override
  public void setCourseId(String pCourseId) {
    mCourseId = pCourseId;
  }

  @Override
  public void setCourses(Course pCourses) {
    mCourses = pCourses;

  }

  @Override
  public MutableOptOfferedGroupCourseMap edit() {
    return null;
  }

  @Override
  public Long create() {
    return sOptOfferedGroupCourseMapManager.create(this);
  }

  @Override
  public void update() {
    sOptOfferedGroupCourseMapManager.update(this);
  }

  @Override
  public void delete() {
    sOptOfferedGroupCourseMapManager.delete(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public Long getGroupId() {
    return mGroupId;
  }

  @Override
  public String getCourseId() {
    return mCourseId;
  }

  @Override
  public Course getCourses() {
    return mCourses == null ? sCourseManager.get(mCourseId) : sCourseManager.validate(mCourses);
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
