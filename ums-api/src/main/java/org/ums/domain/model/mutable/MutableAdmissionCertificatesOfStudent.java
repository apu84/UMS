package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AdmissionCertificatesOfStudent;

public interface MutableAdmissionCertificatesOfStudent extends AdmissionCertificatesOfStudent,
    Editable<Integer>, MutableIdentifier<Integer>, MutableLastModifier {
  void setRowId(final int pRowId);

  void setSemesterId(final int pSemesterId);

  void setReceiptId(final String pReceiptId);

  void setCertificateId(final int pCertificateId);

  void setCertificateName(final String pCertificateName);

  void setCertificateType(final String pCertificateType);
}
