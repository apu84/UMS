package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.AdmissionStudentCertificateResourceHelper;
import org.ums.resource.Resource;

public class MutableAdmissionStudentCertificateResource extends Resource {

  @Autowired
  AdmissionStudentCertificateResourceHelper mHelper;
}
