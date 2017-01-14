package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.AdmissionStudentResourceHelper;
import org.ums.enums.DepartmentSelectionType;
import org.ums.enums.ProgramType;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 17-Dec-16.
 */
public class MutableAdmissionStudentResource extends Resource {

  @Autowired
  AdmissionStudentResourceHelper mHelper;

  @POST
  @Path("/taletalkData/semester/{semester-id}/programType/{program-type}")
  public Response saveTaletalkData(@PathParam("semester-id") int pSemesterId,
      @PathParam("program-type") int pProgramType, final JsonObject pJsonObject) throws Exception {
    return mHelper.postTaletalkData(pJsonObject, pSemesterId, ProgramType.get(pProgramType),
        mUriInfo);
  }

  @PUT
  @Path("/meritListUpload")
  public Response saveMeritList(final JsonObject pJsonObject) throws Exception {
    return mHelper.saveMeritListData(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/verificationStatus")
  public Response verificationStatus(final JsonObject pJsonObject) {
    return mHelper.putVerificationStatus(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/departmentSelectionStatus/{department-selection-status}")
  public JsonObject saveAndGetNextStudent(
      @PathParam("department-selection-status") int pDepartmentSelectionStatus,
      final JsonObject pJsonObject, final @Context Request pRequest) throws Exception {
    return mHelper.saveDepartmentSelectionInfoAndRetrieveNextStudent(pJsonObject,
        DepartmentSelectionType.get(pDepartmentSelectionStatus), mUriInfo);
  }

}
