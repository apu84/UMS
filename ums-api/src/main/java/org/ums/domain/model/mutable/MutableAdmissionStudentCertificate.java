package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;

public interface MutableAdmissionStudentCertificate extends AdmissionStudentCertificate, Mutable,
    MutableIdentifier<Integer>, MutableLastModifier {

  void setCertificateId(final int pCertificateId);

  void setCertificateTitle(final String pCertificateTitle);

  void setCertificateType(final String pCertificateType);

  void setCertificateCategory(final String pCertificateCategory);
}
