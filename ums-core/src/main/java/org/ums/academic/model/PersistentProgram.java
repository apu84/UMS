package org.ums.academic.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.*;
import org.ums.manager.ContentManager;
import org.ums.util.Constants;

public class PersistentProgram implements MutableProgram {

  private static ContentManager<Department, MutableDepartment, Integer> sDepartmentManger;
  private static ContentManager<Program, MutableProgram, Integer> sProgramManager;
  private static ContentManager<ProgramType, MutableProgramType, Integer> sProgramTypeManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDepartmentManger = (ContentManager) applicationContext.getBean("departmentManager");
    sProgramManager = (ContentManager) applicationContext.getBean("programManager");
    sProgramTypeManager = (ContentManager) applicationContext.getBean("programTypeManager");
  }

  private int mId;
  private String mShortName;
  private String mLongName;
  private Department mDepartment;
  private ProgramType mProgramType;
  private String mLastModified;

  private int mDepartmentId;
  private int mProgramTypeId;

  public PersistentProgram() {

  }

  public PersistentProgram(final PersistentProgram pPersistentProgram) throws Exception {
    mId = pPersistentProgram.getId();
    mShortName = pPersistentProgram.getShortName();
    mLongName = pPersistentProgram.getLongName();
    mDepartment = pPersistentProgram.getDepartment();
    mProgramType = pPersistentProgram.getProgramType();
  }

  public Integer getId() {
    return mId;
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  public Department getDepartment() throws Exception {
    return mDepartment == null ? sDepartmentManger.get(mDepartmentId) : mDepartment;
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
  public ProgramType getProgramType() throws Exception {
    return mProgramType == null ? sProgramTypeManager.get(mProgramTypeId) : mProgramType;
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
  public int getDepartmentId() {
    return mDepartmentId;
  }

  public void setDepartmentId(int pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  public void delete() throws Exception {
    sProgramManager.delete(this);
  }

  public void commit(final boolean update) throws Exception {
    if (update) {
      sProgramManager.update(this);
    } else {
      sProgramManager.create(this);
    }
  }

  public MutableProgram edit() throws Exception {
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
