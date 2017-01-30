package org.ums.manager;

import org.ums.domain.model.immutable.AdmissionAllTypesOfCertificate;
import org.ums.domain.model.mutable.MutableAdmissionAllTypesOfCertificate;

import java.util.List;

public interface AdmissionAllTypesOfCertificateManager extends
    ContentManager<AdmissionAllTypesOfCertificate, MutableAdmissionAllTypesOfCertificate, Integer> {

  List<AdmissionAllTypesOfCertificate> getAdmissionStudentCertificateLists();

  List<AdmissionAllTypesOfCertificate> getCertificates(final String pCertificateType);
}
