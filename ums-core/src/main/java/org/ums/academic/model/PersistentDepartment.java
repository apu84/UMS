package org.ums.academic.model;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ums.domain.model.Department;
import org.ums.domain.model.MutableDepartment;
import org.ums.manager.ContentManager;
import org.ums.util.Constants;

public class PersistentDepartment implements MutableDepartment {
  private static ContentManager<Department, MutableDepartment, Integer> sDepartmentManager;

  static {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(Constants.SERVICE_CONTEXT);
    sDepartmentManager = (ContentManager) applicationContext.getBean("departmentManager");
  }

  private int mId;
  private String mShortName;
  private String mLongName;
  private int mType;
  private String mLastModified;

  public PersistentDepartment() {

  }

  public PersistentDepartment(final PersistentDepartment pPersistentDepartment) {
    mId = pPersistentDepartment.getId();
    mShortName = pPersistentDepartment.getShortName();
    mLongName = pPersistentDepartment.getLongName();
    mType = pPersistentDepartment.getType();
  }

  @Override
  public void commit(boolean update) throws Exception {
    if (update) {
      sDepartmentManager.update(this);
    } else {
      sDepartmentManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sDepartmentManager.delete(this);
  }

  public Integer getId() {
    return mId;
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  public String getShortName() {
    return mShortName;
  }

  @Override
  public void setShortName(String pShortName) {
    mShortName = pShortName;
  }

  public String getLongName() {
    return mLongName;
  }

  @Override
  public void setLongName(String pLongName) {
    mLongName = pLongName;
  }

  public int getType() {
    return mType;
  }

  @Override
  public void setType(int pType) {
    mType = pType;
  }

  @Override
  public MutableDepartment edit() throws Exception {
    return new PersistentDepartment(this);
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
