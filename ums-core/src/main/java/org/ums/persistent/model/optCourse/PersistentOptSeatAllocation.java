package org.ums.persistent.model.optCourse;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.optCourse.MutableOptSeatAllocation;
import org.ums.manager.optCourse.OptSeatAllocationManager;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public class PersistentOptSeatAllocation implements MutableOptSeatAllocation {
  private static OptSeatAllocationManager sOptSeatAllocationManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sOptSeatAllocationManager = applicationContext.getBean("optSeatAllocationManager", OptSeatAllocationManager.class);
  }
  private Long mId;
  private Long mGroupId;
  private String mDepartmentId;
  private Integer mSeat;

  public PersistentOptSeatAllocation() {

  }

  public PersistentOptSeatAllocation(PersistentOptSeatAllocation pPersistentOptSeatAllocation) {
    mId = pPersistentOptSeatAllocation.getId();
    mGroupId = pPersistentOptSeatAllocation.getGroupID();
    mDepartmentId = pPersistentOptSeatAllocation.getDepartmentId();
    mSeat = pPersistentOptSeatAllocation.getSeatNumber();
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setGroupID(Long pGroupId) {
    mGroupId = pGroupId;
  }

  @Override
  public void setDepartmentId(String pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  @Override
  public void setSeatNumber(Integer pSeatNumber) {
    mSeat = pSeatNumber;
  }

  @Override
  public Long create() {
    return sOptSeatAllocationManager.create(this);
  }

  @Override
  public void update() {
    sOptSeatAllocationManager.update(this);
  }

  @Override
  public void delete() {
    sOptSeatAllocationManager.delete(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public Long getGroupID() {
    return mGroupId;
  }

  @Override
  public String getDepartmentId() {
    return mDepartmentId;
  }

  @Override
  public Integer getSeatNumber() {
    return mSeat;
  }

  @Override
  public MutableOptSeatAllocation edit() {
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
