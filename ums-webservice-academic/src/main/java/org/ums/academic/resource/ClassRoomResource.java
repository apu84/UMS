package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.enums.ExamType;
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
public class ClassRoomResource extends MutableClassRoomResource {
  @Autowired
  ClassRoomManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() {
    return mResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/seat-plan/all")
  public JsonObject getAllForSeatPlan() {
    return mResourceHelper.getAllForSeatPlan(mUriInfo);
  }

  @GET
  @Path("/roomNo/{room-no}")
  public JsonObject getByRoomNo(final @Context Request pRequest, final @PathParam("room-no") String roomNo) {
    return mResourceHelper.getByRoomNo(roomNo, mUriInfo);
  }

  @GET
  @Path("/roomId/{room-id}")
  public JsonObject getByRoomId(final @Context Request pRequest, final @PathParam("room-id") Long roomId) {
    return mResourceHelper.getByRoomId(roomId, mUriInfo);
  }

  @GET
  @Path("/program")
  public JsonObject getByProgram(final @Context Request pRequest) {
    return mResourceHelper.getRooms(mUriInfo);
  }

  @GET
  @Path("/forRoutine/semester/{semester-id}")
  public JsonObject getRoomsForRoutine(final @Context Request pRequest, final @PathParam("semester-id") int pSemesterId) {
    return mResourceHelper.getRoomsBasedOnRoutine(pSemesterId, mUriInfo);
  }

  @GET
  @Path("/seatplan/semester/{semester-id}/examType/{exam-type}")
  public JsonObject getRoomsBasedOnSeatPlan(final @Context Request pRequest,
      final @PathParam("semester-id") int pSemesterId, final @PathParam("exam-type") int pProgramType) {
    return mResourceHelper.getRoomsBasedOnSeatPlan(pSemesterId, ExamType.get(pProgramType), mUriInfo);
  }

}
