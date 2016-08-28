package org.ums.common.academic.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.academic.resource.helper.SeatPlanResourceHelper;
import org.ums.manager.SeatPlanManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;

/**
 * Created by My Pc on 5/8/2016.
 */
@Component
@Path("/academic/seatplan")
public class SeatPlanResource extends MutableSeatPlanResource{
  private static final Logger mLogger = LoggerFactory.getLogger(SeatPlanResource.class);
  private static final String FILE_PATH = "I:/pdf/seat_plan_report.pdf";
  @Autowired
  SeatPlanManager mManager;



  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception{
    return mSeatPlanResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/semesterId/{semesterId}/groupNo/{groupNo}/type/{type}/examDate/{exam-date}")
  @Produces("application/pdf")
  public StreamingOutput createOrViewSeatPlan(final @Context Request pRequest, final @PathParam("semesterId") String pSemesterId,
                                                       final @PathParam("groupNo") String pGroupNo,
                                                       final @PathParam("type") String pType,
                                                       final @PathParam("exam-date") String pExamDate)throws Exception{

    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try{
          mSeatPlanResourceHelper.createOrCheckSeatPlanAndReturnRoomList(
              Integer.parseInt(pSemesterId),Integer.parseInt(pGroupNo),Integer.parseInt(pType),pExamDate,pOutputStream,pRequest,mUriInfo
          );
        }catch (Exception e){
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
                                                          final @PathParam("program-type") Integer pProgramType,
                                                          final @PathParam("semester-id") Integer pSemesterId,
                                                          final @PathParam("exam-type") Integer pExamType,
                                                          final @PathParam("exam-date") String pExamDate) throws Exception{
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try{
          mSeatPlanResourceHelper.getSeatPlanAttendenceSheetReport(pProgramType,pSemesterId,pExamType,pExamDate,pOutputStream,pRequest,mUriInfo);
        }catch(Exception e){
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
                                                          final @PathParam("program-type") Integer pProgramType,
                                                          final @PathParam("semester-id") Integer pSemesterId,
                                                          final @PathParam("exam-type") Integer pExamType,
                                                          final @PathParam("exam-date") String pExamDate) throws Exception{
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try{
          mSeatPlanResourceHelper.getSeatPlanTopSheetReport(pProgramType,pSemesterId,pExamType,pExamDate,pOutputStream,pRequest,mUriInfo);
        }catch(Exception e){
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }



  @GET
  @Path("sticker/programType/{program-type}/semesterId/{semester-id}/examType/{exam-type}/examDate/{exam-date}")
  @Produces("application/pdf")
  public StreamingOutput getSeatStudentStickerReport(final @Context Request pRequest,
                                                   final @PathParam("program-type") Integer pProgramType,
                                                   final @PathParam("semester-id") Integer pSemesterId,
                                                   final @PathParam("exam-type") Integer pExamType,
                                                   final @PathParam("exam-date") String pExamDate) throws Exception{
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try{
          mSeatPlanResourceHelper.getSeatPlanStudentStickerReport(pProgramType,pSemesterId,pExamType,pExamDate,pOutputStream,pRequest,mUriInfo);
        }catch(Exception e){
          mLogger.error(e.getMessage());
          throw new WebApplicationException(e);
        }
      }
    };
  }



  @GET
  @Path("/studentId/{student-id}/semesterId/{semester-id}")
  public JsonObject getSeatPlanInfoForSeatPlanViewing(final @Context Request pRequest,
                                                      final @PathParam("student-id") String pStudentId,
                                                      final @PathParam("semester-id") Integer pSemesterId) throws Exception{
    return mSeatPlanResourceHelper.getSeatPlanForStudentsSeatPlanView(pStudentId,pSemesterId,mUriInfo);
  }

  @GET
  @Path("/studentId/{student-id}/semesterId/{semester-id}/examDate/{exam-date}")
  public JsonObject getSeatPlanForStudentAndCCIExam(final @Context Request pRequest,
                                                      final @PathParam("student-id") String pStudentId,
                                                      final @PathParam("semester-id") Integer pSemesterId,
                                                      final @PathParam("exam-date") String pExamDate) throws Exception{
    return mSeatPlanResourceHelper.getSeatPlanForStudentAndCCIExam(pStudentId,pSemesterId,pExamDate,mUriInfo);
  }

}
