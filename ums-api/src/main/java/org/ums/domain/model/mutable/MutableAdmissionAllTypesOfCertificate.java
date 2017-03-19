package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AdmissionAllTypesOfCertificate;

public interface MutableAdmissionAllTypesOfCertificate extends AdmissionAllTypesOfCertificate,
    Editable<Integer>, MutableIdentifier<Integer>, MutableLastModifier {

  void setCertificateId(final int pCertificateId);

  void setCertificateTitle(final String pCertificateTitle);

  void setCertificateType(final String pCertificateType);

}
