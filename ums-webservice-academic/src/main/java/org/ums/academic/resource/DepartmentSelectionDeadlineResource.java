package org.ums.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 29-Apr-17.
 */
@Component
@Path("/academic/department-selection-deadline")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class DepartmentSelectionDeadlineResource extends MutableDepartmentSelectionDeadlineResource {

  @GET
  @Path("/semester/{semester-id}/quota/{quota}/unit/{unit}")
  public JsonObject getDeadline(final @Context Request pRequest, final @PathParam("semester-id") int pSemesterId,
      final @PathParam("quota") String pQuota, final @PathParam("unit") String pUnit) {
    return mHelper.getDeadline(pSemesterId, pQuota, pUnit, mUriInfo);
  }

}
