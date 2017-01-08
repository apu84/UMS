package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudentCertificate;
import org.ums.manager.AdmissionStudentManager;

import java.util.List;

public class PersistentAdmissionStudentCertificate implements MutableAdmissionStudentCertificate {

  private static AdmissionStudentManager sAdmissionStudentManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    applicationContext.getBean("admissionStudentManager", AdmissionStudentManager.class);
  }

  private Integer mCertificateId;
  private String mCertificateTitle;
  private String mCertificateType;

  public PersistentAdmissionStudentCertificate() {}

  public PersistentAdmissionStudentCertificate(
      AdmissionStudentCertificate pAdmissionStudentCertificate) {
    mCertificateId = pAdmissionStudentCertificate.getCertificateId();
    mCertificateTitle = pAdmissionStudentCertificate.getCertificateTitle();
    mCertificateType = pAdmissionStudentCertificate.getCertificateType();
  }

  @Override
  public void commit(boolean update) {

  }

  @Override
  public void delete() {

  }

  @Override
  public MutableAdmissionStudentCertificate edit() {
    return new PersistentAdmissionStudentCertificate(this);
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public String getId() {
    return null;
  }

  @Override
  public void setId(String pId) {

  }

  @Override
  public void setLastModified(String pLastModified) {

  }

  @Override
  public void setCertificateId(Integer pCertificateId) {
    mCertificateId = pCertificateId;
  }

  @Override
  public void setCertificateTitle(String pCertificateTitle) {
    mCertificateTitle = pCertificateTitle;
  }

  @Override
  public void setCetificateType(String pCertificateType) {
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
