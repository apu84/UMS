package org.ums.common.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.enums.QuotaType;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

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

  @GET
  @Path("/taletalkData/semester/{semester-id}/unit/{unit}/meritType/{merit-type}")
  public JsonObject getTaletalkData(final @Context Request pRequest,
      final @PathParam("semester-id") int pSemesterId, final @PathParam("unit") String pUnit,
      final @PathParam("merit-type") int pMeritType) {
    return mHelper.getTaletalkData(pSemesterId, QuotaType.get(pMeritType), pUnit, mUriInfo);
  }

  @GET
  @Path("/meritList/semester/{semester-id}/unit/{unit}/meritType/{merit-type}")
  public JsonObject getAdmissionMeritList(final @Context Request pRequest,
      final @PathParam("semester-id") int pSemesterId, final @PathParam("unit") String pUnit,
      final @PathParam("merit-type") int pMeritType) {
    return mHelper.getAdmissionMeritList(pSemesterId, QuotaType.get(pMeritType), pUnit, mUriInfo);
  }

}
