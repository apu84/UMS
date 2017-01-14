package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.AdmissionStudentCertificateResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class MutableAdmissionStudentCertificateResource extends Resource {

  @Autowired
  AdmissionStudentCertificateResourceHelper mHelper;
}
