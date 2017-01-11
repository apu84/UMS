package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.SubGroupResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by My Pc on 5/5/2016.
 */
public class MutableSubGroupResource extends Resource {

  @Autowired
  SubGroupResourceHelper mSubGroupResourceHelper;

  @POST
  public Response createSubGroup(final JsonObject pJsonObject) {
    return mSubGroupResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  public Response updateSubGroup(final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject)
      throws Exception {
    return mSubGroupResourceHelper.put(pJsonObject.getInt("id"), pRequest, pIfMatchHeader,
        pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteSubGroup(final @PathParam("object-id") int objectId) throws Exception {
    return mSubGroupResourceHelper.delete(objectId);
  }

  @PUT
  @Path("/save/semester/{semesterId}/groupNo/{groupNo}/type/{type}")
  public Response saveAllSubGroupInfo(final @PathParam("semesterId") String pSemesterId,
      final @PathParam("groupNo") String pGroupNo, final @PathParam("type") String pType,
      final JsonObject pJsonObject) {
    return mSubGroupResourceHelper.save(Integer.parseInt(pSemesterId), Integer.parseInt(pGroupNo),
        Integer.parseInt(pType), pJsonObject);
  }

  @DELETE
  @Path("/semesterId/{semesterId}/groupNo/{groupNo}/type/{type}")
  public Response deleteBySemesterAndGroup(final @PathParam("semesterId") int semesterId,
      final @PathParam("groupNo") int groupNo, final @PathParam("type") int pType) {
    return mSubGroupResourceHelper.deleteBySemesterAndGroup(semesterId, groupNo, pType);
  }
}
