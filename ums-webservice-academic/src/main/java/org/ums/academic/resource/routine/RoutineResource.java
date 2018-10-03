package org.ums.academic.resource.routine;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.logs.GetLog;
import org.ums.manager.routine.RoutineManager;
import org.ums.resource.Resource;
import org.ums.services.academic.routine.RoutineReportService;

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

  @Autowired
  RoutineReportService mRoutineReportService;

  private static Logger mLogger = org.slf4j.LoggerFactory.getLogger(RoutineResource.class);
  @Autowired
  RoutineManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mRoutineResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/routineForTeacher/employeeId/{employee-id}/semesterId/{semester-id}")
  @GetLog(message = "Accessed teacher's class routine")
  public JsonObject getRoutineForTeachers(final @Context HttpServletRequest pHttpServletRequest,
      @PathParam("employee-id") String pEmployeeId, @PathParam("semester-id") Integer pSemesterId) {
    return mRoutineResourceHelper.getRoutineForTeacher(pEmployeeId, pSemesterId, mUriInfo);
  }

  @GET
  @Path("/semester/{semesterId}/course/{courseId}")
  @GetLog(message = "Requested for semester and course wise routine")
  public JsonArray getRoutine(final @Context HttpServletRequest pHttpServletRequest,
      @PathParam("semesterId") Integer pSemesterId, @PathParam("courseId") String pCourseId) {
    return mRoutineResourceHelper.getRoutine(pSemesterId, pCourseId, mUriInfo);
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
  @Path("/semester-wise-report/semester-id/{semester-id}/program/{program-id}/year/{year}/semester/{semester}/section/{section}")
  @Produces("application/pdf")
  @GetLog(message = "Requested for semester wise Routine report")
  public StreamingOutput createSemesterWiseReport(final @Context HttpServletRequest pHttpServletRequest,
      @PathParam("semester-id") Integer pSemesterId, @PathParam("program-id") Integer pProgramId,
      @PathParam("year") int pYear, @PathParam("semester") int pSemester, @PathParam("section") String pSection) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mRoutineReportService.createSemesterWiseRoutine(pSemesterId, pProgramId, pYear, pSemester, pSection, output);

        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/room-wise-report/room-id/{room-id}/semester/{semester-id}")
  @Produces("application/pdf")
  @GetLog(message = "Requested for room wise Routine report")
  public StreamingOutput createRoomBasedReport(final @Context HttpServletRequest pHttpServletRequest,
      @PathParam("semester-id") Integer pSemesterId, @PathParam("room-id") Long pRoomId) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mRoutineReportService.createRoomBasedRoutine(pRoomId, pSemesterId, output);

        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/room-wise-report/semester/{semester-id}/program/{program-id}")
  @Produces("application/pdf")
  @GetLog(message = "Requested for room wise Routine report")
  public StreamingOutput createAllRoomBasedReport(final @Context HttpServletRequest pHttpServletRequest,
      @PathParam("semester-id") Integer pSemesterId, @PathParam("program-id") Integer pProgramid) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mRoutineReportService.createAllRoomBasedRoutine(pSemesterId, pProgramid, output);

        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/teacher-wise-report/teacher-id/{teacher-id}/semester/{semester-id}")
  @Produces("application/pdf")
  @GetLog(message = "Requested for course teacher based Routine report")
  public StreamingOutput createTeacherBasedReport(final @Context HttpServletRequest pHttpServletRequest,
      @PathParam("semester-id") Integer pSemesterId, @PathParam("teacher-id") String pTeacherId) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mRoutineReportService.createTeachersRoutine(pTeacherId, pSemesterId, output);

        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/teacher-wise-report/semester/{semester-id}/program/{program-id}")
  @Produces("application/pdf")
  @GetLog(message = "Requested for course teacher based Routine report")
  public StreamingOutput createAllTeacherBasedReport(final @Context HttpServletRequest pHttpServletRequest,
      @PathParam("semester-id") Integer pSemesterId, @PathParam("program-id") Integer pProgramId) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mRoutineReportService.createAllTeachersRoutine(pSemesterId, pProgramId, output);

        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/roomBasedRoutine/semester/{semester-id}/room/{room-id}")
  @GetLog(message = "Accessed room-based class routine")
  public JsonObject createRoomBasedRoutine(final @Context HttpServletRequest pHttpServletRequest,
      final @PathParam("semester-id") int pSemesterId, final @PathParam("room-id") int pRoomId,
      final @Context Request pRequest) {
    return mRoutineResourceHelper.getRoomBasedRoutine(pSemesterId, pRoomId, mUriInfo);
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

}
