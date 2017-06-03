package org.ums.academic.resource.fee.semesterfee;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/semester-fee")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SemesterFee extends Resource {
  @Autowired
  SemesterFeeHelper mSemesterFeeHelper;

  @GET
  @Path("/{semesterId}")
  public UGSemesterFee.UGSemesterFeeResponse getReadmissionStatus(final @Context Request pRequest,
      final @PathParam("semesterId") int pSemesterId) throws Exception {
    return null;
  }
}
