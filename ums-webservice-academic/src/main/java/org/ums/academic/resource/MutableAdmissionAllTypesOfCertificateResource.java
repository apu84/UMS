package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.AdmissionAllTypesOfCertificateResourceHelper;
import org.ums.resource.Resource;

public class MutableAdmissionAllTypesOfCertificateResource extends Resource {

  @Autowired
  AdmissionAllTypesOfCertificateResourceHelper mHelper;
}
