package org.ums.fee.certificate;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 13-Nov-17.
 */
public interface MutableCertificateStatusLog extends CertificateStatusLog, Editable<Long>, MutableIdentifier<Long> {

  void setCertificateStatusId(Long pCertificateStatusId);

  void setStatus(CertificateStatus.Status pStatus);

  void setProcessedOn(Date pProcessedOn);

  void setProcessedBy(String pProcessedBy);

}
