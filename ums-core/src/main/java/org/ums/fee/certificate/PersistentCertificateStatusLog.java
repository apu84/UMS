package org.ums.fee.certificate;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 14-Nov-17.
 */
public class PersistentCertificateStatusLog implements MutableCertificateStatusLog {

  private static CertificateStatusLogManager sCertificateStatusLogManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCertificateStatusLogManager =
        applicationContext.getBean("certificateStatusLogManager", CertificateStatusLogManager.class);
  }

  private Long mCertificateStatusId;
  private CertificateStatus.Status mStatus;
  private Date mProcessedOn;
  private String mProcessedBy;

  public PersistentCertificateStatusLog() {

  }

  public PersistentCertificateStatusLog(final PersistentCertificateStatusLog pPersistentCertificateStatusLog) {
    mCertificateStatusId = pPersistentCertificateStatusLog.mCertificateStatusId;
    mStatus = pPersistentCertificateStatusLog.getStatus();
    mProcessedOn = pPersistentCertificateStatusLog.getProcessedOn();
    mProcessedBy = pPersistentCertificateStatusLog.getProcessedBy();
  }

  @Override
  public void setCertificateStatusId(Long pCertificateStatusId) {
    mCertificateStatusId = pCertificateStatusId;
  }

  @Override
  public void setStatus(CertificateStatus.Status pStatus) {
    mStatus = pStatus;
  }

  @Override
  public void setProcessedOn(Date pProcessedOn) {
    mProcessedOn = pProcessedOn;
  }

  @Override
  public void setProcessedBy(String pProcessedBy) {
    mProcessedBy = pProcessedBy;
  }

  @Override
  public Long create() {
    return sCertificateStatusLogManager.create(this);
  }

  @Override
  public void update() {
    sCertificateStatusLogManager.update(this);
  }

  @Override
  public void delete() {
    sCertificateStatusLogManager.delete(this);
  }

  @Override
  public void setId(Long pId) {

  }

  @Override
  public Long getCertificateStatusId() {
    return mCertificateStatusId;
  }

  @Override
  public CertificateStatus.Status getStatus() {
    return mStatus;
  }

  @Override
  public Date getProcessedOn() {
    return mProcessedOn;
  }

  @Override
  public String getProcessedBy() {
    return mProcessedBy;
  }

  @Override
  public MutableCertificateStatusLog edit() {
    return new PersistentCertificateStatusLog(this);
  }

  @Override
  public Long getId() {
    return null;
  }

  @Override
  public String getLastModified() {
    return null;
  }
}
