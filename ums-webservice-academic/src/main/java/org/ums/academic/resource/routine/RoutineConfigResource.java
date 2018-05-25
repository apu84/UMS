package org.ums.academic.resource.routine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.routine.MutableRoutineConfig;
import org.ums.logs.GetLog;
import org.ums.logs.PutLog;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("academic/routine")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class RoutineConfigResource extends Resource {

  @Autowired
  private RoutineConfigResourceHelper mHelper;

  @PUT
  @PutLog(message = "Request for saving routine config")
  public Response saveRoutineConfig(@Context HttpServletRequest httpServletRequest, final JsonObject pJsonObject,
      @Context Request pRequest) throws Exception {
    MutableRoutineConfig routineConfig = mHelper.createOrUpdate(pJsonObject, mUriInfo);
    return mHelper.get(routineConfig.getId(), pRequest, mUriInfo);
  }

  @GET
  @GetLog(message = "Requested for routine config")
  @Path("/semester/{semester-id}/program/{program-id}")
  public JsonObject getRoutineConfig(@Context HttpServletRequest httpServletRequest,
      @PathParam("semester-id") Integer pSemesterId, @PathParam("program-id") Integer pProgramId) {
    return mHelper.get(pSemesterId, pProgramId, mUriInfo);
  }
}
