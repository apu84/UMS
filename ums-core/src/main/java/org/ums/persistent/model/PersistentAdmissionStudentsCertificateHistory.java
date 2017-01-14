package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.AdmissionStudentsCertificateHistory;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateHistory;
import org.ums.manager.AdmissionStudentsCertificateHistoryManager;

public class PersistentAdmissionStudentsCertificateHistory implements
    MutableAdmissionStudentsCertificateHistory {

  private static AdmissionStudentsCertificateHistoryManager sAdmissionStudentsCertificateHistory;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAdmissionStudentsCertificateHistory =
        applicationContext.getBean("admissionStudentsCertificateHistoryManager",
            AdmissionStudentsCertificateHistoryManager.class);
  }

  private int mRowId;
  private int mSemesterId;
  private String mReceiptId;
  private int mCertificateId;
  private String mLastModified;

  public PersistentAdmissionStudentsCertificateHistory() {

  }

  public PersistentAdmissionStudentsCertificateHistory(
      final PersistentAdmissionStudentsCertificateHistory pAdmissionStudentesCertificateHistory) {
    mRowId = pAdmissionStudentesCertificateHistory.getRowId();
    mSemesterId = pAdmissionStudentesCertificateHistory.getSemesterId();
    mReceiptId = pAdmissionStudentesCertificateHistory.getReceiptId();
    mCertificateId = pAdmissionStudentesCertificateHistory.getCertificateId();
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sAdmissionStudentsCertificateHistory.update(this);
    }
    else {
      sAdmissionStudentsCertificateHistory.create(this);
    }
  }

  @Override
  public void delete() {
    sAdmissionStudentsCertificateHistory.delete(this);
  }

  @Override
  public MutableAdmissionStudentsCertificateHistory edit() {
    return new PersistentAdmissionStudentsCertificateHistory(this);
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

}
