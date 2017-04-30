package org.ums.fee.payment;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.fee.FeeType;
import org.ums.fee.FeeTypeManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

public class PersistentStudentPayment implements MutableStudentPayment {

  private static SemesterManager sSemesterManager;
  private static StudentManager sStudentManager;
  private static StudentPaymentManager sStudentPaymentManager;
  private static FeeTypeManager sFeeTypeManager;
  private Long mId;
  private String mTransactionId;
  private Semester mSemester;
  private Integer mSemesterId;
  private Student mStudent;
  private String mStudentId;
  private Double mAmount;
  private Status mStatus;
  private Date mAppliedOn;
  private Date mVerifiedOn;
  private String mLastModified;
  private Integer mFeeTypeId;
  private FeeType mFeeType;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public String getTransactionId() {
    return mTransactionId;
  }

  @Override
  public void setTransactionId(String pTransactionId) {
    this.mTransactionId = pTransactionId;
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
  public Double getAmount() {
    return mAmount;
  }

  @Override
  public void setAmount(Double pAmount) {
    this.mAmount = pAmount;
  }

  @Override
  public Status getStatus() {
    return mStatus;
  }

  @Override
  public void setStatus(Status pStatus) {
    this.mStatus = pStatus;
  }

  @Override
  public Date getAppliedOn() {
    return mAppliedOn;
  }

  @Override
  public void setAppliedOn(Date pAppliedOn) {
    this.mAppliedOn = pAppliedOn;
  }

  @Override
  public Date getVerifiedOn() {
    return mVerifiedOn;
  }

  @Override
  public void setVerifiedOn(Date pVerifiedOn) {
    this.mVerifiedOn = pVerifiedOn;
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
  public Integer getFeeTypeId() {
    return mFeeTypeId;
  }

  @Override
  public FeeType getFeeType() {
    return mFeeType == null ? sFeeTypeManager.get(mFeeTypeId) : sFeeTypeManager.validate(mFeeType);
  }

  @Override
  public void setFeeTypeId(Integer feeTypeId) {
    mFeeTypeId = feeTypeId;
  }

  @Override
  public void setFeeType(FeeType feeType) {
    mFeeType = feeType;
  }

  @Override
  public Long create() {
    return sStudentPaymentManager.create(this);
  }

  @Override
  public void update() {
    sStudentPaymentManager.update(this);
  }

  @Override
  public MutableStudentPayment edit() {
    return new PersistentStudentPayment(this);
  }

  @Override
  public void delete() {
    sStudentPaymentManager.delete(this);
  }

  public PersistentStudentPayment() {}

  public PersistentStudentPayment(MutableStudentPayment pStudentPayment) {
    setId(pStudentPayment.getId());
    setTransactionId(pStudentPayment.getTransactionId());
    setSemester(pStudentPayment.getSemester());
    setSemesterId(pStudentPayment.getSemesterId());
    setStudent(pStudentPayment.getStudent());
    setStudentId(pStudentPayment.getStudentId());
    setAmount(pStudentPayment.getAmount());
    setStatus(pStudentPayment.getStatus());
    setAppliedOn(pStudentPayment.getAppliedOn());
    setVerifiedOn(pStudentPayment.getVerifiedOn());
    setLastModified(pStudentPayment.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sStudentPaymentManager = applicationContext.getBean("studentPaymentManager", StudentPaymentManager.class);
    sFeeTypeManager = applicationContext.getBean("feeTypeManager", FeeTypeManager.class);
  }
}
