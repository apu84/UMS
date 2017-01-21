package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.AdmissionCertificatesOfStudentResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableAdmissionCertificatesOfStudentResource extends Resource {

  @Autowired
  AdmissionCertificatesOfStudentResourceHelper mHelper;

}
