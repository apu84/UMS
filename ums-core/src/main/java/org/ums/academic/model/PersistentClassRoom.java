package org.ums.academic.model;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableClassRoom;
import org.ums.domain.model.mutable.MutableDepartment;
import org.ums.domain.model.readOnly.ClassRoom;
import org.ums.domain.model.readOnly.Department;
import org.ums.enums.ClassRoomType;
import org.ums.manager.ContentManager;

public class PersistentClassRoom  implements MutableClassRoom {
  private static ContentManager<ClassRoom, MutableClassRoom, Integer> sClassRoomManager;
  private static ContentManager<Department, MutableDepartment, String> sDepartmentManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sClassRoomManager = (ContentManager<ClassRoom, MutableClassRoom, Integer>)applicationContext.getBean("classRoomManager");
    sDepartmentManager = (ContentManager<Department, MutableDepartment, String>)applicationContext.getBean("departmentManager");
  }

  private int mId;
  private String mRoomNo;
  private String mDescription;
  private int       mTotalRow;
  private int mTotalColumn;
  private int mCapacity;
  private String mDeptId;
  private ClassRoomType mRoomType;
  private Department mDept;
  private boolean mExamSeatPlan;
  private String mLastModified;

  public PersistentClassRoom() {
  }

  public PersistentClassRoom(final PersistentClassRoom pOriginal) throws Exception {
    mId=pOriginal.getId();
    mRoomNo=pOriginal.getRoomNo();
    mDescription=pOriginal.getDescription();
    mTotalRow=pOriginal.getTotalRow();
    mTotalColumn=pOriginal.getTotalColumn();
    mCapacity=pOriginal.getCapacity();
    mRoomType=pOriginal.getRoomType();
    mDept=pOriginal.getDept();
    mDeptId=pOriginal.getDeptId();
    mExamSeatPlan=pOriginal.isExamSeatPlan();
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
  public void setRoomNo(String pRoomNo) {
    mRoomNo = pRoomNo;
  }

  @Override
  public void setDescription(String pDescription) {
    mDescription=pDescription;
  }

  @Override
  public void setTotalRow(int pTotalRow) {
    mTotalRow=pTotalRow;
  }

  @Override
  public void setTotalColumn(int pTotalColumn) {
    mTotalColumn=pTotalColumn;
  }

  @Override
  public void setCapacity(int pTotalCapacity) {
    mTotalColumn=pTotalCapacity;
  }

  @Override
  public void setRoomType(ClassRoomType pRoomType) {
    mRoomType=pRoomType;
  }

  @Override
  public void setDeptId(String pDeptId) {
  mDeptId=pDeptId;
  }

  @Override
  public void setDept(Department pDept) {
  mDept = pDept;
  }

  @Override
  public void setExamSeatPlan(boolean pExamSeatPlan) {
    mExamSeatPlan=pExamSeatPlan;
  }

  @Override
  public String getRoomNo() {
    return mRoomNo;
  }

  @Override
  public String getDescription() {
    return mDescription;
  }

  @Override
  public int getTotalRow() {
    return mTotalRow;
  }

  @Override
  public int getTotalColumn() {
    return 0;
  }

  @Override
  public int getCapacity() {
    return mCapacity;
  }

  @Override
  public ClassRoomType getRoomType() {
    return mRoomType;
  }

  @Override
  public String getDeptId() {
    return mDeptId;
  }

  @Override
  public Department getDept() throws Exception {
    return mDept == null && !StringUtils.isEmpty(mDeptId) ? sDepartmentManager.get(mDeptId) : mDept;
  }

  @Override
  public boolean isExamSeatPlan() {
    return mExamSeatPlan;
  }


  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  public void delete() throws Exception {
    sClassRoomManager.delete(this);
  }

  public void commit(final boolean update) throws Exception {
    if (update) {
      sClassRoomManager.update(this);
    } else {
      sClassRoomManager.create(this);
    }
  }

  public MutableClassRoom edit() throws Exception {
    return new PersistentClassRoom(this);
  }
}
