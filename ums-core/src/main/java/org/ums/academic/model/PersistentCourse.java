package org.ums.academic.model;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ums.domain.model.*;
import org.ums.manager.ContentManager;
import org.ums.manager.CourseGroupManager;
import org.ums.util.Constants;

public class PersistentCourse implements MutableCourse {
  private static ContentManager<Syllabus, MutableSyllabus, String> sSyllabusManager;
  private static CourseGroupManager sCourseGroupManager;
  private static ContentManager<Department, MutableDepartment, Integer> sDepartmentManager;
  private static ContentManager<Course, MutableCourse, String> sCourseManager;

  static {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(Constants.SERVICE_CONTEXT);
    sSyllabusManager = (ContentManager) applicationContext.getBean("syllabusManager");
    sCourseGroupManager = (CourseGroupManager) applicationContext.getBean("courseGroupManager");
    sDepartmentManager = (ContentManager) applicationContext.getBean("departmentManager");
    sCourseManager = (ContentManager) applicationContext.getBean("courseManager");
  }

  private String mId;
  private String mNo;
  private String mTitle;
  private float mCrHr;
  private Department mOfferedBy;
  private Course.CourseType mCourseType;
  private Course.CourseCategory mCourseCategory;
  private CourseGroup mCourseGroup;
  private Syllabus mSyllabus;
  private int mViewOrder;
  private int mYear;
  private int mSemester;
  private String mLastModified;
  private int mDepartmentId;
  private String mSyllabusId;
  private int mCourseGroupId;

  public PersistentCourse() {

  }

  public PersistentCourse(final PersistentCourse pPersistentCourse) throws Exception {
    mId = pPersistentCourse.getId();
    mNo = pPersistentCourse.getNo();
    mTitle = pPersistentCourse.getTitle();
    mCrHr = pPersistentCourse.getCrHr();
    mOfferedBy = pPersistentCourse.getOfferedBy();
    mCourseType = pPersistentCourse.getCourseType();
    mCourseCategory = pPersistentCourse.getCourseCategory();
    mSyllabus = pPersistentCourse.getSyllabus();
    mCourseGroup = pPersistentCourse.getCourseGroup(mSyllabus.getId());
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
  public Department getOfferedBy() throws Exception {
    return mOfferedBy == null && mDepartmentId > 0 ? sDepartmentManager.get(mDepartmentId) : mOfferedBy;
  }

  @Override
  public void setOfferedBy(Department pDepartment) {
    mOfferedBy = pDepartment;
  }

  @Override
  public int getYear() {
    return mYear;
  }

  @Override
  public void setYear(int pYear) {
    mYear = pYear;
  }

  @Override
  public int getSemester() {
    return mSemester;
  }

  @Override
  public void setSemester(int pSemester) {
    mSemester = pSemester;
  }

  @Override
  public int getViewOrder() {
    return mViewOrder;
  }

  @Override
  public void setViewOrder(int pViewOrder) {
    mViewOrder = pViewOrder;
  }

  @Override
  public CourseGroup getCourseGroup(final String pSyllabusId) throws Exception {
    return mCourseGroup == null && mCourseGroupId > 0 ? sCourseGroupManager.getBySyllabus(mCourseGroupId, pSyllabusId) : mCourseGroup;
  }

  @Override
  public void setCourseGroup(CourseGroup pCourseGroup) {
    mCourseGroup = pCourseGroup;
  }

  @Override
  public Syllabus getSyllabus() throws Exception {
    return mSyllabus == null ? sSyllabusManager.get(mSyllabusId) : mSyllabus;
  }

  @Override
  public void setSyllabus(Syllabus pSyllabus) {
    mSyllabus = pSyllabus;
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
  public MutableCourse edit() throws Exception {
    return new PersistentCourse(this);
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
  public int getOfferedDepartmentId() {
    return mDepartmentId;
  }

  public void setOfferedDepartmentId(int pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  @Override
  public int getCourseGroupId() {
    return mCourseGroupId;
  }

  public void setCourseGroupId(int pCourseGroupId) {
    mCourseGroupId = pCourseGroupId;
  }

  @Override
  public String getSyllabusId() {
    return mSyllabusId;
  }

  public void setSyllabusId(String pSyllabusId) {
    mSyllabusId = pSyllabusId;
  }

  @Override
  public void commit(boolean update) throws Exception {
    if (update) {
      sCourseManager.update(this);
    } else {
      sCourseManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sCourseManager.delete(this);
  }
}
