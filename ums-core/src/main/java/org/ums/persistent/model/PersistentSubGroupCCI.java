package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SubGroup;
import org.ums.domain.model.immutable.SubGroupCCI;
import org.ums.domain.model.mutable.MutableSubGroupCCI;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.SubGroupCCIManager;

/**
 * Created by My Pc on 7/23/2016.
 */
public class PersistentSubGroupCCI implements MutableSubGroupCCI {

  private static SemesterManager sSemesterManager;
  private static CourseManager sCourseManager;
  private static SubGroupCCIManager sSubGroupCCIManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sSubGroupCCIManager =
        applicationContext.getBean("subGroupCCIManager", SubGroupCCIManager.class);
  }

  private int mId;
  private Semester mSemester;
  private Integer mSemesterId;
  private Integer mSubGroupNo;
  private Integer mTotalStudent;
  private Course mCourse;
  private String mCourseId;
  private String mCourseNo;
  private Integer mCourseYear;
  private Integer mCourseSemester;
  private String mExamDate;
  private String mLastModified;

  public PersistentSubGroupCCI() {

  }

  public PersistentSubGroupCCI(final PersistentSubGroupCCI pPersistentSubGroupCCI) throws Exception {
    mId = pPersistentSubGroupCCI.getId();
    mSemester = pPersistentSubGroupCCI.getSemester();
    mSemesterId = pPersistentSubGroupCCI.getSemesterId();
    mSubGroupNo = pPersistentSubGroupCCI.getSubGroupNo();
    mTotalStudent = pPersistentSubGroupCCI.getTotalStudent();
    mCourse = pPersistentSubGroupCCI.getCourse();
    mCourseId = pPersistentSubGroupCCI.getCourseId();
    mCourseNo = pPersistentSubGroupCCI.getCourseNo();
    mCourseYear = pPersistentSubGroupCCI.getCourseYear();
    mCourseSemester = pPersistentSubGroupCCI.getCourseSemester();
    mExamDate = pPersistentSubGroupCCI.getExamDate();
    mLastModified = pPersistentSubGroupCCI.getLastModified();
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setSubGroupNo(Integer pSubGroupNo) {
    mSubGroupNo = pSubGroupNo;
  }

  @Override
  public void setTotalStudent(Integer pTotalStudent) {
    mTotalStudent = pTotalStudent;
  }

  @Override
  public void setCourse(Course pCourse) {
    mCourse = pCourse;
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
  public void setCourseYear(Integer pCourseYear) {
    mCourseYear = pCourseYear;
  }

  @Override
  public void setCourseSemester(Integer pCourseSemester) {
    mCourseSemester = pCourseSemester;
  }

  @Override
  public void setExamDate(String pExamDate) {
    mExamDate = pExamDate;
  }

  @Override
  public void commit(boolean update) throws Exception {
    if(update) {
      sSubGroupCCIManager.update(this);
    }
    else {
      sSubGroupCCIManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sSubGroupCCIManager.delete(this);
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public Semester getSemester() throws Exception {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager
        .validate(mSemester);
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public Integer getSubGroupNo() {
    return mSubGroupNo;
  }

  @Override
  public Integer getTotalStudent() {
    return mTotalStudent;
  }

  @Override
  public Course getCourse() throws Exception {
    return mCourse == null ? sCourseManager.get(mCourseId) : sCourseManager.validate(mCourse);
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
  public Integer getCourseYear() {
    return mCourseYear;
  }

  @Override
  public Integer getCourseSemester() {
    return mCourseSemester;
  }

  @Override
  public String getExamDate() {
    return mExamDate;
  }

  @Override
  public MutableSubGroupCCI edit() throws Exception {
    return new PersistentSubGroupCCI(this);
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }
}
