package org.ums.common.report.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.report.generator.PasswordReportGenerator;
import org.ums.report.generator.AbstractReportGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Path("/credentialReport")
@Produces({"application/pdf"})
public class PasswordReport extends Resource {
  @Autowired
  PasswordReportGenerator mPasswordReportGenerator;

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public StreamingOutput get(final @Context Request pRequest, final @PathParam("object-id") String pObjectId) throws Exception {
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mPasswordReportGenerator.generateReport(AbstractReportGenerator.OutputType.PDF, output, pObjectId);
        } catch (Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
