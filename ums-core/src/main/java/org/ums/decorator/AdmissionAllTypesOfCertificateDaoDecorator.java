package org.ums.decorator;

import org.ums.domain.model.immutable.AdmissionAllTypesOfCertificate;
import org.ums.domain.model.mutable.MutableAdmissionAllTypesOfCertificate;
import org.ums.manager.AdmissionAllTypesOfCertificateManager;

import java.util.List;

public class AdmissionAllTypesOfCertificateDaoDecorator
    extends
    ContentDaoDecorator<AdmissionAllTypesOfCertificate, MutableAdmissionAllTypesOfCertificate, Integer, AdmissionAllTypesOfCertificateManager>
    implements AdmissionAllTypesOfCertificateManager {

  @Override
  public List<AdmissionAllTypesOfCertificate> getAdmissionStudentCertificateLists() {
    return getManager().getAdmissionStudentCertificateLists();
  }

  @Override
  public List<AdmissionAllTypesOfCertificate> getCertificates(String pCertificateType) {
    return getManager().getCertificates(pCertificateType);
  }
}
