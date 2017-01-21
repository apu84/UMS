package org.ums.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("/academic/students/comment")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AdmissionCommentForStudentForStudentResource extends
    MutableAdmissionCommentForStudentResource {

  @GET
  @Path("/savedComments/semesterId/{semester-id}/receiptId/{receipt-Id}")
  public JsonObject getSavedCertificates(final @Context Request pRequest,
      final @PathParam("semester-id") int pSemesterId,
      final @PathParam("receipt-Id") String pReceiptId) {
    return mHelper.getStudentsSavedComments(pSemesterId, pReceiptId, mUriInfo);
  }
}
