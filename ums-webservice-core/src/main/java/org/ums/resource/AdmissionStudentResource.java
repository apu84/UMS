package org.ums.resource;

import org.springframework.stereotype.Component;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;

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
  @Path("/taletalkData/semester/{semester-id}/programType/{program-type}")
  public JsonObject getTaletalkData(final @Context Request pRequest,
      final @PathParam("semester-id") int pSemesterId,
      final @PathParam("program-type") int pProgramType) {
    return mHelper.getTaletalkData(pSemesterId, ProgramType.get(pProgramType), mUriInfo);
  }

  @GET
  @Path("/taletalkData/semester/{semester-id}/programType/{program-type}/unit/{unit}/meritType/{merit-type}")
  public JsonObject getTaletalkData(final @Context Request pRequest,
      final @PathParam("semester-id") int pSemesterId,
      final @PathParam("program-type") int pProgramType, final @PathParam("unit") String pUnit,
      final @PathParam("merit-type") int pMeritType) {
    return mHelper.getTaletalkData(pSemesterId, ProgramType.get(pProgramType),
        QuotaType.get(pMeritType), pUnit, mUriInfo);
  }

  @GET
  @Path("/meritList/semester/{semester-id}/programType/{program-type}/unit/{unit}/meritType/{merit-type}")
  public JsonObject getAdmissionMeritList(final @Context Request pRequest,
      final @PathParam("semester-id") int pSemesterId,
      final @PathParam("program-type") int pProgramType, final @PathParam("unit") String pUnit,
      final @PathParam("merit-type") int pMeritType) {
    return mHelper.getAdmissionMeritList(pSemesterId, ProgramType.get(pProgramType),
        QuotaType.get(pMeritType), pUnit, mUriInfo);
  }

  @GET
  @Path("/statistics/semester/{semester-id}/programType/{program-type}/unit/{unit}/meritType/{merit-type}")
  public JsonObject getCurrentStatistics(final @Context Request pRequest,
      final @PathParam("semester-id") int pSemesterId,
      final @PathParam("program-type") int pProgramType, final @PathParam("unit") String pUnit,
      final @PathParam("merit-type") int pMeritType) {
    return mHelper.getCurrentDepartmentAllocationStatistics(pSemesterId,
        ProgramType.get(pProgramType), QuotaType.get(pMeritType), pUnit, mUriInfo);
  }

  // kawsurilu

  @GET
  @Path("/candidatesList/programType/{program-Type}/semester/{semester-Id}")
  public JsonObject getAllCandidates(@PathParam("program-Type") int pProgramType,
      @PathParam("semester-Id") int pSemesterId, final @Context Request pRequest) {
    return mHelper.getCandidatesList(ProgramType.get(pProgramType), pSemesterId, mUriInfo);
  }

  //

  @GET
  @Path("/semester/{semester-id}/programType/{program-type}/receiptId/{receipt-id}")
  public JsonObject getStudentByReceiptId(@PathParam("semester-id") int pSemesterId,
      @PathParam("program-type") int pProgramType, @PathParam("receipt-id") String pReceiptId,
      final @Context Request pRequest) {
    return mHelper.getAdmissionStudentByReceiptId(pSemesterId, ProgramType.get(pProgramType),
        pReceiptId, mUriInfo);
  }

}
