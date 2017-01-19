package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.AdmissionCommentForStudentResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableAdmissionCommentForStudentResource extends Resource {

  @Autowired
  AdmissionCommentForStudentResourceHelper mHelper;

  @POST
  @Path("/comments")
  public Response setComments(final JsonObject pJsonObject) {
    return mHelper.post(pJsonObject, mUriInfo);
  }

}
