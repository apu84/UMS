package org.ums.common.academic.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
   /* StreamingOutput strem =  mSeatPlanResourceHelper.createOrCheckSeatPlanAndReturnRoomList(
        Integer.parseInt(pSemesterId),Integer.parseInt(pGroupNo),Integer.parseInt(pType),pRequest,mUriInfo
    );


    File file = new File(FILE_PATH );

    Response.ResponseBuilder response = Response.ok((Object) file);
    //response.header("SeatPlan Report","attachment;filename=seatPlanReport.pdf");


    return  response.build();*/

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

}
