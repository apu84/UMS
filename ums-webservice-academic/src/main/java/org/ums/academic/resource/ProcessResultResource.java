package org.ums.academic.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.resource.Resource;
import org.ums.response.type.GenericResponse;
import org.ums.services.academic.ProcessResult;

@Component
@Path("/academic")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ProcessResultResource extends Resource {

  @Autowired
  ProcessResult mProcessResult;

  @GET
  @Path("/processResult/status/program/{program-id}/semester/{semester-id}/")
  public GenericResponse<TaskStatus> getResultProcessStatus(final @Context Request pRequest,
      final @PathParam("program-id") Integer pProgramId,
      final @PathParam("semester-id") Integer pSemesterId) {
    return mProcessResult.status(pProgramId, pSemesterId);
  }

  @POST
  @Path("/processResult/program/{program-id}/semester/{semester-id}/")
  public Response processResult(final @PathParam("program-id") int pProgramId,
      final @PathParam("semester-id") int pSemesterId) {
    mProcessResult.process(pProgramId, pSemesterId);
    return Response.ok().build();
  }

  @POST
  @Path("/publishResult/program/{program-id}/semester/{semester-id}/")
  public Response publishResult(final @PathParam("program-id") int pProgramId,
      final @PathParam("semester-id") int pSemesterId) {
    mProcessResult.publishResult(pProgramId, pSemesterId);
    return Response.ok().build();
  }
}
