package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.ApplicationCCIResourceHelper;
import org.ums.resource.Resource;

import javax.annotation.PostConstruct;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by My Pc on 7/14/2016.
 */
public class MutableApplicationCCIResource extends Resource {

  @Autowired
  ApplicationCCIResourceHelper mHelper;

  @POST
  @Produces({MediaType.APPLICATION_JSON})
  public JsonObject createApplicationCCI(final JsonObject pJsonObject) {
    return mHelper.saveAndReturn(pJsonObject, mUriInfo);
  }

  @POST
  @Path("/appliedAndApproved/studentId/{student-id}/semesterId/{semester-id}")
  @Produces({MediaType.APPLICATION_JSON})
  public Response appliedAndApproved(final @PathParam("student-id") String pStudentId,
      final @PathParam("semester-id") Integer pSemesterid, final JsonObject pJsonObject) {
    return mHelper.appliedAndApproved(pStudentId, pSemesterid, pJsonObject, mUriInfo);
  }

  @POST
  @Path("/transactionId")
  @Produces({MediaType.APPLICATION_JSON})
  public Response cciApprovalbank(final JsonObject pJsonObject) {
    return mHelper.cciApproval(pJsonObject, mUriInfo);
  }

  @DELETE
  public Response delete() {
    return mHelper.deleteByStudentId(mUriInfo);
  }
}
