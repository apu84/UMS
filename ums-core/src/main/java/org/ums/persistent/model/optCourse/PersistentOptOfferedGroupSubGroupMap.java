package org.ums.persistent.model.optCourse;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupSubGroupMap;
import org.ums.manager.DepartmentManager;
import org.ums.manager.optCourse.OptOfferedGroupSubGroupMapManager;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class PersistentOptOfferedGroupSubGroupMap implements MutableOptOfferedGroupSubGroupMap {
  private static DepartmentManager sDepartmentManager;
  private static OptOfferedGroupSubGroupMapManager sOptOfferedGroupSubGroupMapManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
    sOptOfferedGroupSubGroupMapManager =
        applicationContext.getBean("optOfferedGroupSubGroupMapManager", OptOfferedGroupSubGroupMapManager.class);
  }
  private Long mId;
  private Long mGroupId;
  private Long mSubGroupId;
  private String mSubGroupName;

  public PersistentOptOfferedGroupSubGroupMap() {

  }

  public PersistentOptOfferedGroupSubGroupMap(PersistentOptOfferedGroupSubGroupMap pPersistentOptOfferedGroupSubGroupMap) {
    mId = pPersistentOptOfferedGroupSubGroupMap.getId();
    mGroupId = pPersistentOptOfferedGroupSubGroupMap.getGroupId();
    mSubGroupId = pPersistentOptOfferedGroupSubGroupMap.getSubGroupId();
    mSubGroupName = pPersistentOptOfferedGroupSubGroupMap.getSubGroupName();
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
  public void setSubGroupId(Long pSubGroupId) {
    mSubGroupId = pSubGroupId;
  }

  @Override
  public void setSubGroupName(String pSubGroupName) {
    mSubGroupName = pSubGroupName;
  }

  @Override
  public Long create() {
    return sOptOfferedGroupSubGroupMapManager.create(this);
  }

  @Override
  public void update() {
    sOptOfferedGroupSubGroupMapManager.update(this);
  }

  @Override
  public void delete() {
    sOptOfferedGroupSubGroupMapManager.delete(this);
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
  public Long getSubGroupId() {
    return mSubGroupId;
  }

  @Override
  public String getSubGroupName() {
    return mSubGroupName;
  }

  @Override
  public MutableOptOfferedGroupSubGroupMap edit() {
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
