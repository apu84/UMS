package org.ums.common.academic.resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.ums.manager.RoutineManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Path("academic/routine")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class RoutineResource extends MutableRoutineResource {

  private static Logger mLogger = org.slf4j.LoggerFactory.getLogger(RoutineResource.class);
  @Autowired
  RoutineManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mRoutineResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/routineForTeacher")
  public JsonObject getRoutineForTeachers(final @Context Request pRequest) {

    return mRoutineResourceHelper.getRoutineForTeacher(pRequest, mUriInfo);
  }

  @GET
  @Path("/routineReportTeacher")
  @Produces("application/pdf")
  public StreamingOutput createTeacherRoutineReport(final @Context Request pRequest) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mRoutineResourceHelper.getRoutineReportForTeacher(pOutputStream, pRequest, mUriInfo);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/roomBasedRoutine/semester/{semester-id}/room/{room-id}")
  @Produces("application/pdf")
  public StreamingOutput createRoomBasedRoutineReport(
      final @PathParam("semester-id") int pSemesterId, final @PathParam("room-id") int pRoomId,
      final @Context Request pRequest) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mRoutineResourceHelper.getRoomBasedRoutineReport(pOutputStream, pSemesterId, pRoomId,
              pRequest, mUriInfo);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/routineForStudent")
  public JsonObject getRoutineForStudents(final @Context Request pRequest,
      final @PathParam("semesterId") String semesterId,
      final @PathParam("programId") String programId) {
    return mRoutineResourceHelper.getRoutineForStudent();
  }

  @GET
  @Path("/routineForEmployee/semester/{semesterId}/year/{year}/semester/{semester}/section/{section}")
  public JsonObject getRoutineForEmployee(final @Context Request pRequest,
      final @PathParam("semesterId") String semesterId, final @PathParam("year") String year,
      final @PathParam("semester") String semester, final @PathParam("section") String section) {
    return mRoutineResourceHelper.getRoutineForEmployee(Integer.parseInt(semesterId),
        Integer.parseInt(year), Integer.parseInt(semester), section, pRequest, mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest,
      final @PathParam("object-id") String pObjectId) throws Exception {
    return mRoutineResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }
}
