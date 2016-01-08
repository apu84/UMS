package org.ums.academic.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ums.domain.model.mutable.MutableDepartment;
import org.ums.domain.model.mutable.MutableProgram;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.regular.*;
import org.ums.manager.ContentManager;
import org.ums.manager.SemesterManager;
import org.ums.util.Constants;

import java.util.Date;

public class PersistentStudent implements MutableStudent {
  private static ContentManager<Department, MutableDepartment, Integer> sDepartmentManager;
  private static SemesterManager sSemesterManager;
  private static ContentManager<Program, MutableProgram, Integer> sProgramManager;
  private static ContentManager<Student, MutableStudent, String> sStudentManager;

  static {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(Constants.SERVICE_CONTEXT);
    sDepartmentManager = (ContentManager) applicationContext.getBean("departmentManager");
    sSemesterManager = (SemesterManager) applicationContext.getBean("semesterManager");
    sProgramManager = (ContentManager<Program, MutableProgram, Integer>) applicationContext.getBean("programManager");
    sStudentManager = (ContentManager<Student, MutableStudent, String>) applicationContext.getBean("studentManager");
  }

  private String mId;
  private User mUser;
  private String mFullName;
  private Integer mDepartmentId;
  private Department mDepartment;
  private Integer mSemesterId;
  private Semester mSemester;
  private Integer mProgramId;
  private Program mProgram;
  private String mFatherName;
  private String mMotherName;
  private Date mDateOfBirth;
  private String mGender;
  private String mPresentAddress;
  private String mPermanentAddress;
  private String mMobileNo;
  private String mPhoneNo;
  private String mBloodGroup;
  private String mEmail;
  private String mGuardianName;
  private String mGuardianMobileNo;
  private String mGuardianPhoneNo;
  private String mGuardianEmail;
  private String mLastModified;

  public PersistentStudent() {

  }

  public PersistentStudent(final MutableStudent pMutableStudent) {
    setId(pMutableStudent.getId());
    setUser(pMutableStudent.getUser());
    setFullName(pMutableStudent.getFullName());
    setDepartmentId(pMutableStudent.getDepartmentId());
    setSemesterId(pMutableStudent.getSemesterId());
    setProgramId(pMutableStudent.getProgramId());
    setFatherName(pMutableStudent.getFatherName());
    setMotherName(pMutableStudent.getMotherName());
    setDateOfBirth(pMutableStudent.getDateOfBirth());
    setGender(pMutableStudent.getGender());
    setPresentAddress(pMutableStudent.getPresentAddress());
    setPermanentAddress(pMutableStudent.getPermanentAddress());
    setMobileNo(pMutableStudent.getMobileNo());
    setPhoneNo(pMutableStudent.getPhoneNo());
    setBloodGroup(pMutableStudent.getBloodGroup());
    setEmail(pMutableStudent.getBloodGroup());
    setGuardianName(pMutableStudent.getGuardianName());
    setGuardianMobileNo(pMutableStudent.getGuardianMobileNo());
    setGuardianPhoneNo(pMutableStudent.getGuardianPhoneNo());
    setGuardianEmail(pMutableStudent.getGuardianEmail());
    setLastModified(pMutableStudent.getLastModified());
  }


  @Override
  public void commit(boolean update) throws Exception {
    if (update) {
      sStudentManager.update(this);
    } else {
      sStudentManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sStudentManager.delete(this);
  }

  @Override
  public User getUser() {
    return mUser;
  }

  @Override
  public void setUser(User pUser) {
    mUser = pUser;
  }

  @Override
  public String getFullName() {
    return mFullName;
  }

  @Override
  public void setFullName(String pFullName) {
    mFullName = pFullName;
  }

  @Override
  public Integer getDepartmentId() {
    return mDepartmentId;
  }

  @Override
  public void setDepartmentId(Integer pDepartmentId) {
    mDepartmentId = pDepartmentId;
  }

  @Override
  public Department getDepartment() throws Exception {
    return mDepartment == null ? sDepartmentManager.get(mDepartmentId) : mDepartment;
  }

  @Override
  public void setDepartment(Department pDepartment) throws Exception {
    mDepartment = pDepartment;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public Semester getSemester() throws Exception {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : mSemester;
  }

  @Override
  public void setSemester(Semester pSemester) throws Exception {
    mSemester = pSemester;
  }

  @Override
  public Integer getProgramId() {
    return mProgramId;
  }

  @Override
  public void setProgramId(Integer pProgramId) {
    mProgramId = pProgramId;
  }

  @Override
  public Program getProgram() throws Exception {
    return mProgram == null ? sProgramManager.get(mProgramId) : mProgram;
  }

  @Override
  public void setProgram(Program pProgram) throws Exception {
    mProgram = pProgram;
  }

  @Override
  public String getFatherName() {
    return mFatherName;
  }

  @Override
  public void setFatherName(String pFatherName) {
    mFatherName = pFatherName;
  }

  @Override
  public String getMotherName() {
    return mMotherName;
  }

  @Override
  public void setMotherName(String pMotherName) {
    mMotherName = pMotherName;
  }

  @Override
  public Date getDateOfBirth() {
    return mDateOfBirth;
  }

  @Override
  public void setDateOfBirth(Date pDateOfBirth) {
    mDateOfBirth = pDateOfBirth;
  }

  @Override
  public String getGender() {
    return mGender;
  }

  @Override
  public void setGender(String pGender) {
    mGender = pGender;
  }

  @Override
  public String getPresentAddress() {
    return mPresentAddress;
  }

  @Override
  public void setPresentAddress(String pPresentAddress) {
    mPresentAddress = pPresentAddress;
  }

  @Override
  public String getPermanentAddress() {
    return mPermanentAddress;
  }

  @Override
  public void setPermanentAddress(String pPermanentAddress) {
    mPermanentAddress = pPermanentAddress;
  }

  @Override
  public String getMobileNo() {
    return mMobileNo;
  }

  @Override
  public void setMobileNo(String pMobileNo) {
    mMobileNo = pMobileNo;
  }

  @Override
  public String getPhoneNo() {
    return mPhoneNo;
  }

  @Override
  public void setPhoneNo(String pPhoneNo) {
    mPhoneNo = pPhoneNo;
  }

  @Override
  public String getBloodGroup() {
    return mBloodGroup;
  }

  @Override
  public void setBloodGroup(String pBloodGroup) {
    mBloodGroup = pBloodGroup;
  }

  @Override
  public String getEmail() {
    return mEmail;
  }

  @Override
  public void setEmail(String pEmail) {
    mEmail = pEmail;
  }

  @Override
  public String getGuardianName() {
    return mGuardianName;
  }

  @Override
  public void setGuardianName(String pGuardianName) {
    mGuardianName = pGuardianName;
  }

  @Override
  public String getGuardianMobileNo() {
    return mGuardianMobileNo;
  }

  @Override
  public void setGuardianMobileNo(String pGuardianMobileNo) {
    mGuardianMobileNo = pGuardianMobileNo;
  }

  @Override
  public String getGuardianPhoneNo() {
    return mGuardianPhoneNo;
  }

  @Override
  public void setGuardianPhoneNo(String pGuardianPhoneNo) {
    mGuardianPhoneNo = pGuardianPhoneNo;
  }

  @Override
  public String getGuardianEmail() {
    return mGuardianEmail;
  }

  @Override
  public void setGuardianEmail(String pGuardianEmail) {
    mGuardianEmail = pGuardianEmail;
  }

  @Override
  public MutableStudent edit() throws Exception {
    return new PersistentStudent(this);
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
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }
}
