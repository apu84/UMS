package org.ums.common.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 17-Dec-16.
 */

@Component
@Path("/academic/admission")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AdmissionStudentResource extends MutableAdmissionStudentResource {

  @GET
  @Path("/taletalkData/semester/{semester-id}")
  public JsonObject getTaletalkData(final @Context Request pRequest,
      final @PathParam("semester-id") int pSemesterId) {
    return mHelper.getTaletalkData(pSemesterId, mUriInfo);
  }
}