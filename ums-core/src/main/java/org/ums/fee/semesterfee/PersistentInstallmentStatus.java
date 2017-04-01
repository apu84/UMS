package org.ums.fee.semesterfee;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

public class PersistentInstallmentStatus implements MutableInstallmentStatus {

  private static SemesterManager sSemesterManager;
  private static StudentManager sStudentManager;
  private static InstallmentStatusManager sInstallmentStatusManager;
  private Long mId;
  private Date mReceivedOn;
  private Boolean mPaymentCompleted;
  private Integer mInstallmentOrder;
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
  public Integer getInstallmentOrder() {
    return mInstallmentOrder;
  }

  @Override
  public void setInstallmentOrder(Integer pInstallmentOrder) {
    this.mInstallmentOrder = pInstallmentOrder;
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
    return sInstallmentStatusManager.create(this);
  }

  @Override
  public void update() {
    sInstallmentStatusManager.update(this);
  }

  @Override
  public MutableInstallmentStatus edit() {
    return new PersistentInstallmentStatus(this);
  }

  @Override
  public void delete() {
    sInstallmentStatusManager.delete(this);
  }

  public PersistentInstallmentStatus() {}

  public PersistentInstallmentStatus(MutableInstallmentStatus pInstallmentStatus) {
    setId(pInstallmentStatus.getId());
    setReceivedOn(pInstallmentStatus.getReceivedOn());
    setPaymentCompleted(pInstallmentStatus.isPaymentCompleted());
    setInstallmentOrder(pInstallmentStatus.getInstallmentOrder());
    setSemester(pInstallmentStatus.getSemester());
    setSemesterId(pInstallmentStatus.getSemesterId());
    setStudent(pInstallmentStatus.getStudent());
    setStudentId(pInstallmentStatus.getStudentId());
    setLastModified(pInstallmentStatus.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sInstallmentStatusManager = applicationContext.getBean("installmentStatusManager", InstallmentStatusManager.class);
  }
}
