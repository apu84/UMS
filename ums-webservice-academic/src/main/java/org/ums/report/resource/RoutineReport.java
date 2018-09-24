package org.ums.report.resource;

import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.routine.RoutineResourceHelper;
import org.ums.generator.XlsGenerator;
import org.ums.logs.GetLog;
import org.ums.resource.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 05-Sep-18.
 */
@Component
@Path("academic/routine")
@Consumes(Resource.MIME_TYPE_JSON)
public class RoutineReport {
  @Autowired
  RoutineResourceHelper mRoutineResourceHelper;
  @Autowired
  XlsGenerator mXlsGenerator;

  @GET
  @Path("/routine-template")
  @GetLog(message = "Requested for downloading routine Template")
  @Produces({"application/vnd.ms-excel"})
  public StreamingOutput get(@Context HttpServletRequest pHttpServletRequest, final @Context Request pRequest) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          InputStream inputStream = RoutineReport.class.getResourceAsStream("/report/xls/template/RoutineTemplate.xls");
          org.jxls.common.Context context = new org.jxls.common.Context();
          JxlsHelper.getInstance().processTemplate(inputStream, output, context);
        } catch(Exception e) {

        }
      }
    };
  }

}
