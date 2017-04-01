package org.ums.fee.semesterfee;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

class PersistentSemesterAdmissionStatus implements MutableSemesterAdmissionStatus {

  private static SemesterManager sSemesterManager;
  private static StudentManager sStudentManager;
  private static SemesterAdmissionStatusManager sSemesterAdmissionStatusManager;
  private Long mId;
  private Date mReceivedOn;
  private Boolean mPaymentCompleted;
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
  public Date getReceivedOn() {
    return mReceivedOn;
  }

  @Override
  public void setReceivedOn(Date pReceivedOn) {
    this.mReceivedOn = pReceivedOn;
  }

  @Override
  public Boolean isPaymentCompleted() {
    return mPaymentCompleted;
  }

  @Override
  public void setPaymentCompleted(Boolean pPaymentCompleted) {
    this.mPaymentCompleted = pPaymentCompleted;
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
    return sSemesterAdmissionStatusManager.create(this);
  }

  @Override
  public void update() {
    sSemesterAdmissionStatusManager.update(this);
  }

  @Override
  public MutableSemesterAdmissionStatus edit() {
    return new PersistentSemesterAdmissionStatus(this);
  }

  @Override
  public void delete() {
    sSemesterAdmissionStatusManager.delete(this);
  }

  public PersistentSemesterAdmissionStatus() {}

  PersistentSemesterAdmissionStatus(MutableSemesterAdmissionStatus pSemesterAdmissionStatus) {
    setId(pSemesterAdmissionStatus.getId());
    setReceivedOn(pSemesterAdmissionStatus.getReceivedOn());
    setPaymentCompleted(pSemesterAdmissionStatus.isPaymentCompleted());
    setSemester(pSemesterAdmissionStatus.getSemester());
    setSemesterId(pSemesterAdmissionStatus.getSemesterId());
    setStudent(pSemesterAdmissionStatus.getStudent());
    setStudentId(pSemesterAdmissionStatus.getStudentId());
    setLastModified(pSemesterAdmissionStatus.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sSemesterAdmissionStatusManager =
        applicationContext.getBean("semesterAdmissionStatusManager", SemesterAdmissionStatusManager.class);
  }
}
