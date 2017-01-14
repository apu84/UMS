package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.AdmissionStudentsCertificateHistoryResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by kawsu on 1/11/2017.
 */
public class MutableAdmissionStudentsCertificateHistoryResource extends Resource {

  @Autowired
  AdmissionStudentsCertificateHistoryResourceHelper mHelper;

  @POST
  @Path("/saveCertificates")
  public Response setComments(final JsonObject pJsonObject) {
    return mHelper.postCertificateList(pJsonObject, mUriInfo);
  }

}
