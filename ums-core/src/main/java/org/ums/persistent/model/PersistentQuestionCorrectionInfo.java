package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableQuestionCorrectionInfo;
import org.ums.manager.CourseManager;
import org.ums.manager.QuestionCorrectionManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */
public class PersistentQuestionCorrectionInfo implements MutableQuestionCorrectionInfo {
  private static StudentManager sStudentManager;
  private static SemesterManager sSemesterManager;
  private static CourseManager sCourseManager;
  private static QuestionCorrectionManager sQuestionCorrectionManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sQuestionCorrectionManager =
        applicationContext.getBean("questionCorrectionManager", QuestionCorrectionManager.class);
  }

  private Long mId;
  private Integer mSemesterId;
  private Integer mExamType;
  private Integer mProgramId;
  private String mProgramName;
  private Integer mYear;
  private Integer mSemester;
  private String mCourseId;
  private String mCourseNo;
  private String mCourseTitle;
  private String mIncorrectQuestionNo;
  private String mTypeOfMistake;
  private String mEmployeeId;
  private String mEmployeeName;
  private String mExamDate;

  public PersistentQuestionCorrectionInfo() {

  }

  public PersistentQuestionCorrectionInfo(final PersistentQuestionCorrectionInfo pPersistentQuestionCorrectionInfo) {
    mId = pPersistentQuestionCorrectionInfo.getId();
    mSemesterId = pPersistentQuestionCorrectionInfo.getSemesterId();
    mExamType = pPersistentQuestionCorrectionInfo.getExamType();
    mProgramId = pPersistentQuestionCorrectionInfo.getProgramId();
    mProgramName = pPersistentQuestionCorrectionInfo.getProgramName();
    mYear = pPersistentQuestionCorrectionInfo.getYear();
    mSemester = pPersistentQuestionCorrectionInfo.getSemester();
    mCourseId = pPersistentQuestionCorrectionInfo.getCourseId();
    mCourseNo = pPersistentQuestionCorrectionInfo.getCourseNo();
    mCourseTitle = pPersistentQuestionCorrectionInfo.getCourseTitle();
    mIncorrectQuestionNo = pPersistentQuestionCorrectionInfo.getIncorrectQuestionNo();
    mTypeOfMistake = pPersistentQuestionCorrectionInfo.getTypeOfMistake();
    mEmployeeId = pPersistentQuestionCorrectionInfo.getEmployeeId();
    mEmployeeName = pPersistentQuestionCorrectionInfo.getEmployeeName();
    mExamDate = pPersistentQuestionCorrectionInfo.getExamDate();
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
  public void setExamType(Integer pExamType) {
    mExamType = pExamType;
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
  public void setCourseNo(String pCourseNo) {
    mCourseNo = pCourseNo;
  }

  @Override
  public void setCourseTitle(String pCourseTitle) {
    mCourseTitle = pCourseTitle;
  }

  @Override
  public void setIncorrectQuestionNo(String pIncorrectQuestionNo) {
    mIncorrectQuestionNo = pIncorrectQuestionNo;
  }

  @Override
  public void setTypeOfMistake(String pTypeOfMistake) {
    mTypeOfMistake = pTypeOfMistake;
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
  public void setExamDate(String pExamDate) {
    mExamDate = pExamDate;
  }

  @Override
  public Long create() {
    return sQuestionCorrectionManager.create(this);
  }

  @Override
  public void update() {
    sQuestionCorrectionManager.update(this);
  }

  @Override
  public void delete() {
    sQuestionCorrectionManager.delete(this);
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
  public String getCourseNo() {
    return mCourseNo;
  }

  @Override
  public String getCourseTitle() {
    return mCourseTitle;
  }

  @Override
  public String getIncorrectQuestionNo() {
    return mIncorrectQuestionNo;
  }

  @Override
  public String getTypeOfMistake() {
    return mTypeOfMistake;
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
  public String getExamDate() {
    return mExamDate;
  }

  @Override
  public MutableQuestionCorrectionInfo edit() {
    return new PersistentQuestionCorrectionInfo(this);
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
