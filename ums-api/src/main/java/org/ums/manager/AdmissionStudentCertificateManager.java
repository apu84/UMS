package org.ums.manager;

import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;
import org.ums.domain.model.mutable.MutableAdmissionStudentCertificate;

import java.util.List;

public interface AdmissionStudentCertificateManager extends
    ContentManager<AdmissionStudentCertificate, MutableAdmissionStudentCertificate, Integer> {

  List<AdmissionStudentCertificate> getAdmissionStudentCertificateLists();
}
