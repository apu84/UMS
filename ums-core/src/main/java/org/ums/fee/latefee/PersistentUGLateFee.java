package org.ums.fee.latefee;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Semester;
import org.ums.manager.SemesterManager;

public class PersistentUGLateFee implements MutableUGLateFee {

  private static SemesterManager sSemesterManager;
  private static UGLateFeeManager sUGLateFeeManager;
  private Long mId;
  private Date mFrom;
  private Date mTo;
  private BigDecimal mFee;
  private Semester mSemester;
  private Integer mSemesterId;
  private String mLastModified;
  private AdmissionType mAdmissionType;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public Date getFrom() {
    return mFrom;
  }

  @Override
  public void setFrom(Date pFrom) {
    this.mFrom = pFrom;
  }

  @Override
  public Date getTo() {
    return mTo;
  }

  @Override
  public void setTo(Date pTo) {
    this.mTo = pTo;
  }

  @Override
  public BigDecimal getFee() {
    return mFee;
  }

  @Override
  public void setFee(BigDecimal pFee) {
    this.mFee = pFee;
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
  public void setAdmissionType(AdmissionType pAdmissionType) {
    mAdmissionType = pAdmissionType;
  }

  @Override
  public AdmissionType getAdmissionType() {
    return mAdmissionType;
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
    return sUGLateFeeManager.create(this);
  }

  @Override
  public void update() {
    sUGLateFeeManager.update(this);
  }

  @Override
  public MutableUGLateFee edit() {
    return new PersistentUGLateFee(this);
  }

  @Override
  public void delete() {
    sUGLateFeeManager.delete(this);
  }

  public PersistentUGLateFee() {}

  public PersistentUGLateFee(MutableUGLateFee pLateFee) {
    setId(pLateFee.getId());
    setFrom(pLateFee.getFrom());
    setTo(pLateFee.getTo());
    setFee(pLateFee.getFee());
    setSemester(pLateFee.getSemester());
    setSemesterId(pLateFee.getSemesterId());
    setLastModified(pLateFee.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sUGLateFeeManager = applicationContext.getBean("lateFeeManager", UGLateFeeManager.class);
  }
}
