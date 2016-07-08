package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.common.ResourceHelper;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.mutable.MutableParameterSetting;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;


public class MutableParameterSettingResource extends Resource {
  @Autowired
  ResourceHelper<ParameterSetting,MutableParameterSetting,String> mResourceHelper;

  @POST
  public Response createParameterSetting(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateParameterSetting(final @PathParam("object-id") String pObjectId,
                               final @Context Request pRequest,
                               final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
                               final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteParameterSetting(final @PathParam("object-id") String pObjectId) throws Exception {
    return mResourceHelper.delete(pObjectId);
  }
}
