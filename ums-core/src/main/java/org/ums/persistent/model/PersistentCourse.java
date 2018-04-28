package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.CourseGroup;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.enums.CourseCategory;
import org.ums.enums.CourseType;
import org.ums.manager.*;

public class PersistentCourse implements MutableCourse {
  private static SyllabusManager sSyllabusManager;
  private static CourseGroupManager sCourseGroupManager;
  private static DepartmentManager sDepartmentManager;
  private static CourseManager sCourseManager;
  private static ProgramManager sProgramManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSyllabusManager = applicationContext.getBean("syllabusManager", SyllabusManager.class);
    sCourseGroupManager = applicationContext.getBean("courseGroupManager", CourseGroupManager.class);
    sDepartmentManager = applicationContext.getBean("departmentManager", DepartmentManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sProgramManager = applicationContext.getBean("programManager", ProgramManager.class);
  }

  private String mId;
  private String mNo;
  private String mTitle;
  private float mCrHr;
  private Department mOfferedBy;
  private Department mOfferedTo;
  private Integer mOfferedToProgramId;
  private Program mOfferedToProgram;
  private CourseType mCourseType;
  private CourseCategory mCourseCategory;
  private CourseGroup mCourseGroup;
  private int mViewOrder;
  private int mYear;
  private int mSemester;
  private String mLastModified;
  private String mDepartmentId;
  private String mSyllabusId;
  private int mCourseGroupId;
  private String mPairCourseId;
  private int mTotalApplied;

  public PersistentCourse() {

  }

  public PersistentCourse(final PersistentCourse pPersistentCourse) {
    mId = pPersistentCourse.getId();
    mNo = pPersistentCourse.getNo();
    mTitle = pPersistentCourse.getTitle();
    mCrHr = pPersistentCourse.getCrHr();
    mOfferedBy = pPersistentCourse.getOfferedBy();
    mOfferedToProgram = pPersistentCourse.getOfferedToProgram();
    mCourseType = pPersistentCourse.getCourseType();
    mCourseCategory = pPersistentCourse.getCourseCategory();
    mCourseGroupId = pPersistentCourse.getCourseGroupId();
    mViewOrder = pPersistentCourse.getViewOrder();
    mYear = pPersistentCourse.getYear();
    mSemester = pPersistentCourse.getSemester();
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void setId(String pId) {
    mId = pId;
  }

  @Override
  public String getNo() {
    return mNo;
  }

  @Override
  public void setNo(String pName) {
    mNo = pName;
  }

  @Override
  public String getTitle() {
    return mTitle;
  }

  @Override
  public void setTitle(String pTitle) {
    mTitle = pTitle;
  }

  @Override
  public float getCrHr() {
    return mCrHr;
  }

  @Override
  public void setCrHr(float pCrHr) {
    mCrHr = pCrHr;
  }

  @Override
  public Department getOfferedBy() {
    return mOfferedBy == null && !StringUtils.isEmpty(mDepartmentId) ? sDepartmentManager.get(mDepartmentId)
        : mOfferedBy;
  }

  @Override
  public Department getOfferedToDepartment() {
    return sProgramManager.get(mOfferedToProgramId).getDepartment();
  }

  @Override
  public Program getOfferedToProgram() {
    return sProgramManager.get(mOfferedToProgramId);
  }

  @Override
  public Integer getOfferedToProgramId() {
    return mOfferedToProgramId;
  }

  @Override
  public void setOfferedBy(Department pDepartment) {
    mOfferedBy = pDepartment;
  }

  @Override
  public void setOfferedToDepartment(Department pDepartment) {
    mOfferedTo = pDepartment;
  }

  @Override
  public void setOfferedToProgram(Program pProgram) {
    mOfferedToProgram = pProgram;
  }

  @Override
  public void setOfferedToProgramId(Integer pProgramId) {
    mOfferedToProgramId = pProgramId;
  }

  @Override
  public Integer getYear() {
    return mYear;
  }

  @Override
  public void setYear(Integer pYear) {
    mYear = pYear;
  }

  @Override
  public Integer getSemester() {
    return mSemester;
  }

  @Override
  public void setSemester(Integer pSemester) {
    mSemester = pSemester;
  }

  @Override
  public Integer getViewOrder() {
    return mViewOrder;
  }

  @Override
  public void setViewOrder(Integer pViewOrder) {
    mViewOrder = pViewOrder;
  }

  @Override
  public void setCourseGroupId(Integer pGroupId) {
    mCourseGroupId = pGroupId;
  }

  @Override
  public CourseGroup getCourseGroup(final Integer pGroupId) {
    return sCourseGroupManager.get(pGroupId);
  }

  @Override
  public void setCourseGroup(CourseGroup pCourseGroup) {
    mCourseGroup = pCourseGroup;
  }

  @Override
  public CourseType getCourseType() {
    return mCourseType;
  }

  @Override
  public void setCourseType(CourseType pCourseType) {
    mCourseType = pCourseType;
  }

  @Override
  public CourseCategory getCourseCategory() {
    return mCourseCategory;
  }

  @Override
  public void setCourseCategory(CourseCategory pCourseCategory) {
    mCourseCategory = pCourseCategory;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public String getOfferedDepartmentId() {
    return mDepartmentId;
  }

  public void setOfferedDepartmentId(String pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  @Override
  public Integer getCourseGroupId() {
    return mCourseGroupId;
  }

  public void setCourseGroupId(int pCourseGroupId) {
    mCourseGroupId = pCourseGroupId;
  }

  public String getPairCourseId() {
    return mPairCourseId;
  }

  public void setPairCourseId(String mPairCourseId) {
    this.mPairCourseId = mPairCourseId;
  }

  @Override
  public Integer getTotalApplied() {
    return mTotalApplied;
  }

  @Override
  public void setTotalApplied(Integer mTotalApplied) {
    this.mTotalApplied = mTotalApplied;
  }

  @Override
  public String create() {
    return sCourseManager.create(this);
  }

  @Override
  public void update() {
    sCourseManager.update(this);
  }

  @Override
  public MutableCourse edit() {
    return new PersistentCourse(this);
  }

  @Override
  public void delete() {
    sCourseManager.delete(this);
  }
}
