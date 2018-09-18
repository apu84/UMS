package org.ums.persistent.model.optCourse;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedSubGroupCourseMap;
import org.ums.manager.CourseManager;
import org.ums.manager.DepartmentManager;
import org.ums.manager.optCourse.OptOfferedSubGroupCourseMapManager;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class PersistentOptOfferedSubGroupCourseMap implements MutableOptOfferedSubGroupCourseMap {
  private static DepartmentManager sDepartmentManager;
  private static CourseManager sCourseManager;
  private static OptOfferedSubGroupCourseMapManager sOptCourseOfferManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sOptCourseOfferManager = applicationContext.getBean("optCourseOfferManager", OptOfferedSubGroupCourseMapManager.class);
  }
  private Long mId;
  private Long mSubGroupId;
  private String mCourseId;
  private Course mCourse;

  public PersistentOptOfferedSubGroupCourseMap() {

  }

  public PersistentOptOfferedSubGroupCourseMap(PersistentOptOfferedSubGroupCourseMap pPersistentOptCourseOffer) {
    mId = pPersistentOptCourseOffer.getId();
    mSubGroupId=pPersistentOptCourseOffer.getSubGroupId();
    mCourseId = pPersistentOptCourseOffer.getCourseId();
    mCourse = pPersistentOptCourseOffer.getCourses();
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setSubGroupId(Long pSubGroupId) {
    mSubGroupId=pSubGroupId;

  }

  @Override
  public void setCourseId(String pCourseId) {
    mCourseId = pCourseId;

  }

  @Override
  public void setCourses(Course pCourses) {
    mCourse = pCourses;
  }

  @Override
  public Long create() {
    return sOptCourseOfferManager.create(this);
  }

  @Override
  public void update() {
    sOptCourseOfferManager.update(this);
  }

  @Override
  public void delete() {
    sOptCourseOfferManager.delete(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public Long getSubGroupId() {
    return mSubGroupId;
  }

  @Override
  public String getCourseId() {
    return mCourseId;
  }

  @Override
  public Course getCourses() {
    return mCourse == null ? sCourseManager.get(mCourseId) : sCourseManager.validate(mCourse);
  }


  @Override
  public MutableOptOfferedSubGroupCourseMap edit() {
    return null;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
