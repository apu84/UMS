package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutablePaymentInfo;
import org.ums.enums.PaymentMode;
import org.ums.enums.PaymentType;
import org.ums.manager.PaymentInfoManager;
import org.ums.manager.AdmissionStudentManager;
import org.ums.manager.SemesterManager;

/**
 * Created by Monjur-E-Morshed on 24-Jan-17.
 */
public class PersistentPaymentInfo implements MutablePaymentInfo {

  private static AdmissionStudentManager sAdmissionStudentManager;
  private static SemesterManager sSemesterManager;
  private static PaymentInfoManager sPaymentInfoManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAdmissionStudentManager =
        applicationContext.getBean("admissionStudentManager", AdmissionStudentManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sPaymentInfoManager =
        applicationContext.getBean("admissionPaymentManager", PaymentInfoManager.class);
  }

  private int mId;
  private String mReferenceId;
  private AdmissionStudent mAdmissionStudent;
  private int mSemesterId;
  private Semester mSemester;
  private PaymentType mPaymentType;
  private int mAmount;
  private String mPaymentDate;
  private PaymentMode mPaymentMode;
  private String mLastModified;

  public PersistentPaymentInfo() {}

  public PersistentPaymentInfo(final PersistentPaymentInfo pPersistentAdmissionPayment) {
    mId = pPersistentAdmissionPayment.getId();
    mReferenceId = pPersistentAdmissionPayment.getReferenceId();
    mSemesterId = pPersistentAdmissionPayment.getSemesterId();
    mSemester = pPersistentAdmissionPayment.getSemester();
    mPaymentType = pPersistentAdmissionPayment.getPaymentType();
    mAmount = pPersistentAdmissionPayment.getAmount();
    mPaymentDate = pPersistentAdmissionPayment.getPaymentDate();
    mPaymentMode = pPersistentAdmissionPayment.getPaymentMode();
    mLastModified = pPersistentAdmissionPayment.getLastModified();
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sPaymentInfoManager.update(this);
    }
    else {
      sPaymentInfoManager.create(this);
    }
  }

  @Override
  public void setPaymentMode(PaymentMode pPaymentMode) {
    mPaymentMode = pPaymentMode;
  }

  @Override
  public PaymentMode getPaymentMode() {
    return mPaymentMode;
  }

  @Override
  public MutablePaymentInfo edit() {
    return new PersistentPaymentInfo(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Integer getId() {
    return mId;
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
  public void delete() {
    sPaymentInfoManager.delete(this);
  }

  @Override
  public void setReferenceId(String pReceiptId) {
    mReferenceId = pReceiptId;
  }

  @Override
  public String getReferenceId() {
    return mReferenceId;
  }

  @Override
  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setPaymentType(PaymentType pPaymentType) {
    mPaymentType = pPaymentType;
  }

  @Override
  public int getSemesterId() {
    return mSemesterId;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager
        .validate(mSemester);
  }

  @Override
  public PaymentType getPaymentType() {
    return mPaymentType;
  }

  @Override
  public int getAmount() {
    return mAmount;
  }

  @Override
  public void setAmount(int pAmount) {
    mAmount = pAmount;
  }

  @Override
  public String getPaymentDate() {
    return mPaymentDate;
  }

  @Override
  public void setPaymentDate(String pPaymentDate) {
    mPaymentDate = pPaymentDate;
  }

}
