package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;
import org.ums.domain.model.mutable.MutableAdmissionStudentCertificate;
import org.ums.manager.AdmissionStudentCertificateManager;

import java.util.List;

public class PersistentAdmissionStudentCertificate implements MutableAdmissionStudentCertificate {

  private static AdmissionStudentCertificateManager sAdmissionStudentCertificateManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAdmissionStudentCertificateManager =
        applicationContext.getBean("admissionStudentCertificateManager",
            AdmissionStudentCertificateManager.class);
  }

  private int mCertificateId;
  private String mCertificateTitle;
  private String mCertificateType;
  private String mCertificateCategory;
  private String mLastModified;

  public PersistentAdmissionStudentCertificate() {}

  public PersistentAdmissionStudentCertificate(
      AdmissionStudentCertificate pAdmissionStudentCertificate) {
    mCertificateId = pAdmissionStudentCertificate.getCertificateId();
    mCertificateTitle = pAdmissionStudentCertificate.getCertificateTitle();
    mCertificateType = pAdmissionStudentCertificate.getCertificateType();
    mCertificateCategory = pAdmissionStudentCertificate.getCertificateCategory();
    mLastModified = pAdmissionStudentCertificate.getLastModified();
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sAdmissionStudentCertificateManager.update(this);
    }
    else {
      sAdmissionStudentCertificateManager.create(this);
    }
  }

  @Override
  public void delete() {
    sAdmissionStudentCertificateManager.delete(this);
  }

  @Override
  public MutableAdmissionStudentCertificate edit() {
    return new PersistentAdmissionStudentCertificate(this);
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
  public void setCertificateCategory(String pCertificateCategory) {
    mCertificateCategory = pCertificateCategory;
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

  @Override
  public String getCertificateCategory() {
    return mCertificateCategory;
  }
}
