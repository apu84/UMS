package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableApplicationCCI;
import org.ums.enums.ApplicationStatus;
import org.ums.enums.ApplicationType;
import org.ums.manager.ApplicationCCIManager;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

/**
 * Created by My Pc on 7/14/2016.
 */
public class PersistentApplicationCCI implements MutableApplicationCCI {

  private static StudentManager sStudentManager;
  private static SemesterManager sSemesterManager;
  private static CourseManager sCourseManager;
  private static ApplicationCCIManager sApplicationCCIManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sCourseManager = applicationContext.getBean("courseManager", CourseManager.class);
    sApplicationCCIManager = applicationContext.getBean("applicationCCIManager", ApplicationCCIManager.class);
  }

  private Long mId;
  private Semester mSemester;
  private Integer mSemesterId;
  private Student mStudent;
  private String mStudentId;
  private Course mCourse;
  private String mCourseId;
  private String mCourseNo;
  private String mCourseTitle;
  private ApplicationType mApplicationType;
  private String mApplicationDate;
  private String mExamDate;
  private String mExamDateOriginal;
  private String mMessage;
  private Integer mTotalStudent;
  private Integer mCourseYear;
  private Integer mCourseSemester;
  private String mRoomNo;
  private Integer mRoomId;
  private ApplicationStatus mApplicationStatus;
  // rumi
  private Integer mCCIStatus;
  private String mGradeLetter;
  private Integer mcarryYear;
  private Integer mcarrySemester;
  private String mFullName;
  private Integer mCurrentEnrolledSemester;
  private Integer mGetTotalcarry;
  private Integer mGetTotalApplied;
  private Integer mGetTotalApproved;
  private String mGetStartDate;
  private String mTransactionId;
  private Integer mImprovementLimit;
  private String mCarryLastDate;
  private Integer mRowNumber;
  private String mSemesterName;

  public PersistentApplicationCCI() {

  }

  public PersistentApplicationCCI(final PersistentApplicationCCI pPersistentApplicationCCI) {
    mId = pPersistentApplicationCCI.getId();
    mSemester = pPersistentApplicationCCI.getSemester();
    mSemesterId = pPersistentApplicationCCI.getSemesterId();
    mStudent = pPersistentApplicationCCI.getStudent();
    mStudentId = pPersistentApplicationCCI.getStudentId();
    mCourse = pPersistentApplicationCCI.getCourse();
    mCourseId = pPersistentApplicationCCI.getCourseId();
    mCourseNo = pPersistentApplicationCCI.getCourseNo();
    mCourseTitle = pPersistentApplicationCCI.getCourseTitle();
    mApplicationType = pPersistentApplicationCCI.getApplicationType();
    mApplicationDate = pPersistentApplicationCCI.getApplicationDate();
    mExamDate = pPersistentApplicationCCI.getExamDate();
    mExamDateOriginal = pPersistentApplicationCCI.getExamDateOriginal();
    mMessage = pPersistentApplicationCCI.getMessage();
    mTotalStudent = pPersistentApplicationCCI.totalStudent();
    mCourseYear = pPersistentApplicationCCI.getCourseYear();
    mCourseSemester = pPersistentApplicationCCI.getCourseSemester();
    mRoomNo = pPersistentApplicationCCI.getRoomNo();
    mRoomId = pPersistentApplicationCCI.getRoomId();
    mApplicationStatus = pPersistentApplicationCCI.getApplicationStatus();
    mCCIStatus = pPersistentApplicationCCI.getCCIStatus();
    mGradeLetter = pPersistentApplicationCCI.getGradeLetter();
    mcarryYear = pPersistentApplicationCCI.getCarryYear();
    mcarrySemester = pPersistentApplicationCCI.getCarrySemester();
    mFullName = pPersistentApplicationCCI.getFullName();
    mCurrentEnrolledSemester = pPersistentApplicationCCI.getCurrentEnrolledSemester();
    mGetTotalcarry = pPersistentApplicationCCI.getTotalcarry();
    mGetTotalApplied = pPersistentApplicationCCI.getTotalApplied();
    mGetTotalApproved = pPersistentApplicationCCI.getTotalApproved();
    mGetStartDate = pPersistentApplicationCCI.getStartDate();
    mTransactionId = pPersistentApplicationCCI.getTransactionID();
    mImprovementLimit = pPersistentApplicationCCI.getImprovementLimit();
    mCarryLastDate = pPersistentApplicationCCI.getCarryLastDate();
    mRowNumber = pPersistentApplicationCCI.getRowNumber();
    mSemesterName = pPersistentApplicationCCI.getSemesterName();
  }

  @Override
  public void setSemesterName(String semesterName) {
    mSemesterName = semesterName;
  }

  @Override
  public String getSemesterName() {
    return mSemesterName;
  }

  @Override
  public void setRowNumber(Integer rowNumber) {
    mRowNumber = rowNumber;
  }

  @Override
  public Integer getRowNumber() {
    return mRowNumber;
  }

  @Override
  public void setImprovementLimit(Integer improvementLimit) {
    mImprovementLimit = improvementLimit;
  }

  @Override
  public void setCarryLastDate(String carryLastDate) {
    mCarryLastDate = carryLastDate;
  }

  @Override
  public String getCarryLastDate() {
    return mCarryLastDate;
  }

  @Override
  public Integer getImprovementLimit() {
    return mImprovementLimit;
  }

  @Override
  public void setTransactionID(String transactionId) {
    mTransactionId = transactionId;
  }

  @Override
  public String getTransactionID() {
    return mTransactionId;
  }

  @Override
  public void setTotalcarry(Integer totalcarry) {
    mGetTotalcarry = totalcarry;
  }

  @Override
  public void setTotalApplied(Integer totalApplied) {
    mGetTotalApplied = totalApplied;
  }

  @Override
  public void setTotalApproved(Integer totalApproved) {
    mGetTotalApproved = totalApproved;
  }

  @Override
  public void setStartDate(String pStartdate) {
    mGetStartDate = pStartdate;
  }

  @Override
  public Integer getTotalcarry() {
    return mGetTotalcarry;
  }

  @Override
  public Integer getTotalApplied() {
    return mGetTotalApplied;
  }

  @Override
  public Integer getTotalApproved() {
    return mGetTotalApproved;
  }

  @Override
  public String getStartDate() {
    return mGetStartDate;
  }

  @Override
  public void setFullName(String fullName) {
    mFullName = fullName;
  }

  @Override
  public void setCurrentEnrolledSemester(Integer currentEnrolledSemester) {
    mCurrentEnrolledSemester = currentEnrolledSemester;
  }

  @Override
  public String getFullName() {
    return mFullName;
  }

  @Override
  public Integer getCurrentEnrolledSemester() {
    return mCurrentEnrolledSemester;
  }

  @Override
  public void setCarryYear(Integer carryyear) {
    mcarryYear = carryyear;
  }

  @Override
  public void setCarrySemester(Integer carrySemester) {
    mcarrySemester = carrySemester;
  }

  @Override
  public Integer getCarryYear() {
    return mcarryYear;
  }

  @Override
  public Integer getCarrySemester() {
    return mcarrySemester;
  }

  @Override
  public void setGradeLetter(String gradeLetter) {
    mGradeLetter = gradeLetter;
  }

  @Override
  public String getGradeLetter() {
    return mGradeLetter;
  }

  // /rumi
  @Override
  public void setCCIStatus(Integer cciStatus) {
    mCCIStatus = cciStatus;
  }

  @Override
  public Integer getCCIStatus() {
    return mCCIStatus;
  }

  @Override
  public void setApplicationStatus(ApplicationStatus applicationStatus) {
    mApplicationStatus = applicationStatus;

  }

  @Override
  public ApplicationStatus getApplicationStatus() {
    return mApplicationStatus;
  }

  // ////////
  @Override
  public void setRoomId(Integer pRoomId) {
    mRoomId = pRoomId;
  }

  @Override
  public Integer getRoomId() {
    return mRoomId;
  }

  @Override
  public void setRoomNo(String pRoomNo) {
    mRoomNo = pRoomNo;
  }

  @Override
  public String getRoomNo() {
    return mRoomNo;
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
  public Integer getCourseYear() {
    return mCourseYear;
  }

  @Override
  public Integer getCourseSemester() {
    return mCourseSemester;
  }

  @Override
  public void setExamDateOriginal(String pExamDateOriginal) {
    mExamDateOriginal = pExamDateOriginal;
  }

  @Override
  public String getExamDateOriginal() {
    return mExamDateOriginal;
  }

  @Override
  public void setTotalStudent(Integer pTotalStudent) {
    mTotalStudent = pTotalStudent;
  }

  @Override
  public Integer totalStudent() {
    return mTotalStudent;
  }

  @Override
  public void setMessage(String pMessage) {
    String message = "" + pMessage;
    mMessage = message;
  }

  @Override
  public String getMessage() {
    if(mMessage == null) {
      mMessage = "";
    }
    return mMessage;
  }

  @Override
  public void setExamDate(String pExamDate) {
    mExamDate = pExamDate;
  }

  @Override
  public String getExamDate() {
    return mExamDate;
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
  public String getCourseNo() {
    return mCourseNo;
  }

  @Override
  public String getCourseTitle() {
    return mCourseTitle;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setStudentId(String pStudentId) {
    mStudentId = pStudentId;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public String getStudentId() {
    return mStudentId;
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setStudent(Student pStudent) {
    mStudent = pStudent;
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
  public void setApplicationType(ApplicationType pApplicationType) {
    mApplicationType = pApplicationType;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public Student getStudent() {
    return mStudent == null ? sStudentManager.get(mStudentId) : sStudentManager.validate(mStudent);
  }

  @Override
  public Course getCourse() {
    return mCourse == null ? sCourseManager.get(mCourseId) : sCourseManager.validate(mCourse);
  }

  @Override
  public String getCourseId() {
    return mCourseId;
  }

  @Override
  public ApplicationType getApplicationType() {
    return mApplicationType;
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
  public MutableApplicationCCI edit() {
    return new PersistentApplicationCCI(this);
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mApplicationDate;
  }

  @Override
  public Long create() {
    return sApplicationCCIManager.create(this);
  }

  @Override
  public void update() {
    sApplicationCCIManager.update(this);
  }

  @Override
  public void delete() {
    sApplicationCCIManager.delete(this);
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {

  }
}
