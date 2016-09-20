package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.ClassRoomManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("/academic/classroom")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ClassRoomResource  extends MutableClassRoomResource {
  @Autowired
  ClassRoomManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }


  @GET
  @Path("/roomNo/{room-no}")
  public JsonObject getByRoomNo(final @Context Request pRequest,
                                final @PathParam("room-no") String roomNo) throws Exception{
    return mResourceHelper.getByRoomNo(roomNo,mUriInfo);
  }

  @GET
  @Path("/roomId/{room-id}")
  public JsonObject getByRoomId(final @Context Request pRequest,
                                final @PathParam("room-id") Integer roomId) throws Exception{
    return mResourceHelper.getByRoomId(roomId,mUriInfo);
  }

  @GET
  @Path("/program")
  public JsonObject getByProgram(final @Context Request pRequest) throws Exception{
    return mResourceHelper.getRooms(mUriInfo);
  }

}
