package org.ums.academic.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.enums.ExamType;
import org.ums.manager.SeatPlanManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by My Pc on 5/8/2016.
 */
@Component
@Path("/academic/seatplan")
public class SeatPlanResource extends MutableSeatPlanResource {
  private static final Logger mLogger = LoggerFactory.getLogger(SeatPlanResource.class);
  private static final String FILE_PATH = "I:/pdf/seat_plan_report.pdf";
  @Autowired
  SeatPlanManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mSeatPlanResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path("?semester-id={semester-id}&exam-type={exam-type}&exam-date={exam-date}&student-id={student-id}")
  public JsonObject getSeatPlanOfStudent(@QueryParam("semester-id") Integer semesterId,
                                         @QueryParam("exam-tye") Integer examType, @QueryParam("exam-date") String examDate,
                                         @QueryParam("student-id") String studentId) {
    return mSeatPlanResourceHelper.getSeatPlanForStudent(semesterId, examType,
        examDate, studentId, mUriInfo);
  }

  @GET
  @Path("/semesterId/{semesterId}/groupNo/{groupNo}/type/{type}/examDate/{exam-date}")
  @Produces("application/pdf")
  public StreamingOutput createOrViewSeatPlan(final @Context Request pRequest,
      final @PathParam("semesterId") String pSemesterId, final @PathParam("groupNo") String pGroupNo,
      final @PathParam("type") String pType, final @PathParam("exam-date") String pExamDate) {

    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mSeatPlanResourceHelper.createOrCheckSeatPlanAndReturnRoomList(Integer.parseInt(pSemesterId),
              Integer.parseInt(pGroupNo), Integer.parseInt(pType), pExamDate, pOutputStream, pRequest, mUriInfo);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/programType/{program-type}/semesterId/{semester-id}/examType/{exam-type}/examDate/{exam-date}")
  @Produces("application/pdf")
  public StreamingOutput getSeatPlanAttendenceSheetReport(final @Context Request pRequest,
      final @PathParam("program-type") Integer pProgramType, final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("exam-type") Integer pExamType, final @PathParam("exam-date") String pExamDate) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mSeatPlanResourceHelper.getSeatPlanAttendenceSheetReport(pProgramType, pSemesterId, pExamType, pExamDate,
              pOutputStream, pRequest, mUriInfo);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("topsheet/programType/{program-type}/semesterId/{semester-id}/examType/{exam-type}/examDate/{exam-date}")
  @Produces("application/pdf")
  public StreamingOutput getSeatPlanTopSheetReport(final @Context Request pRequest,
      final @PathParam("program-type") Integer pProgramType, final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("exam-type") Integer pExamType, final @PathParam("exam-date") String pExamDate) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mSeatPlanResourceHelper.getSeatPlanTopSheetReport(pProgramType, pSemesterId, pExamType, pExamDate,
              pOutputStream, pRequest, mUriInfo);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("sticker/programType/{program-type}/semesterId/{semester-id}/examType/{exam-type}/examDate/{exam-date}/roomId/{room-id}")
  @Produces("application/pdf")
  public StreamingOutput getSeatStudentStickerReport(final @Context Request pRequest,
      final @PathParam("program-type") Integer pProgramType, final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("exam-type") Integer pExamType, final @PathParam("exam-date") String pExamDate,
      final @PathParam("room-id") int pRoomId) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mSeatPlanResourceHelper.getSeatPlanStudentStickerReport(pProgramType, pSemesterId, pExamType, pExamDate,
              pRoomId, pOutputStream, pRequest, mUriInfo);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("sittingArrangement/semesterId/{semester-id}/examType/{exam-type}")
  @Produces("application/pdf")
  public StreamingOutput getSeatPlanSittingArrangement(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("exam-type") Integer pExamType) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mSeatPlanResourceHelper.getSeatPlanSittingArrangement(pSemesterId, ExamType.get(pExamType), pOutputStream,
              pRequest, mUriInfo);
        } catch(Exception e) {
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/studentId/{student-id}/semesterId/{semester-id}")
  public JsonObject getSeatPlanInfoForSeatPlanViewing(final @Context Request pRequest,
      final @PathParam("student-id") String pStudentId, final @PathParam("semester-id") Integer pSemesterId) {
    return mSeatPlanResourceHelper.getSeatPlanForStudentsSeatPlanView(pStudentId, pSemesterId, mUriInfo);
  }

  @GET
  @Path("/studentId/{student-id}/semesterId/{semester-id}/examDate/{exam-date}")
  public JsonObject getSeatPlanForStudentAndCCIExam(final @Context Request pRequest,
      final @PathParam("student-id") String pStudentId, final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("exam-date") String pExamDate) {
    return mSeatPlanResourceHelper.getSeatPlanForStudentAndCCIExam(pStudentId, pSemesterId, pExamDate, mUriInfo);
  }

}
