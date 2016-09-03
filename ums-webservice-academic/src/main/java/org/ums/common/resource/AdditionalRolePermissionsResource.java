package org.ums.common.resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.resource.helper.AdditionalRolePermissionsHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/additionalRolePermissions")
@Consumes(Resource.MIME_TYPE_JSON)
@Produces(Resource.MIME_TYPE_JSON)
public class AdditionalRolePermissionsResource extends Resource {
  @Autowired
  AdditionalRolePermissionsHelper mAdditionalRolePermissionsHelper;

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public JsonObject getAdditionalRolePermissions(final @Context Request pRequest,
                                          final @PathParam("object-id") String pUserId) throws Exception {
    return mAdditionalRolePermissionsHelper
        .getUserAdditionalRolePermissionsByAssignedBy(pUserId, SecurityUtils.getSubject().getPrincipal().toString(), mUriInfo);
  }

  @POST
  public Response createAdditionalRolePermissions(final JsonObject pJsonObject) throws Exception {
    return mAdditionalRolePermissionsHelper.post(pJsonObject, mUriInfo);
  }

}
