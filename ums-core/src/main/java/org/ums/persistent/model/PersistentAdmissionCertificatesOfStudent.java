package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableAdmissionCertificatesOfStudent;
import org.ums.manager.AdmissionCertificatesOfStudentManager;

public class PersistentAdmissionCertificatesOfStudent implements
    MutableAdmissionCertificatesOfStudent {

  private static AdmissionCertificatesOfStudentManager sAdmissionStudentsCertificateHistory;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAdmissionStudentsCertificateHistory =
        applicationContext.getBean("admissionStudentsCertificateHistoryManager",
            AdmissionCertificatesOfStudentManager.class);
  }

  private int mRowId;
  private int mSemesterId;
  private String mReceiptId;
  private int mCertificateId;
  private String mLastModified;
  private String mCertificateName;
  private String mCertificateType;

  public PersistentAdmissionCertificatesOfStudent() {

  }

  public PersistentAdmissionCertificatesOfStudent(
      final PersistentAdmissionCertificatesOfStudent pAdmissionStudentesCertificateHistory) {
    mRowId = pAdmissionStudentesCertificateHistory.getRowId();
    mSemesterId = pAdmissionStudentesCertificateHistory.getSemesterId();
    mReceiptId = pAdmissionStudentesCertificateHistory.getReceiptId();
    mCertificateId = pAdmissionStudentesCertificateHistory.getCertificateId();
    mCertificateName = pAdmissionStudentesCertificateHistory.getCertificateName();
    mCertificateType = pAdmissionStudentesCertificateHistory.getCertificateType();
  }

  @Override
  public Integer create() {
    return sAdmissionStudentsCertificateHistory.create(this);
  }

  @Override
  public void update() {
    sAdmissionStudentsCertificateHistory.update(this);
  }

  @Override
  public void delete() {
    sAdmissionStudentsCertificateHistory.delete(this);
  }

  @Override
  public MutableAdmissionCertificatesOfStudent edit() {
    return new PersistentAdmissionCertificatesOfStudent(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Integer getId() {
    return mRowId;
  }

  @Override
  public void setId(Integer pId) {
    mRowId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public int getRowId() {
    return mRowId;
  }

  @Override
  public int getSemesterId() {
    return mSemesterId;
  }

  @Override
  public String getReceiptId() {
    return mReceiptId;
  }

  @Override
  public int getCertificateId() {
    return mCertificateId;
  }

  @Override
  public String getCertificateName() {
    return mCertificateName;
  }

  @Override
  public String getCertificateType() {
    return mCertificateType;
  }

  @Override
  public void setRowId(int pRowId) {
    mRowId = pRowId;
  }

  @Override
  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setReceiptId(String pReceiptId) {
    mReceiptId = pReceiptId;
  }

  @Override
  public void setCertificateId(int pCertificateId) {
    mCertificateId = pCertificateId;
  }

  @Override
  public void setCertificateName(String pCertificateName) {
    mCertificateName = pCertificateName;
  }

  @Override
  public void setCertificateType(String pCertificateType) {
    mCertificateType = pCertificateType;
  }

}
