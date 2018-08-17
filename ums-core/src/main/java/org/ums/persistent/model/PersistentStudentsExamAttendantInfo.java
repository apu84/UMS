package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.MutableExpelledInformation;
import org.ums.domain.model.mutable.MutableStudentsExamAttendantInfo;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;
import org.ums.manager.StudentsExamAttendantInfoManager;

/**
 * Created by Monjur-E-Morshed on 6/9/2018.
 */
public class PersistentStudentsExamAttendantInfo implements MutableStudentsExamAttendantInfo {
  private static StudentManager sStudentManager;
  private static SemesterManager sSemesterManager;
  private static CourseManager sCourseManager;
  private static StudentsExamAttendantInfoManager sStudentsExamAttendantInfoManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sStudentsExamAttendantInfoManager =
        applicationContext.getBean("studentsExamAttendantInfoManager", StudentsExamAttendantInfoManager.class);
  }
  private Long mId;
  private Integer mSemesterId;
  private Integer mProgramId;
  private String mCourseId;
  private String mCourseNo;
  private String mCourseTitle;
  private String mProgramName;
  private String mDeptId;
  private String mDeptName;
  private Integer mExamType;
  private Integer mYear;
  private Integer mSemester;
  private Integer mPresentStudents;
  private Integer mAbsentStudents;
  private Integer mRegisteredStudents;
  private String mExamDate;

  public PersistentStudentsExamAttendantInfo() {

  }

  public PersistentStudentsExamAttendantInfo(
      final PersistentStudentsExamAttendantInfo pPersistentStudentsExamAttendantInfo) {
    mId = pPersistentStudentsExamAttendantInfo.getId();
    mSemesterId = pPersistentStudentsExamAttendantInfo.getSemesterId();
    mProgramId = pPersistentStudentsExamAttendantInfo.getProgramId();
    mYear = pPersistentStudentsExamAttendantInfo.getYear();
    mSemester = pPersistentStudentsExamAttendantInfo.getSemester();
    mExamType = pPersistentStudentsExamAttendantInfo.getExamType();
    mPresentStudents = pPersistentStudentsExamAttendantInfo.getPresentStudents();
    mAbsentStudents = pPersistentStudentsExamAttendantInfo.getAbsentStudents();
    mRegisteredStudents = pPersistentStudentsExamAttendantInfo.getRegisteredStudents();
    mCourseId = pPersistentStudentsExamAttendantInfo.getCourseId();
    mCourseNo = pPersistentStudentsExamAttendantInfo.getCourseNo();
    mCourseTitle = pPersistentStudentsExamAttendantInfo.getCourseTitle();
    mProgramName = pPersistentStudentsExamAttendantInfo.getProgramName();
    mDeptId = pPersistentStudentsExamAttendantInfo.getDeptId();
    mDeptName = pPersistentStudentsExamAttendantInfo.getDeptName();
    mExamDate = pPersistentStudentsExamAttendantInfo.getExamDate();
  }

  @Override
  public void setProgramId(Integer pProgramId) {
    mProgramId = pProgramId;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
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
  public void setExamType(Integer pExamType) {
    mExamType = pExamType;
  }

  @Override
  public void setPresentStudents(Integer pPresentStudents) {
    mPresentStudents = pPresentStudents;
  }

  @Override
  public void setAbsentStudents(Integer pAbsentStudents) {
    mAbsentStudents = pAbsentStudents;
  }

  @Override
  public void setRegisteredStudents(Integer pRegisteredStudents) {
    mRegisteredStudents = pRegisteredStudents;
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
  public void setProgramName(String pProgramName) {
    mProgramName = pProgramName;
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
  public void setExamDate(String pExamDate) {
    mExamDate = pExamDate;
  }

  @Override
  public Long create() {
    return sStudentsExamAttendantInfoManager.create(this);
  }

  @Override
  public void update() {
    sStudentsExamAttendantInfoManager.update(this);
  }

  @Override
  public void delete() {
    sStudentsExamAttendantInfoManager.delete(this);
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public Integer getProgramId() {
    return mProgramId;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
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
  public Integer getExamType() {
    return mExamType;
  }

  @Override
  public Integer getPresentStudents() {
    return mPresentStudents;
  }

  @Override
  public Integer getAbsentStudents() {
    return mAbsentStudents;
  }

  @Override
  public Integer getRegisteredStudents() {
    return mRegisteredStudents;
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
  public String getProgramName() {
    return mProgramName;
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
  public String getExamDate() {
    return mExamDate;
  }

  @Override
  public MutableStudentsExamAttendantInfo edit() {
    return new PersistentStudentsExamAttendantInfo(this);
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
  public void setLastModified(String pLastModified) {

  }
}
