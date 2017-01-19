package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.AdmissionAllTypesOfCertificate;
import org.ums.domain.model.mutable.MutableAdmissionAllTypesOfCertificate;
import org.ums.manager.AdmissionAllTypesOfCertificateManager;

public class PersistentAdmissionAllTypesOfCertificate implements
    MutableAdmissionAllTypesOfCertificate {

  private static AdmissionAllTypesOfCertificateManager sAdmissionAllTypesOfCertificateManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAdmissionAllTypesOfCertificateManager =
        applicationContext.getBean("admissionStudentCertificateManager",
            AdmissionAllTypesOfCertificateManager.class);
  }

  private int mCertificateId;
  private String mCertificateTitle;
  private String mCertificateType;
  private String mLastModified;

  public PersistentAdmissionAllTypesOfCertificate() {}

  public PersistentAdmissionAllTypesOfCertificate(
      AdmissionAllTypesOfCertificate pAdmissionAllTypesOfCertificate) {
    mCertificateId = pAdmissionAllTypesOfCertificate.getCertificateId();
    mCertificateTitle = pAdmissionAllTypesOfCertificate.getCertificateTitle();
    mCertificateType = pAdmissionAllTypesOfCertificate.getCertificateType();
    mLastModified = pAdmissionAllTypesOfCertificate.getLastModified();
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sAdmissionAllTypesOfCertificateManager.update(this);
    }
    else {
      sAdmissionAllTypesOfCertificateManager.create(this);
    }
  }

  @Override
  public void delete() {
    sAdmissionAllTypesOfCertificateManager.delete(this);
  }

  @Override
  public MutableAdmissionAllTypesOfCertificate edit() {
    return new PersistentAdmissionAllTypesOfCertificate(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Integer getId() {
    return mCertificateId;
  }

  @Override
  public void setId(Integer pId) {
    mCertificateId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void setCertificateId(int pCertificateId) {
    mCertificateId = pCertificateId;
  }

  @Override
  public void setCertificateTitle(String pCertificateTitle) {
    mCertificateTitle = pCertificateTitle;
  }

  @Override
  public void setCertificateType(String pCertificateType) {
    mCertificateType = pCertificateType;
  }

  @Override
  public int getCertificateId() {
    return mCertificateId;
  }

  @Override
  public String getCertificateTitle() {
    return mCertificateTitle;
  }

  @Override
  public String getCertificateType() {
    return mCertificateType;
  }

}
