package org.ums.academic.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.ums.enums.ProgramType;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by kawsu on 1/11/2017.
 */
@Component
@Path("/academic/students/certificateHistory")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AdmissionCertificatesOfStudentResource extends
    MutableAdmissionCertificatesOfStudentResource {

  // TODO Remove it from here and getUndertakenReport()
  private static final Logger mLogger = LoggerFactory
      .getLogger(AdmissionCertificateSubmissionResource.class);

  @GET
  @Path("/savedCertificates/semesterId/{semester-id}/receiptId/{receipt-Id}")
  public JsonObject getSavedCertificates(final @Context Request pRequest,
      final @PathParam("semester-id") int pSemesterId,
      final @PathParam("receipt-Id") String pReceiptId) {
    return mHelper.getStudentsSavedCertificates(pSemesterId, pReceiptId, mUriInfo);
  }

  // TODO remove it
  @GET
  @Path("/underTaken/programType/{program-type}/semesterId/{semester-Id}/receiptId/{receipt-Id}")
  @Produces("application/pdf")
  public StreamingOutput getUndertakenReport(final @PathParam("program-type") int pProgramType,
      final @PathParam("semester-Id") int pSemesterId,
      final @PathParam("receipt-Id") String pReceiptId, final @Context Request pRequest) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mHelper.getUndertakenForm(ProgramType.get(pProgramType), pSemesterId, pReceiptId,
              pOutputStream, pRequest, mUriInfo);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
