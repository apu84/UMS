package org.ums.fee.certificate;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 14-Nov-17.
 */
public class CertificateStatusLogDaoDecorator extends
    ContentDaoDecorator<CertificateStatusLog, MutableCertificateStatusLog, Long, CertificateStatusLogManager> implements
    CertificateStatusLogManager {

  @Override
  public List<CertificateStatusLog> getByStatus(CertificateStatus.Status pStatus) {
    return getManager().getByStatus(pStatus);
  }
}
