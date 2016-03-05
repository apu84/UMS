package org.ums.academic.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.mutable.MutableStudentRecord;
import org.ums.domain.model.readOnly.Semester;
import org.ums.domain.model.readOnly.Student;
import org.ums.domain.model.readOnly.StudentRecord;
import org.ums.manager.ContentManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentRecordManager;

public class PersistentStudentRecord implements MutableStudentRecord {
  private static StudentRecordManager sStudentRecordManager;
  private static ContentManager<Student, MutableStudent, String> sStudentManager;
  private static SemesterManager sSemesterManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sStudentRecordManager = applicationContext.getBean("studentRecordManager", StudentRecordManager.class);
    sStudentManager = (ContentManager<Student, MutableStudent, String>) applicationContext.getBean("studentManager");
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
  }

  private Integer mId;
  private String mStudentId;
  private Student mStudent;
  private Integer mSemesterId;
  private Semester mSemester;
  private Integer mYear;
  private Integer mAcademicSemester;
  private Float mCGPA;
  private Float mGPA;
  private StudentRecord.Status mStatus;
  private StudentRecord.Type mType;
  private String mLastModified;

  public PersistentStudentRecord() {
  }

  public PersistentStudentRecord(final PersistentStudentRecord pPersistentStudentRecord) throws Exception {
    setId(pPersistentStudentRecord.getId());
    setStudentId(pPersistentStudentRecord.getStudentId());
    setSemesterId(pPersistentStudentRecord.getSemesterId());
    setYear(pPersistentStudentRecord.getYear());
    setAcademicSemester(pPersistentStudentRecord.getAcademicSemester());
    setCGPA(pPersistentStudentRecord.getCGPA());
    setGPA(pPersistentStudentRecord.getGPA());
    setType(pPersistentStudentRecord.getType());
    setStatus(pPersistentStudentRecord.getStatus());
    setLastModified(pPersistentStudentRecord.getLastModified());
  }

  @Override
  public void setStudentId(String pStudentId) {
    mStudentId = pStudentId;
  }

  @Override
  public void setStudent(Student pStudent) {
    mStudent = pStudent;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setYear(Integer pYear) {
    mYear = pYear;
  }

  @Override
  public void setAcademicSemester(Integer pAcademicSemester) {
    mAcademicSemester = pAcademicSemester;
  }

  @Override
  public void setCGPA(Float pCGPA) {
    mCGPA = pCGPA;
  }

  @Override
  public void setGPA(Float pGPA) {
    mGPA = pGPA;
  }

  @Override
  public void setType(Type pType) {
    mType = pType;
  }

  @Override
  public void setStatus(Status pStatus) {
    mStatus = pStatus;
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void commit(boolean update) throws Exception {
    if (update) {
      sStudentRecordManager.update(this);
    } else {
      sStudentRecordManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sStudentRecordManager.delete(this);
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
  public String getStudentId() {
    return mStudentId;
  }

  @Override
  public Student getStudent() throws Exception {
    return mStudent == null ? sStudentManager.get(mStudentId) : sStudentManager.validate(mStudent);
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public Semester getSemester() throws Exception {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public Integer getYear() {
    return mYear;
  }

  @Override
  public Integer getAcademicSemester() {
    return mAcademicSemester;
  }

  @Override
  public Float getCGPA() {
    return mCGPA;
  }

  @Override
  public Float getGPA() {
    return mGPA;
  }

  @Override
  public Type getType() {
    return mType;
  }

  @Override
  public Status getStatus() {
    return mStatus;
  }

  @Override
  public MutableStudentRecord edit() throws Exception {
    return new PersistentStudentRecord(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }
}
