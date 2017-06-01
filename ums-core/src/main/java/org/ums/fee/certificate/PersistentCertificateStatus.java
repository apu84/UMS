package org.ums.fee.certificate;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

import java.util.Date;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeCategoryManager;
import org.ums.domain.model.immutable.Student;
import org.ums.manager.SemesterManager;
import org.ums.domain.model.immutable.Semester;
import org.ums.manager.StudentManager;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

public class PersistentCertificateStatus implements MutableCertificateStatus {

  private static FeeCategoryManager sFeeCategoryManager;
  private static SemesterManager sSemesterManager;
  private static StudentManager sStudentManager;
  private static UserManager sUserManager;
  private static CertificateStatusManager sCertificateStatusManager;
  private Long mId;
  private FeeCategory mFeeCategory;
  private String mFeeCategoryId;
  private String mTransactionId;
  private Student mStudent;
  private String mStudentId;
  private Semester mSemester;
  private Integer mSemesterId;
  private Date mProcessedOn;
  private Status mStatus;
  private User mUser;
  private String mUserId;
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
  public FeeCategory getFeeCategory() {
    return mFeeCategory == null ? sFeeCategoryManager.get(mFeeCategoryId) : sFeeCategoryManager.validate(mFeeCategory);
  }

  @Override
  public void setFeeCategory(FeeCategory pFeeCategory) {
    this.mFeeCategory = pFeeCategory;
  }

  @Override
  public String getFeeCategoryId() {
    return mFeeCategoryId;
  }

  @Override
  public void setFeeCategoryId(String pFeeCategoryId) {
    this.mFeeCategoryId = pFeeCategoryId;
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
  public Date getProcessedOn() {
    return mProcessedOn;
  }

  @Override
  public void setProcessedOn(Date pProcessedOn) {
    this.mProcessedOn = pProcessedOn;
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
  public User getUser() {
    return mUser == null ? sUserManager.get(mUserId) : sUserManager.validate(mUser);
  }

  @Override
  public void setUser(User pUser) {
    this.mUser = pUser;
  }

  @Override
  public String getUserId() {
    return mUserId;
  }

  @Override
  public void setUserId(String pUserId) {
    this.mUserId = pUserId;
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
    return sCertificateStatusManager.create(this);
  }

  @Override
  public void update() {
    sCertificateStatusManager.update(this);
  }

  @Override
  public MutableCertificateStatus edit() {
    return new PersistentCertificateStatus(this);
  }

  @Override
  public void delete() {
    sCertificateStatusManager.delete(this);
  }

  public PersistentCertificateStatus() {}

  public PersistentCertificateStatus(MutableCertificateStatus pCertificateStatus) {
    setId(pCertificateStatus.getId());
    setFeeCategory(pCertificateStatus.getFeeCategory());
    setFeeCategoryId(pCertificateStatus.getFeeCategoryId());
    setTransactionId(pCertificateStatus.getTransactionId());
    setStudent(pCertificateStatus.getStudent());
    setStudentId(pCertificateStatus.getStudentId());
    setSemester(pCertificateStatus.getSemester());
    setSemesterId(pCertificateStatus.getSemesterId());
    setProcessedOn(pCertificateStatus.getProcessedOn());
    setStatus(pCertificateStatus.getStatus());
    setUser(pCertificateStatus.getUser());
    setUserId(pCertificateStatus.getUserId());
    setLastModified(pCertificateStatus.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFeeCategoryManager = applicationContext.getBean("feeCategoryManager", FeeCategoryManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sUserManager = applicationContext.getBean("userManager", UserManager.class);
    sCertificateStatusManager = applicationContext.getBean("certificateStatusManager", CertificateStatusManager.class);
  }
}
