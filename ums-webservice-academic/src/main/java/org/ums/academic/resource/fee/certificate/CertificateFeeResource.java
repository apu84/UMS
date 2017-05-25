package org.ums.academic.resource.fee.certificate;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/certificate-fee")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CertificateFeeResource extends Resource {
  @Autowired
  CertificateFeeHelper mCertificateFeeHelper;

  @GET
  @Path("/attended-semesters/{studentId}")
  public JsonObject getDues(final @PathParam("studentId") String pStudentId) throws Exception {
    return mCertificateFeeHelper.getAttendedSemesters(pStudentId, mUriInfo);
  }

  @POST
  @Path("/apply/{studentId}/semester/{semesterId}/category/{categoryId}/")
  public Response applyForCertificate(final @PathParam("studentId") String pStudentId,
      final @PathParam("semesterId") Integer pSemesterId, final @PathParam("categoryId") String pCategoryId)
      throws Exception {
    mCertificateFeeHelper.applyForCertificate(pCategoryId, pStudentId, pSemesterId, mUriInfo);
    return Response.ok().build();
  }
}