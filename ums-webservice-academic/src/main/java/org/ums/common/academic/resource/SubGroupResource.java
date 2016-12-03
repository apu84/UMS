package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.SubGroupManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by My Pc on 5/5/2016.
 */

@Component
@Path("academic/subGroup")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SubGroupResource extends MutableSubGroupResource {

  @Autowired
  SubGroupManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mSubGroupResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/get/semesterId/{semesterId}/groupNo/{groupNo}/type/{type}")
  public JsonObject getBySemesterAndGroupNo(final @Context Request pRequest,
      final @PathParam("semesterId") int pSemesterId, final @PathParam("groupNo") int pGroupNo,
      final @PathParam("type") String type) {
    return mSubGroupResourceHelper.getBySemesterAndGroupNO(pSemesterId, pGroupNo,
        Integer.parseInt(type), pRequest, mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") int pObjectId)
      throws Exception {
    return mSubGroupResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }
}
