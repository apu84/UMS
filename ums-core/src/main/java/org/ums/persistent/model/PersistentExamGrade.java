package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.enums.ExamType;
import org.ums.manager.ExamGradeManager;

import java.util.Date;
import java.util.List;

/**
 * Created by ikh on 4/29/2016.
 */
public class PersistentExamGrade implements MutableExamGrade {

  private static ExamGradeManager sExamGradeManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sExamGradeManager = applicationContext.getBean("examGradeManager", ExamGradeManager.class);
  }

  private Integer mId;
  private List<StudentGradeDto> mGradeList;
  private String mExamTypeName;
  private int mExamTypeId;
  private ExamType mExamType;
  private int mSemesterId;
  private String mSemesterName;
  private String mCourseId;
  private String mCourseTitle;
  private String mCourseNo;
  private String mExamDate;
  private String mProgramShortname;
  private Integer mCourseCreditHour;
  private Date mLastSubmissionDate;
  private Integer mTotalStudents;

  public PersistentExamGrade() {

  }

  public PersistentExamGrade(final MutableExamGrade pOriginal) {
    mGradeList = pOriginal.getGradeList();
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  public void save() {

    // sExamRoutineManager.create(this);

  }

  @Override
  public void setExamType(ExamType pExamType) {
    mExamType = pExamType;
  }

  @Override
  public ExamType getExamType() {
    return mExamType;
  }

  @Override
  public void delete() {
    // sExamRoutineManager.delete(this);

  }

  @Override
  public void commit(boolean update) {}

  @Override
  public MutableExamGrade edit() {
    return null;
  }

  @Override
  public void setTotalStudents(Integer pTotalStudents) {
    mTotalStudents = pTotalStudents;
  }

  @Override
  public Integer getTotalStudents() {
    return mTotalStudents;
  }

  @Override
  public void setCourseNo(String pCourseNo) {
    mCourseNo = pCourseNo;
  }

  @Override
  public void setCourseCreditHour(Integer pCourseCreditHour) {
    mCourseCreditHour = pCourseCreditHour;
  }

  @Override
  public void setLastSubmissionDate(Date pLastSubmissionDate) {
    mLastSubmissionDate = pLastSubmissionDate;
  }

  @Override
  public void setExamDate(String pExamDate) {
    mExamDate = pExamDate;
  }

  @Override
  public void setProgramShortName(String pProgramShortName) {
    mProgramShortname = pProgramShortName;
  }

  @Override
  public String getCourseNo() {
    return mCourseNo;
  }

  @Override
  public Integer getCourseCreditHour() {
    return mCourseCreditHour;
  }

  @Override
  public Date getLastSubmissionDate() {
    return mLastSubmissionDate;
  }

  @Override
  public String getExamDate() {
    return mExamDate;
  }

  @Override
  public String getProgramShortName() {
    return mProgramShortname;
  }

  @Override
  public void setGradeList(List<StudentGradeDto> pGradeList) {
    mGradeList = pGradeList;
  }

  @Override
  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void getSemesterName(String pSemesterName) {
    mSemesterName = pSemesterName;
  }

  @Override
  public void setExamTypeId(int pExamTypeId) {
    mExamTypeId = pExamTypeId;
  }

  @Override
  public void setExamTypeName(String pExamTypeName) {
    mExamTypeName = pExamTypeName;
  }

  @Override
  public void setCourseId(String pCourseId) {
    mCourseId = pCourseId;
  }

  @Override
  public void setCourseTitle(String pCourseTitle) {
    mCourseTitle = pCourseTitle;
  }

  @Override
  public List<StudentGradeDto> getGradeList() {
    return mGradeList;
  }

  @Override
  public int getSemesterId() {
    return mSemesterId;
  }

  @Override
  public String getSemesterName() {
    return mSemesterName;
  }

  @Override
  public int getExamTypeId() {
    return mExamTypeId;
  }

  @Override
  public String getExamTypeName() {
    return mExamTypeName;
  }

  @Override
  public String getCourseId() {
    return mCourseId;
  }

  @Override
  public String getCourseTitle() {
    return mCourseTitle;
  }
}
