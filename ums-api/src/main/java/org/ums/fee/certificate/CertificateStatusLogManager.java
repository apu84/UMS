package org.ums.fee.certificate;

import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 14-Nov-17.
 */
public interface CertificateStatusLogManager extends
    ContentManager<CertificateStatusLog, MutableCertificateStatusLog, Long> {

  List<CertificateStatusLog> getByStatus(CertificateStatus.Status pStatus);

}
