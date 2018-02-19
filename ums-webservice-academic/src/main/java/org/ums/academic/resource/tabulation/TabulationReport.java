package org.ums.academic.resource.tabulation;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.tabulation.model.TabulationReportModel;
import org.ums.academic.tabulation.service.TabulationService;
import org.ums.resource.Resource;

@Component
@Path("/academic/tabulation/")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class TabulationReport {
  @Autowired
  TabulationService mTabulationService;

  @Autowired
  TabulationPdf mTabulationPdf;

  @GET
  @Produces({"application/pdf"})
  @Path("/report/program/{program-id}/semester/{semester-id}/year/{year}/academic-semester/{academic-semester}")
  public StreamingOutput get(final @Context Request pRequest, final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("year") Integer pYear,
      final @PathParam("academic-semester") Integer pAcademicSemester) {
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mTabulationPdf.createPdf(mTabulationService.getTabulation(pProgramId, pSemesterId, pYear, pAcademicSemester),
              output);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
