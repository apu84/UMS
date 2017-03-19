package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.ClassRoomResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class MutableClassRoomResource extends Resource {

  @Autowired
  ClassRoomResourceHelper mResourceHelper;

  @POST
  public Response createClassRoom(final JsonObject pJsonObject) {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

  @PUT
  public Response updateSemester(final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.put(Long.parseLong(pJsonObject.getString("id")), pRequest, pIfMatchHeader, pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteSemester(final @PathParam("object-id") String pObjectId) throws Exception {
    return mResourceHelper.delete(Long.parseLong(pObjectId));
  }

}
