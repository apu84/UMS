package org.ums.academic.resource.certificates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.report.generator.certificates.CertificateReportGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 07-Nov-17.
 */
@Component
@Path("/certificate")
public class CertificateResource {
  private final Logger mLogger = LoggerFactory.getLogger(CertificateResource.class);

  @Autowired
  CertificateReportGenerator mCertificateReportGenerator;

  @GET
  @Path("/report")
  @Produces("application/pdf")
  public StreamingOutput createCertificateReport(@QueryParam("feeCategory") String pFeeCategory,
      @QueryParam("studentId") String pStudentId, @QueryParam("semesterId") Integer pSemesterId) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mCertificateReportGenerator.createReport(pFeeCategory, pStudentId, pSemesterId, output);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
