package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.resource.Resource;
import org.ums.response.type.GenericResponse;
import org.ums.services.academic.ProcessResult;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("/academic/processResult")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ProcessResultResource extends Resource {

  @Autowired
  ProcessResult mProcessResult;

  @GET
  @Path("/status/program/{program-id}/semester/{semester-id}/")
  public GenericResponse<TaskStatus> getResultProcessStatus(final @Context Request pRequest,
      final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester-id") Integer pSemesterId) throws Exception {
    return mProcessResult.status(pProgramId, pSemesterId);
  }
}
