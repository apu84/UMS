package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.MutableExpelledInformation;
import org.ums.manager.CourseManager;
import org.ums.manager.ExpelledInformationManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

/**
 * Created by Monjur-E-Morshed on 5/27/2018.
 */
public class PersistentExpelledInformation implements MutableExpelledInformation {
  private static StudentManager sStudentManager;
  private static SemesterManager sSemesterManager;
  private static CourseManager sCourseManager;
  private static ExpelledInformationManager sExpelledInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sExpelledInformationManager =
        applicationContext.getBean("expelledInformationManager", ExpelledInformationManager.class);
  }
  private Long mId;
  private Integer mSemesterId;
  private String mSemesterName;
  private String mStudentId;
  private String mStudentName;
  private String mCourseId;
  private String mCourseNo;
  private String mCourseTitle;
  private Integer mExamType;
  private String mExamTypeName;
  private String mProgramName;
  private Department mDepartment;
  private String mExpelledReason;
  private String mInsertionDate;
  private Integer mStatus;
  private String mExamDate;
  private String mDeptId;
  private String mDeptName;
  private Integer mRegType;

  public PersistentExpelledInformation() {

  }

  public PersistentExpelledInformation(final PersistentExpelledInformation pPersistentExpelledInformation) {
    mId = pPersistentExpelledInformation.getId();
    mStudentId = pPersistentExpelledInformation.getStudentId();
    mStudentName = pPersistentExpelledInformation.getStudentName();
    mSemesterId = pPersistentExpelledInformation.getSemesterId();
    mSemesterName = pPersistentExpelledInformation.getSemesterName();
    mProgramName = pPersistentExpelledInformation.getProgramName();
    mCourseId = pPersistentExpelledInformation.getCourseId();
    mCourseNo = pPersistentExpelledInformation.getCourseNo();
    mCourseTitle = pPersistentExpelledInformation.getCourseTitle();
    mExamType = pPersistentExpelledInformation.getExamType();
    mExamTypeName = pPersistentExpelledInformation.getExamTypeName();
    mDepartment = pPersistentExpelledInformation.getDepartment();
    mExpelledReason = pPersistentExpelledInformation.getExpelledReason();
    mStatus = pPersistentExpelledInformation.getStatus();
    mInsertionDate = pPersistentExpelledInformation.getInsertionDate();
    mExamDate = pPersistentExpelledInformation.getExamDate();
    mDeptId = pPersistentExpelledInformation.getDeptId();
    mDeptName = pPersistentExpelledInformation.getDeptName();
    mRegType = pPersistentExpelledInformation.getRegType();
  }

  @Override
  public void setStudentId(String pStudentId) {
    mStudentId = pStudentId;

  }

  @Override
  public void setStudentName(String pStudentName) {
    mStudentName = pStudentName;
  }

  @Override
  public void setCourseId(String pCourseId) {
    mCourseId = pCourseId;
  }

  @Override
  public void setCourseNo(String pCourseNo) {
    mCourseNo = pCourseNo;

  }

  @Override
  public void setCourseTitle(String pCourseTitle) {
    mCourseTitle = pCourseTitle;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setSemesterName(String pSemesterName) {
    mSemesterName = pSemesterName;
  }

  @Override
  public void setExamType(Integer pExamType) {
    mExamType = pExamType;
  }

  @Override
  public void setRegType(Integer pRegType) {
    mRegType = pRegType;
  }

  @Override
  public void setExamTypeName(String pExamTypeName) {
    mExamTypeName = pExamTypeName;
  }

  @Override
  public void setDepartment(Department pDepartment) {
    mDepartment = pDepartment;
  }

  @Override
  public void setProgramName(String pProgramName) {
    mProgramName = pProgramName;
  }

  @Override
  public void setExpelledReason(String pExpelledReason) {
    mExpelledReason = pExpelledReason;
  }

  @Override
  public void setInsertionDate(String pInsertionDate) {
    mInsertionDate = pInsertionDate;
  }

  @Override
  public void setStatus(Integer pStatus) {
    mStatus = pStatus;
  }

  @Override
  public void setExamDate(String pExamDate) {
    mExamDate = pExamDate;
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
  public String getStudentId() {
    return mStudentId;
  }

  @Override
  public String getStudentName() {
    return mStudentName;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public String getSemesterName() {
    return mSemesterName;
  }

  @Override
  public String getCourseId() {
    return mCourseId;
  }

  @Override
  public String getCourseNo() {
    return mCourseNo;
  }

  @Override
  public String getCourseTitle() {
    return mCourseTitle;
  }

  @Override
  public Integer getExamType() {
    return mExamType;
  }

  @Override
  public Integer getRegType() {
    return mRegType;
  }

  @Override
  public String getExamTypeName() {
    return mExamTypeName;
  }

  @Override
  public Department getDepartment() {
    return mDepartment;
  }

  @Override
  public String getProgramName() {
    return mProgramName;
  }

  @Override
  public String getExpelledReason() {
    return mExpelledReason;
  }

  @Override
  public String getInsertionDate() {
    return mInsertionDate;
  }

  @Override
  public Integer getStatus() {
    return mStatus;
  }

  @Override
  public String getExamDate() {
    return mExamDate;
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
  public MutableExpelledInformation edit() {
    return new PersistentExpelledInformation(this);
  }

  @Override
  public Long create() {
    return sExpelledInformationManager.create(this);
  }

  @Override
  public void update() {
    sExpelledInformationManager.update(this);
  }

  @Override
  public void delete() {
    sExpelledInformationManager.delete(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
