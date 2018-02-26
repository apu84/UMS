package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.manager.*;

/**
 * Created by Rumi on 2/20/2018.
 */
public class PersistentApplicationTES implements MutableApplicationTES {
  private static StudentManager sStudentManager;
  private static SemesterManager sSemesterManager;
  private static CourseManager sCourseManager;
  private static ApplicationTESManager sApplicationTESManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sApplicationTESManager = applicationContext.getBean("applicationTESManager", ApplicationTESManager.class);
  }
  private Long mId;
  private String mApplicationDate;
  private Integer mQuestionID;
  private String mQuestionDetails;
  private String mReviewEligibleCoures;
  private String mSemesterName;
  private String mCourseTitle;
  private String mCourseNo;
  private String mTeacherId;
  private String mSection;
  private String mDeptId;
  private String mDeptShortName;
  private String mFirstName;
  private String mLastName;

  public PersistentApplicationTES() {

  }

  public PersistentApplicationTES(final PersistentApplicationTES persistentApplicationTES) {
    mId = persistentApplicationTES.getId();
    mApplicationDate = persistentApplicationTES.getApplicationDate();
    mQuestionID = persistentApplicationTES.getQuestionId();
    mQuestionDetails = persistentApplicationTES.getQuestionDetails();
    mReviewEligibleCoures = persistentApplicationTES.getReviewEligibleCourses();
    mSemesterName = persistentApplicationTES.getSemesterName();
    mCourseTitle = persistentApplicationTES.getCourseTitle();
    mCourseNo = persistentApplicationTES.getCourseNo();
    mTeacherId = persistentApplicationTES.getTeacherId();
    mSection = persistentApplicationTES.getSection();
    mDeptId = persistentApplicationTES.getDeptId();
    mDeptShortName = persistentApplicationTES.getDeptShortName();
    mFirstName = persistentApplicationTES.getFirstName();
    mLastName = persistentApplicationTES.getLastName();
  }

  @Override
  public void setTeacherId(String pTeacherId) {
    mTeacherId = pTeacherId;
  }

  @Override
  public void setSection(String pSection) {
    mSection = pSection;
  }

  @Override
  public void setDeptId(String pDeptId) {
    mDeptId = pDeptId;
  }

  @Override
  public void setDeptShortName(String pDeptShortName) {
    mDeptShortName = pDeptShortName;
  }

  @Override
  public void setFirstName(String pFirstName) {
    mFirstName = pFirstName;
  }

  @Override
  public void setLastName(String pLastName) {
    mLastName = pLastName;
  }

  @Override
  public String getTeacherId() {
    return mTeacherId;
  }

  @Override
  public String getSection() {
    return mSection;
  }

  @Override
  public String getDeptId() {
    return mDeptId;
  }

  @Override
  public String getDeptShortName() {
    return mDeptShortName;
  }

  @Override
  public String getFirstName() {
    return mFirstName;
  }

  @Override
  public String getLastName() {
    return mLastName;
  }

  @Override
  public void setCourseTitle(String pCoursetitle) {
    mCourseTitle = pCoursetitle;
  }

  @Override
  public void setCourseNo(String pCourseNo) {
    mCourseNo = pCourseNo;
  }

  @Override
  public String getCourseTitle() {
    return mCourseTitle;
  }

  @Override
  public String getCourseNo() {
    return mCourseNo;
  }

  @Override
  public void setReviewEligibleCourses(String pReviewEligibleCourses) {
    mReviewEligibleCoures = pReviewEligibleCourses;
  }

  @Override
  public void setSemesterName(String pSemesterName) {
    mSemesterName = pSemesterName;
  }

  @Override
  public String getReviewEligibleCourses() {
    return mReviewEligibleCoures;
  }

  @Override
  public String getSemesterName() {
    return mSemesterName;
  }

  @Override
  public void setQuestionId(Integer pQuestionId) {
    mQuestionID = pQuestionId;
  }

  @Override
  public void setQuestionDetails(String pQuestionDetails) {
    mQuestionDetails = pQuestionDetails;
  }

  @Override
  public Integer getQuestionId() {
    return mQuestionID;
  }

  @Override
  public String getQuestionDetails() {
    return mQuestionDetails;
  }

  @Override
  public void setApplicationDate(String pApplicationDate) {
    mApplicationDate = pApplicationDate;
  }

  @Override
  public String getApplicationDate() {
    return mApplicationDate;
  }

  @Override
  public MutableApplicationTES edit() {
    return new PersistentApplicationTES(this);
  }

  @Override
  public Long create() {
    return sApplicationTESManager.create(this);
  }

  @Override
  public void update() {
    sApplicationTESManager.update(this);
  }

  @Override
  public void delete() {
    sApplicationTESManager.delete(this);
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
