package org.ums.common.report.resource;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/promotionList")
public class PromotionList extends Resource {
  @GET
  @Produces({"application/pdf"})
  @Path("/pdf/program/{program-id}/semester/{semester-id}")
  public StreamingOutput get(final @Context Request pRequest,
      final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester-id") Integer pSemesterId) throws Exception {
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {

        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
