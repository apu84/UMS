package org.ums.academic.resource.fee.installment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/installment-settings")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class InstallmentSettingResource extends Resource {
  @Autowired
  InstallmentSettingsHelper mInstallmentSettingsHelper;

  @GET
  @Path("/semester/{semester-id}")
  public JsonObject getInstallmentSettings(@PathParam("semester-id") Integer pSemesterId) throws Exception {
    return mInstallmentSettingsHelper.getInstallmentSettings(pSemesterId, mUriInfo);
  }

  @POST
  @Path("/semester/{semester-id}")
  public Response create(JsonObject pJsonObject) throws Exception {
    return mInstallmentSettingsHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateCourse(final @PathParam("object-id") Long pObjectId, final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject) throws Exception {
    return mInstallmentSettingsHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }
}
