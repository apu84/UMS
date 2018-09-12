package org.ums.persistent.model.optCourse;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseOffer;
import org.ums.manager.CourseManager;
import org.ums.manager.DepartmentManager;
import org.ums.manager.optCourse.OptCourseOfferManager;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class PersistentOptCourseOffer implements MutableOptCourseOffer {
  private static DepartmentManager sDepartmentManager;
  private static CourseManager sCourseManager;
  private static OptCourseOfferManager sOptCourseOfferManager;
  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sOptCourseOfferManager = applicationContext.getBean("optCourseOfferManager", OptCourseOfferManager.class);
  }
  private Long mId;
  private Integer mSemesterId;
  private String mDeptId;
  private Department mDepartment;
  private Integer mProgramId;
  private String mProgramName;
  private Integer mYear;
  private Integer mSemester;
  private String mCourseId;
  private Course mCourse;
  private Integer mCallForApplication;
  private Integer mApproved;
  private Integer mTotalApplied;

  public PersistentOptCourseOffer() {

  }

  public PersistentOptCourseOffer(PersistentOptCourseOffer pPersistentOptCourseOffer) {
    mId = pPersistentOptCourseOffer.getId();
    mSemesterId = pPersistentOptCourseOffer.getSemesterId();
    mDeptId = pPersistentOptCourseOffer.getDepartmentId();
    mDepartment = pPersistentOptCourseOffer.getDepartment();
    mProgramId = pPersistentOptCourseOffer.getProgramId();
    mProgramName = pPersistentOptCourseOffer.getProgramName();
    mYear = pPersistentOptCourseOffer.getYear();
    mSemester = pPersistentOptCourseOffer.getSemester();
    mCourseId = pPersistentOptCourseOffer.getCourseId();
    mCourse = pPersistentOptCourseOffer.getCourses();
    mCallForApplication = pPersistentOptCourseOffer.getCallForApplication();
    mApproved = pPersistentOptCourseOffer.getApproved();
    mTotalApplied = pPersistentOptCourseOffer.getTotal();
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
  public void setDepartmentId(String pDepartmentId) {
    mDeptId = pDepartmentId;

  }

  @Override
  public void setDepartment(Department pDepartment) {
    mDepartment = pDepartment;

  }

  @Override
  public void setProgramId(Integer pProgramId) {
    mProgramId = pProgramId;

  }

  @Override
  public void setProgramName(String pProgramName) {
    mProgramName = pProgramName;

  }

  @Override
  public void setYear(Integer pYear) {
    mYear = pYear;

  }

  @Override
  public void setSemester(Integer pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setCourseId(String pCourseId) {
    mCourseId = pCourseId;

  }

  @Override
  public void setCourses(Course pCourses) {
    mCourse = pCourses;
  }

  @Override
  public void setCallForApplication(Integer pCallForApplication) {
    mCallForApplication = pCallForApplication;

  }

  @Override
  public void setApproved(Integer pApproved) {
    mApproved = pApproved;
  }

  @Override
  public void setTotal(Integer pTotal) {
    mTotalApplied = pTotal;
  }

  @Override
  public Long create() {
    return sOptCourseOfferManager.create(this);
  }

  @Override
  public void update() {
    sOptCourseOfferManager.update(this);
  }

  @Override
  public void delete() {
    sOptCourseOfferManager.delete(this);
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
  public String getDepartmentId() {
    return mDeptId;
  }

  @Override
  public Department getDepartment() {
    return mDepartment == null ? sDepartmentManager.get(mDeptId) : sDepartmentManager.validate(mDepartment);
  }

  @Override
  public Integer getProgramId() {
    return mProgramId;
  }

  @Override
  public String getProgramName() {
    return mProgramName;
  }

  @Override
  public Integer getYear() {
    return mYear;
  }

  @Override
  public Integer getSemester() {
    return mSemester;
  }

  @Override
  public String getCourseId() {
    return mCourseId;
  }

  @Override
  public Course getCourses() {
    return mCourse == null ? sCourseManager.get(mCourseId) : sCourseManager.validate(mCourse);
  }

  @Override
  public Integer getCallForApplication() {
    return mCallForApplication;
  }

  @Override
  public Integer getApproved() {
    return mApproved;
  }

  @Override
  public Integer getTotal() {
    return mTotalApplied;
  }

  @Override
  public MutableOptCourseOffer edit() {
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
