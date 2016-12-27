package org.ums.common.report.resource;

import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.academic.resource.helper.AdmissionStudentResourceHelper;
import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.generator.XlsGenerator;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 19-Dec-16.
 */
@Component
@Path("/admission/xlx")
@Consumes(Resource.MIME_TYPE_JSON)
public class AdmissionXls {

  @Autowired
  XlsGenerator mXlsGenerator;

  @Autowired
  AdmissionStudentResourceHelper mHelper;

  @GET
  @Path("/taletalkData/semester/{semester-id}")
  @Produces("application/vnd.ms-excel")
  public StreamingOutput getTaletalkXlsFormat(final @Context Request pRequest,
      @PathParam("semester-id") int pSemesterId) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mHelper.getTaletalkDataXlesFormat(pOutputStream, pSemesterId);

        } catch(Exception e) {
          throw new WebApplicationException(e);
        }

      }
    };
  }

  @GET
  @Path("/meritList/semester/{semester-id}")
  @Produces("application/vnd.ms-excel")
  public StreamingOutput getMeritListXlsFileFormat(final @Context Request pRequest,
      @PathParam("semester-id") int pSemesterId) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mHelper.getMeritLisXlesFormat(pOutputStream, pSemesterId);

        } catch(Exception e) {
          throw new WebApplicationException(e);
        }

      }
    };
  }

}
