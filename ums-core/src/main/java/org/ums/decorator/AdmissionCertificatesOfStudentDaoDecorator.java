package org.ums.decorator;

import org.ums.domain.model.immutable.AdmissionCertificatesOfStudent;
import org.ums.domain.model.mutable.MutableAdmissionCertificatesOfStudent;
import org.ums.manager.AdmissionCertificatesOfStudentManager;

import java.util.List;

/**
 * Created by kawsu on 1/11/2017.
 */
public class AdmissionCertificatesOfStudentDaoDecorator
    extends
    ContentDaoDecorator<AdmissionCertificatesOfStudent, MutableAdmissionCertificatesOfStudent, Integer, AdmissionCertificatesOfStudentManager>
    implements AdmissionCertificatesOfStudentManager {
  @Override
  public int saveAdmissionStudentsCertificates(
      List<MutableAdmissionCertificatesOfStudent> pCertificateHistorys) {
    return getManager().saveAdmissionStudentsCertificates(pCertificateHistorys);
  }

  @Override
  public List<AdmissionCertificatesOfStudent> getStudentsSavedCertificateLists(int pSemesterId,
      String pReceiptId) {
    return getManager().getStudentsSavedCertificateLists(pSemesterId, pReceiptId);
  }
}
