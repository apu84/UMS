package org.ums.decorator;

import org.ums.domain.model.immutable.AdmissionStudentsCertificateHistory;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateHistory;
import org.ums.manager.AdmissionStudentsCertificateHistoryManager;

import java.util.List;

/**
 * Created by kawsu on 1/11/2017.
 */
public class AdmissionStudentsCertificateHistoryDaoDecorator
    extends
    ContentDaoDecorator<AdmissionStudentsCertificateHistory, MutableAdmissionStudentsCertificateHistory, Integer, AdmissionStudentsCertificateHistoryManager>
    implements AdmissionStudentsCertificateHistoryManager {
  @Override
  public int saveAdmissionStudentsCertificates(
      List<MutableAdmissionStudentsCertificateHistory> pCertificateHistorys) {
    return getManager().saveAdmissionStudentsCertificates(pCertificateHistorys);
  }

  @Override
  public List<AdmissionStudentsCertificateHistory> getStudentsSavedCertificateLists(
      int pSemesterId, String pReceiptId) {
    return getManager().getStudentsSavedCertificateLists(pSemesterId, pReceiptId);
  }
}
