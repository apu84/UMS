package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.AdmissionStudentsCertificateCommentResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by kawsu on 1/12/2017.
 */
public class MutableAdmissionStudentsCertificateCommentResource extends Resource {

  @Autowired
  AdmissionStudentsCertificateCommentResourceHelper mHelper;

  @POST
  @Path("/comments")
  public Response setComments(final JsonObject pJsonObject) {
    return mHelper.post(pJsonObject, mUriInfo);
  }

}
