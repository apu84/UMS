package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.AdmissionCertificateSubmissionResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableAdmissionCertificateSubmissionResource extends Resource {

  @Autowired
  AdmissionCertificateSubmissionResourceHelper mHelper;

  @PUT
  @Path("/saveAll")
  public Response saveAllDocuments(final JsonObject pJsonObject) {
    return mHelper.saveAllCertificates(pJsonObject, mUriInfo);
  }
}
