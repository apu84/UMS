package org.ums.decorator;

import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;
import org.ums.domain.model.mutable.MutableAdmissionStudentCertificate;
import org.ums.manager.AdmissionStudentCertificateManager;

import java.util.List;

public class AdmissionStudentCertificateDaoDecorator
    extends
    ContentDaoDecorator<AdmissionStudentCertificate, MutableAdmissionStudentCertificate, Integer, AdmissionStudentCertificateManager>
    implements AdmissionStudentCertificateManager {

  @Override
  public List<AdmissionStudentCertificate> getAdmissionStudentCertificateLists() {
    return getManager().getAdmissionStudentCertificateLists();
  }
}
