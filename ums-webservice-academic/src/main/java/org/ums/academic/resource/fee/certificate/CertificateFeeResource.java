package org.ums.academic.resource.fee.certificate;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.report.generator.testimonial.TestimonialGenerator;
import org.ums.resource.Resource;

import java.io.IOException;
import java.io.OutputStream;

@Component
@Path("/certificate-fee")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CertificateFeeResource extends Resource {
  @Autowired
  CertificateFeeHelper mCertificateFeeHelper;

  @Autowired
  TestimonialGenerator mTestimonialGenerator;

  @GET
  @Path("/attended-semesters")
  public JsonObject getAttendedSemesters() throws Exception {
    return mCertificateFeeHelper.getAttendedSemesters(getLoggedInUserId(), mUriInfo);
  }

  @POST
  @Path("/apply/semester/{semesterId}/category/{categoryId}")
  public Response applyForCertificate(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("semesterId") Integer pSemesterId, final @PathParam("categoryId") String pCategoryId)
      throws Exception {
    mCertificateFeeHelper.applyForCertificate(pHttpServletRequest, pCategoryId, getLoggedInUserId(), pSemesterId);
    return Response.ok().build();
  }

  @POST
  @Path("/apply/category/{categoryId}")
  public Response applyForCertificate(final @PathParam("categoryId") String pCategoryId) throws Exception {
    mCertificateFeeHelper.applyForCertificate(pCategoryId, getLoggedInUserId());
    return Response.ok().build();
  }

  @GET
  @Path("/testimonial/{student-id}")
  @Produces("application/pdf")
  public StreamingOutput getEmployeeCV(final @PathParam("student-id") String pStudentId, final @Context Request pRequest) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mTestimonialGenerator.createTestimonial(pStudentId, pOutputStream);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
