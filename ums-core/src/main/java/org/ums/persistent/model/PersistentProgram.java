package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.mutable.MutableProgram;
import org.ums.manager.DepartmentManager;
import org.ums.manager.ProgramManager;
import org.ums.manager.ProgramTypeManager;

public class PersistentProgram implements MutableProgram {

  private static DepartmentManager sDepartmentManger;
  private static ProgramManager sProgramManager;
  private static ProgramTypeManager sProgramTypeManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDepartmentManger = applicationContext.getBean("departmentManager", DepartmentManager.class);
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
    sProgramTypeManager =
        applicationContext.getBean("programTypeManager", ProgramTypeManager.class);
  }

  private int mId;
  private String mShortName;
  private String mLongName;
  private Department mDepartment;
  private Faculty mFaculty;
  private ProgramType mProgramType;
  private String mLastModified;

  private String mDepartmentId;
  private int mFacultyId;
  private int mProgramTypeId;

  public PersistentProgram() {

  }

  public PersistentProgram(final PersistentProgram pPersistentProgram) {
    mId = pPersistentProgram.getId();
    mShortName = pPersistentProgram.getShortName();
    mLongName = pPersistentProgram.getLongName();
    mDepartment = pPersistentProgram.getDepartment();
    mProgramType = pPersistentProgram.getProgramType();
  }

  @Override
  public Faculty getFaculty() {
    return mFaculty;
  }

  @Override
  public int getFacultyId() {
    return mFacultyId;
  }

  @Override
  public void setFaculty(Faculty pFaculty) {
    mFaculty = pFaculty;
  }

  @Override
  public void setFacultyId(int pFacultyId) {
    mFacultyId = pFacultyId;
  }

  public Integer getId() {
    return mId;
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  public Department getDepartment() {
    return mDepartment == null ? sDepartmentManger.get(mDepartmentId) : sDepartmentManger
        .validate(mDepartment);
  }

  @Override
  public void setDepartment(Department pDepartment) {
    mDepartment = pDepartment;
  }

  public String getLongName() {
    return mLongName;
  }

  @Override
  public void setLongName(String pLongName) {
    mLongName = pLongName;
  }

  public String getShortName() {
    return mShortName;
  }

  @Override
  public void setShortName(String pShortName) {
    mShortName = pShortName;
  }

  @Override
  public ProgramType getProgramType() {
    return mProgramType == null ? sProgramTypeManager.get(mProgramTypeId) : sProgramTypeManager
        .validate(mProgramType);
  }

  @Override
  public void setProgramType(ProgramType pProgramType) {
    mProgramType = pProgramType;
  }

  @Override
  public int getProgramTypeId() {
    return mProgramTypeId;
  }

  public void setProgramTypeId(int pProgramTypeId) {
    mProgramTypeId = pProgramTypeId;
  }

  @Override
  public String getDepartmentId() {
    return mDepartmentId;
  }

  public void setDepartmentId(String pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  public void delete() {
    sProgramManager.delete(this);
  }

  @Override
  public Integer create() {
    return sProgramManager.create(this);
  }

  @Override
  public void update() {
    sProgramManager.update(this);
  }

  public MutableProgram edit() {
    return new PersistentProgram(this);
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
