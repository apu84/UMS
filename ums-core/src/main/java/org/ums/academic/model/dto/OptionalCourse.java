package org.ums.academic.model.dto;

import org.ums.academic.model.PersistentCourse;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.readOnly.Department;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class OptionalCourse extends PersistentCourse {

  private String mGroupId;
  private String mGroupName;
  private String mPairCourseId;
  private String mCallForApplication;
  private String mTotalApplied;

  public String getGroupId() {
    return mGroupId;
  }

  public void setGroupId(String mGroupId) {
    this.mGroupId = mGroupId;
  }

  public String getGroupName() {
    return mGroupName;
  }

  public void setGroupName(String mGroupName) {
    this.mGroupName = mGroupName;
  }

  public String getPairCourseId() {
    return mPairCourseId;
  }

  public void setmPairCourseId(String mPairCourseId) {
    this.mPairCourseId = mPairCourseId;
  }

  public String getmCallForApplication() {
    return mCallForApplication;
  }

  public void setCallForApplication(String mCallForApplication) {
    this.mCallForApplication = mCallForApplication;
  }

  public String getTotalApplied() {
    return mTotalApplied;
  }

  public void setTotalApplied(String mTotalApplied) {
    this.mTotalApplied = mTotalApplied;
  }

  @Override
  public MutableCourse edit() throws Exception {
    throw new NotImplementedException();
  }
  @Override
  public void commit(boolean update) throws Exception {
    throw new NotImplementedException();
  }
  @Override
  public void delete() throws Exception {
    throw new NotImplementedException();
  }
}
