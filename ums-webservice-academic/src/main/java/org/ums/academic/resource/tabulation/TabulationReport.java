package org.ums.academic.resource.tabulation;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

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

  @GET
  @Path("/report")
  public TabulationReportModel getBySyllabus(final @Context Request pRequest) {
    return mTabulationService.getTabulation(110100, 11012016, 2, 1);
  }
}
