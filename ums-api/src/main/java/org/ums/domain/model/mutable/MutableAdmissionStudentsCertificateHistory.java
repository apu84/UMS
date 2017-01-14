package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AdmissionStudentsCertificateHistory;

public interface MutableAdmissionStudentsCertificateHistory extends
    AdmissionStudentsCertificateHistory, Mutable, MutableIdentifier<Integer>, MutableLastModifier {
  void setRowId(final int pRowId);

  void setSemesterId(final int pSemesterId);

  void setReceiptId(final String pReceiptId);

  void setCertificateId(final int pCertificateId);
}
