package org.ums.academic.resource.routine;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.logs.GetLog;
import org.ums.manager.routine.RoutineManager;
import org.ums.resource.Resource;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
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
  @GetLog(message = "Accessed teacher's class routine")
  public JsonObject getRoutineForTeachers(final @Context HttpServletRequest pHttpServletRequest) {
    return mRoutineResourceHelper.getRoutineForTeacher(mUriInfo);
  }

  @GET
  @Path("/routineReportTeacher")
  @Produces("application/pdf")
  @GetLog(message = "Accessed teacher's class routine report (PDF)")
  public StreamingOutput createTeacherRoutineReport(final @Context HttpServletRequest pHttpServletRequest) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mRoutineResourceHelper.getRoutineReportForTeacher(pOutputStream);
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
  @GetLog(message = "Accessed room-based class routine report (PDF)")
  public StreamingOutput createRoomBasedRoutineReport(final @Context HttpServletRequest pHttpServletRequest,
      final @PathParam("semester-id") int pSemesterId, final @PathParam("room-id") int pRoomId,
      final @Context Request pRequest) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mRoutineResourceHelper.getRoomBasedRoutineReport(pOutputStream, pSemesterId, pRoomId);
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
      final @PathParam("semesterId") String semesterId, final @PathParam("programId") String programId) {
    return mRoutineResourceHelper.getRoutineForStudent();
  }

  @GET
  @Path("/semester/{semesterId}/program/{programId}/year/{year}/semester/{semester}/section/{section}")
  @GetLog(message = "Accessed class routine report for employee")
  public JsonArray getRoutineForEmployee(final @Context HttpServletRequest pHttpServletRequest,
      final @PathParam("semesterId") int semesterId, final @PathParam("programId") int programId,
      final @PathParam("year") int year, final @PathParam("semester") int semester,
      final @PathParam("section") String section) {
    return mRoutineResourceHelper.getRoutine(semesterId, programId, year, semester, section, mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context HttpServletRequest pHttpServletRequest, @Context Request pRequest,
      final @PathParam("object-id") String pObjectId) throws Exception {
    return mRoutineResourceHelper.get(Long.parseLong(pObjectId), pRequest, mUriInfo);
  }
}
