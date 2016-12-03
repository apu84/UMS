package org.ums.common.report.resource;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.report.generator.PromotionListGenerator;
import org.ums.common.report.generator.SemesterResultGenerator;
import org.ums.manager.ResultPublishManager;
import org.ums.resource.Resource;

@Component
@Path("/result")
public class ResultList extends Resource {
  @Autowired
  PromotionListGenerator mPromotionListGenerator;

  @Autowired
  SemesterResultGenerator mSemesterResultGenerator;

  @Autowired
  ResultPublishManager mResultPublishManager;

  @GET
  @Produces({"application/pdf"})
  @Path("/pdf/program/{program-id}/semester/{semester-id}")
  public StreamingOutput get(final @Context Request pRequest,
      final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester-id") Integer pSemesterId) {
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          if(mResultPublishManager.isResultPublished(pProgramId, pSemesterId)) {
            mPromotionListGenerator.createPdf(pProgramId, pSemesterId, output);
          }
          else {
            mSemesterResultGenerator.createPdf(pProgramId, pSemesterId, output);
          }

        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
