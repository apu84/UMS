package org.ums.persistent.model.optCourse;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroup;
import org.ums.manager.DepartmentManager;
import org.ums.manager.optCourse.OptOfferedGroupManager;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class PersistentOptOfferedGroup implements MutableOptOfferedGroup {
  private static DepartmentManager sDepartmentManager;
  private static OptOfferedGroupManager sOptOfferedGroupManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
    sOptOfferedGroupManager = applicationContext.getBean("optOfferedGroupManager", OptOfferedGroupManager.class);
  }
  private Long mId;
  private String mGroupName;
  private Integer mSemesterId;
  private Department mDepartment;
  private String mDepartmentId;
  private Integer mProgramId;
  private String mProgramName;
  private Integer mIsMandatory;
  private Integer mYear;
  private Integer mSemester;

  public PersistentOptOfferedGroup() {

  }

  public PersistentOptOfferedGroup(PersistentOptOfferedGroup pPersistentOptOfferedGroup) {
    mId = pPersistentOptOfferedGroup.getId();
    mGroupName = pPersistentOptOfferedGroup.getGroupName();
    mSemesterId = pPersistentOptOfferedGroup.getSemesterId();
    mDepartmentId = pPersistentOptOfferedGroup.getDepartmentId();
    mDepartment = pPersistentOptOfferedGroup.getDepartment();
    mProgramId = pPersistentOptOfferedGroup.getProgramId();
    mProgramName = pPersistentOptOfferedGroup.getGroupName();
    mIsMandatory = pPersistentOptOfferedGroup.getIsMandatory();
    mYear = pPersistentOptOfferedGroup.getYear();
    mSemester = pPersistentOptOfferedGroup.getSemester();
  }

  @Override
  public Long create() {
    return sOptOfferedGroupManager.create(this);
  }

  @Override
  public void update() {
    sOptOfferedGroupManager.update(this);
  }

  @Override
  public void delete() {
    sOptOfferedGroupManager.delete(this);
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setGroupName(String pGroupName) {
    mGroupName = pGroupName;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setDepartmentId(String pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  @Override
  public void setDepartment(Department pDepartment) {
    mDepartment = pDepartment;
  }

  @Override
  public void setProgramId(Integer pProgramId) {
    mProgramId = pProgramId;
  }

  @Override
  public void setProgramName(String pProgramName) {
    mProgramName = pProgramName;
  }

  @Override
  public void setIsMandatory(Integer pIsMandatory) {
    mIsMandatory = pIsMandatory;

  }

  @Override
  public void setYear(Integer pYear) {
    mYear = pYear;
  }

  @Override
  public void setSemester(Integer pSemester) {
    mSemester = pSemester;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getGroupName() {
    return mGroupName;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public String getDepartmentId() {
    return mDepartmentId;
  }

  @Override
  public Department getDepartment() {
    return mDepartment == null ? sDepartmentManager.get(mDepartmentId) : sDepartmentManager.validate(mDepartment);
  }

  @Override
  public Integer getProgramId() {
    return mProgramId;
  }

  @Override
  public String getProgramName() {
    return mProgramName;
  }

  @Override
  public Integer getIsMandatory() {
    return mIsMandatory;
  }

  @Override
  public Integer getYear() {
    return mYear;
  }

  @Override
  public Integer getSemester() {
    return mSemester;
  }

  @Override
  public MutableOptOfferedGroup edit() {
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
