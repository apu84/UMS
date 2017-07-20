package org.ums.academic.resource.fee.installment;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/installment-setting")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class InstallmentSettingResource extends Resource {
  @Autowired
  InstallmentSettingsHelper mInstallmentSettingsHelper;

  @GET
  @Path("/{semester-id}")
  public JsonObject getInstallmentSettings(@PathParam("semester-id") Integer pSemesterId) throws Exception {
    return mInstallmentSettingsHelper.getInstallmentSettings(pSemesterId, mUriInfo);
  }

  @GET
  @Path("/{semester-id}/date-setting")
  public JsonArray getInstallmentDateSettings(@PathParam("semester-id") Integer pSemesterId) throws Exception {
    return mInstallmentSettingsHelper.getInstallmentDateSettings(pSemesterId);
  }

  @POST
  public Response create(JsonObject pJsonObject) throws Exception {
    return mInstallmentSettingsHelper.post(pJsonObject, mUriInfo);
  }

  @POST
  @Path("/{semester-id}/date-setting")
  public Response dateSetting(@PathParam("semester-id") Integer pSemesterId, JsonArray pJsonArray) throws Exception {
    return mInstallmentSettingsHelper.dateSetting(pSemesterId, pJsonArray);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateCourse(final @PathParam("object-id") Long pObjectId, final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject) throws Exception {
    return mInstallmentSettingsHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }
}
