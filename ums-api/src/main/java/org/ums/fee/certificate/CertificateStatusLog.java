package org.ums.fee.certificate;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 13-Nov-17.
 */
public interface CertificateStatusLog extends Serializable, EditType<MutableCertificateStatusLog>, LastModifier,
    Identifier<Long> {
  Long getCertificateStatusId();

  CertificateStatus.Status getStatus();

  Date getProcessedOn();

  String getProcessedBy();
}
