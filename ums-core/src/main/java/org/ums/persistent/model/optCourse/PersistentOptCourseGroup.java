package org.ums.persistent.model.optCourse;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseGroup;
import org.ums.manager.optCourse.OptCourseGroupManager;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class PersistentOptCourseGroup implements MutableOptCourseGroup {
  private static OptCourseGroupManager sOptCourseGroupManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sOptCourseGroupManager = applicationContext.getBean("optCourseGroupManager", OptCourseGroupManager.class);
  }
  private Long mId;
  private Integer mGroupId;
  private String mGroupName;

  public PersistentOptCourseGroup() {

  }

  public PersistentOptCourseGroup(PersistentOptCourseGroup pPersistentOptCourseGroup) {
    mId = pPersistentOptCourseGroup.getId();
    mGroupId = pPersistentOptCourseGroup.getOptGroupId();
    mGroupName = pPersistentOptCourseGroup.getOptGroupName();
  }

  @Override
  public void setOptGroupId(Integer pGroupId) {
    mGroupId = pGroupId;
  }

  @Override
  public void setOptGroupName(String pGroupName) {
    mGroupName = pGroupName;
  }

  @Override
  public MutableOptCourseGroup edit() {
    return null;
  }

  @Override
  public Long create() {
    return sOptCourseGroupManager.create(this);
  }

  @Override
  public void update() {
    sOptCourseGroupManager.update(this);

  }

  @Override
  public void delete() {
    sOptCourseGroupManager.delete(this);

  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public Integer getOptGroupId() {
    return mGroupId;
  }

  @Override
  public String getOptGroupName() {
    return mGroupName;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
