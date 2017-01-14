package org.ums.manager;

import org.ums.domain.model.immutable.AdmissionStudentsCertificateHistory;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateHistory;

import java.util.List;

/**
 * Created by kawsu on 1/11/2017.
 */
public interface AdmissionStudentsCertificateHistoryManager
    extends
    ContentManager<AdmissionStudentsCertificateHistory, MutableAdmissionStudentsCertificateHistory, Integer> {
  int saveAdmissionStudentsCertificates(
      final List<MutableAdmissionStudentsCertificateHistory> pCertificateHistorys);

  List<AdmissionStudentsCertificateHistory> getStudentsSavedCertificateLists(final int pSemesterId,
      final String pReceiptId);
}
