package org.ums.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
@Component
@Path("/academic/admissionTotalSeat")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AdmissionTotalSeatResource extends MutableAdmissionTotalSeatResource {

  @GET
  @Path("/semester/{semester-id}/programType/{program-type}/quota/{quota-type}")
  public JsonObject getTotalSeatInfo(final @Context Request pRequest, final @PathParam("semester-id") int pSemesterId,
      final @PathParam("program-type") int pProgramTyoe, final @PathParam("quota-type") int pQuotaType) {
    return mHelper.getAdmissionTotalSeat(pSemesterId, ProgramType.get(pProgramTyoe), QuotaType.get(pQuotaType),
        mUriInfo);
  }
}
