package org.ums.persistent.dao;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableAbsLateComingInfo;
import org.ums.manager.AbsLateComingInfoManager;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
public class PersistentAbsLateComingInfo implements MutableAbsLateComingInfo {
  private static StudentManager sStudentManager;
  private static SemesterManager sSemesterManager;
  private static CourseManager sCourseManager;
  private static AbsLateComingInfoManager sAbsLateComingInfoManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sAbsLateComingInfoManager = applicationContext.getBean("absLateComingInfoManager", AbsLateComingInfoManager.class);
  }
  private Long mId;
  private Integer mSemesterId;
  private Integer mExamType;
  private Integer mPresentType;
  private String mPresentInfo;
  private String mRemarks;
  private String mEmployeeId;
  private String mEmployeeName;
  private String mEmployeeType;
  private String mDeptId;
  private String mDeptName;
  private Long mInvigilatorRoomId;
  private String mInvigilatorRoomName;
  private String mExamDate;
  private String mArrivalTime;

  public PersistentAbsLateComingInfo() {

  }

  public PersistentAbsLateComingInfo(PersistentAbsLateComingInfo pPersistentAbsLateComingInfo) {
    mId = pPersistentAbsLateComingInfo.getId();
    mSemesterId = pPersistentAbsLateComingInfo.getSemesterId();
    mExamType = pPersistentAbsLateComingInfo.getExamType();
    mPresentType = pPersistentAbsLateComingInfo.getPresentType();
    mPresentInfo = pPersistentAbsLateComingInfo.getPresentInfo();
    mRemarks = pPersistentAbsLateComingInfo.getRemarks();
    mEmployeeId = pPersistentAbsLateComingInfo.getEmployeeId();
    mEmployeeName = pPersistentAbsLateComingInfo.getEmployeeName();
    mEmployeeType = pPersistentAbsLateComingInfo.getEmployeeType();
    mDeptId = pPersistentAbsLateComingInfo.getDeptId();
    mDeptName = pPersistentAbsLateComingInfo.getDeptName();
    mInvigilatorRoomId = pPersistentAbsLateComingInfo.getInvigilatorRoomId();
    mInvigilatorRoomName = pPersistentAbsLateComingInfo.getInvigilatorRoomName();
    mExamDate = pPersistentAbsLateComingInfo.getExamDate();
    mArrivalTime = pPersistentAbsLateComingInfo.getArrivalTime();
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setExamType(Integer pSetExamType) {
    mExamType = pSetExamType;
  }

  @Override
  public void setPresentType(Integer pPresentType) {
    mPresentType = pPresentType;
  }

  @Override
  public void setPresentInfo(String pPresentInfo) {
    mPresentInfo = pPresentInfo;

  }

  @Override
  public void setRemarks(String pRemarks) {
    mRemarks = pRemarks;

  }

  @Override
  public void setEmployeeId(String pEmployeeId) {
    mEmployeeId = pEmployeeId;
  }

  @Override
  public void setEmployeeName(String pEmployeeName) {
    mEmployeeName = pEmployeeName;
  }

  @Override
  public void setEmployeeType(String pEmployeeType) {
    mEmployeeType = pEmployeeType;
  }

  @Override
  public void setDeptId(String pDeptId) {
    mDeptId = pDeptId;
  }

  @Override
  public void setDeptName(String pDeptName) {
    mDeptName = pDeptName;
  }

  @Override
  public void setInvigilatorRoomId(Long pInvigilatorRoomId) {
    mInvigilatorRoomId = pInvigilatorRoomId;
  }

  @Override
  public void setInvigilatorRoomName(String pInvigilatorRoomName) {
    mInvigilatorRoomName = pInvigilatorRoomName;
  }

  @Override
  public void setExamDate(String pExamDate) {
    mExamDate = pExamDate;
  }

  @Override
  public void setArrivalTime(String pArrivalTime) {
    mArrivalTime = pArrivalTime;
  }

  @Override
  public Long create() {
    return sAbsLateComingInfoManager.create(this);
  }

  @Override
  public void update() {
    sAbsLateComingInfoManager.update(this);

  }

  @Override
  public void delete() {
    sAbsLateComingInfoManager.delete(this);

  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public Integer getExamType() {
    return mExamType;
  }

  @Override
  public Integer getPresentType() {
    return mPresentType;
  }

  @Override
  public String getPresentInfo() {
    return mPresentInfo;
  }

  @Override
  public String getRemarks() {
    return mRemarks;
  }

  @Override
  public String getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public String getEmployeeName() {
    return mEmployeeName;
  }

  @Override
  public String getEmployeeType() {
    return mEmployeeType;
  }

  @Override
  public String getDeptId() {
    return mDeptId;
  }

  @Override
  public String getDeptName() {
    return mDeptName;
  }

  @Override
  public Long getInvigilatorRoomId() {
    return mInvigilatorRoomId;
  }

  @Override
  public String getInvigilatorRoomName() {
    return mInvigilatorRoomName;
  }

  @Override
  public String getExamDate() {
    return mExamDate;
  }

  @Override
  public String getArrivalTime() {
    return mArrivalTime;
  }

  @Override
  public MutableAbsLateComingInfo edit() {
    return new PersistentAbsLateComingInfo(this);
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
