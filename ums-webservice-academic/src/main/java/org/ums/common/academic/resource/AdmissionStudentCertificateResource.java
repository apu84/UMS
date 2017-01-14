package org.ums.common.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("/academic/admission/certificate")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AdmissionStudentCertificateResource extends MutableAdmissionStudentCertificateResource {

  @GET
  @Path("/certificateLists")
  public JsonObject getAllCertificates(final @Context Request pRequest) {
    return mHelper.getCertificates(mUriInfo);
  }

}
