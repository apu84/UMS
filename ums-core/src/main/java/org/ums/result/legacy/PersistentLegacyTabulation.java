package org.ums.result.legacy;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

public class PersistentLegacyTabulation implements MutableLegacyTabulation {

  private static SemesterManager sSemesterManager;
  private static StudentManager sStudentManager;
  private static LegacyTabulationManager sLegacyTabulationManager;
  private Long mId;
  private Double mGpa;
  private Double mCgpa;
  private String mComment;
  private Integer mYear;
  private Integer mAcademicSemester;
  private Semester mSemester;
  private Integer mSemesterId;
  private Student mStudent;
  private String mStudentId;
  private String mLastModified;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public Double getGpa() {
    return mGpa;
  }

  @Override
  public void setGpa(Double pGpa) {
    this.mGpa = pGpa;
  }

  @Override
  public Double getCgpa() {
    return mCgpa;
  }

  @Override
  public void setCgpa(Double pCgpa) {
    this.mCgpa = pCgpa;
  }

  @Override
  public String getComment() {
    return mComment;
  }

  @Override
  public void setComment(String pComment) {
    this.mComment = pComment;
  }

  @Override
  public Integer getYear() {
    return mYear;
  }

  @Override
  public void setYear(Integer pYear) {
    this.mYear = pYear;
  }

  @Override
  public Integer getAcademicSemester() {
    return mAcademicSemester;
  }

  @Override
  public void setAcademicSemester(Integer pAcademicSemester) {
    this.mAcademicSemester = pAcademicSemester;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public void setSemester(Semester pSemester) {
    this.mSemester = pSemester;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    this.mSemesterId = pSemesterId;
  }

  @Override
  public Student getStudent() {
    return mStudent == null ? sStudentManager.get(mStudentId) : sStudentManager.validate(mStudent);
  }

  @Override
  public void setStudent(Student pStudent) {
    this.mStudent = pStudent;
  }

  @Override
  public String getStudentId() {
    return mStudentId;
  }

  @Override
  public void setStudentId(String pStudentId) {
    this.mStudentId = pStudentId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public Long create() {
    return sLegacyTabulationManager.create(this);
  }

  @Override
  public void update() {
    sLegacyTabulationManager.update(this);
  }

  @Override
  public MutableLegacyTabulation edit() {
    return new PersistentLegacyTabulation(this);
  }

  @Override
  public void delete() {
    sLegacyTabulationManager.delete(this);
  }

  public PersistentLegacyTabulation() {}

  public PersistentLegacyTabulation(MutableLegacyTabulation pLegacyTabulation) {
    setId(pLegacyTabulation.getId());
    setGpa(pLegacyTabulation.getGpa());
    setCgpa(pLegacyTabulation.getCgpa());
    setComment(pLegacyTabulation.getComment());
    setYear(pLegacyTabulation.getYear());
    setAcademicSemester(pLegacyTabulation.getAcademicSemester());
    setSemester(pLegacyTabulation.getSemester());
    setSemesterId(pLegacyTabulation.getSemesterId());
    setStudent(pLegacyTabulation.getStudent());
    setStudentId(pLegacyTabulation.getStudentId());
    setLastModified(pLegacyTabulation.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sLegacyTabulationManager = applicationContext.getBean("legacyTabulationManager", LegacyTabulationManager.class);
  }
}
