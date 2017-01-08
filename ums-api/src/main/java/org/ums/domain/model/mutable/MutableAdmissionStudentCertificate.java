package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;

public interface MutableAdmissionStudentCertificate extends AdmissionStudentCertificate, Mutable,
    MutableIdentifier<String>, MutableLastModifier {

  void setCertificateId(final Integer pCertificateId);

  void setCertificateTitle(final String pCertificateTitle);

  void setCetificateType(final String pCertificateType);

}
