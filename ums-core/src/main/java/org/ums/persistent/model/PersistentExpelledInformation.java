package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
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
  private String mStudentId;
  private String mCourseId;
  private String mCourseNo;
  private String mCourseTitle;
  private Integer mExamType;
  private String mExpelledReason;
  private String mInsertionDate;
  private Integer mStatus;
  private String mExamDate;

  public PersistentExpelledInformation() {

  }

  public PersistentExpelledInformation(final PersistentExpelledInformation pPersistentExpelledInformation) {
    mId = pPersistentExpelledInformation.getId();
    mStudentId = pPersistentExpelledInformation.getStudentId();
    mSemesterId = pPersistentExpelledInformation.getSemesterId();
    mCourseId = pPersistentExpelledInformation.getCourseId();
    mCourseNo = pPersistentExpelledInformation.getCourseNo();
    mCourseTitle = pPersistentExpelledInformation.getCourseTitle();
    mExamType = pPersistentExpelledInformation.getExamType();
    mExpelledReason = pPersistentExpelledInformation.getExpelledReason();
    mStatus = pPersistentExpelledInformation.getStatus();
    mInsertionDate = pPersistentExpelledInformation.getInsertionDate();
    mExamDate = pPersistentExpelledInformation.getExamDate();
  }

  @Override
  public void setStudentId(String pStudentId) {
    mStudentId = pStudentId;

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
  public void setExamType(Integer pExamType) {
    mExamType = pExamType;
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
  public String getStudentId() {
    return mStudentId;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
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
