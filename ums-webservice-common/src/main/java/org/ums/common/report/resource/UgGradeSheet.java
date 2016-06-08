package org.ums.common.report.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.report.generator.PasswordReportGenerator;
import org.ums.common.report.generator.UgGradeSheetGenerator;
import org.ums.report.generator.AbstractReportGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Ifti on 07-Jun-16.
 */

@Component
@Path("/gradeReport")
@Produces({"application/pdf"})
public class UgGradeSheet extends Resource {
  @Autowired
  PasswordReportGenerator mPasswordReportGenerator;

  @GET
  //@Path("/single"+PATH_PARAM_OBJECT_ID)
  public StreamingOutput get(final @Context Request pRequest) throws Exception {
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          new UgGradeSheetGenerator().createPdf(output,"e://hello.pdf");
        } catch (Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }

}
