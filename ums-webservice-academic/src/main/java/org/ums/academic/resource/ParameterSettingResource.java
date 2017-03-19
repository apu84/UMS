package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.helper.ParameterSettingResourceHelper;
import org.ums.manager.ParameterSettingManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by My Pc on 3/15/2016.
 */
@Component
@Path("/academic/parameterSetting")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ParameterSettingResource extends MutableParameterSettingResource {
  @Autowired
  ParameterSettingResourceHelper mResourceHelper;

  @Autowired
  ParameterSettingManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() {
    return mResourceHelper.getAllInfo(mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") String pObjectId) throws Exception {
    return mResourceHelper.get(Long.parseLong(pObjectId), pRequest, mUriInfo);
  }

  @GET
  @Path(("/semester/{semesterId}"))
  public JsonObject getBySemester(final @Context Request pRequest, final @PathParam("semesterId") Integer pSemesterId) {
    return mResourceHelper.getBySemester(pSemesterId, pRequest, mUriInfo);
  }

  @GET
  @Path(("/parameter/{parameterId}/semester/{semesterId}"))
  public JsonObject getByParameterIdAndSemester(final @Context Request pRequest,
      final @PathParam("parameterId") Integer pParameterId, final @PathParam("semesterId") Integer pSemesterId) {
    return mResourceHelper.getByParameterIdAndSemesterId(pParameterId, pSemesterId, pRequest, mUriInfo);
  }

  @GET
  @Path(("/parameter/{parameter}"))
  public JsonObject getByParameterAndSemester(final @Context Request pRequest,
      final @PathParam("parameter") String parameter) {
    return mResourceHelper.getByParameterAndSemesterId(parameter, pRequest, mUriInfo);
  }
}
