package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.report.generator.EmpExamAttendanceGenerator;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
@Component
@Path("academic/empExamAttendance")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class EmpExamAttendanceResource extends MutableEmpExamAttendanceResource {
  @Autowired
  EmpExamAttendanceGenerator mEmpExamAttendanceGenerator;

  @GET
  @Path("/getEmpExamAttendanceList/semesterId/{Semester-id}/examType/{exam-type}")
  public JsonObject getExpelInfoList(@Context Request pRequest, @PathParam("Semester-id") Integer pSemesterId,
      @PathParam("exam-type") Integer pExamType) {
    return mHelper.getEmpExamAttendanceInfo(pSemesterId, pExamType, pRequest, mUriInfo);
  }

  @GET
  @Path("/getMemorandumReport/semesterId/{semester-id}/examType/{exam-type}")
  @Produces("application/pdf")
  public StreamingOutput createRoomMemorandum(@PathParam("semester-id") Integer pSemesterId,
      @PathParam("exam-type") Integer pExamType) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mEmpExamAttendanceGenerator.createRoomMemorandum(pSemesterId, pExamType, output);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/getEmployeeAttendantReport/semesterId/{semester-id}/examType/{exam-type}/examDate/{exam-date}/deptId/{dept-id}")
  @Produces("application/pdf")
  public StreamingOutput createEmployeeReport(@PathParam("semester-id") Integer pSemesterId,
      @PathParam("exam-type") Integer pExamType, @PathParam("exam-date") String pExamDate,
      @PathParam("dept-id") String pDeptId) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mEmpExamAttendanceGenerator.createEmployeeAttendantList(pSemesterId, pExamType, pExamDate, pDeptId, output);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/getStaffAttendantReport/semesterId/{semester-id}/examType/{exam-type}/examDate/{exam-date}")
  @Produces("application/pdf")
  public StreamingOutput createStaffReport(@PathParam("semester-id") Integer pSemesterId,
      @PathParam("exam-type") Integer pExamType, @PathParam("exam-date") String pExamDate) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          mEmpExamAttendanceGenerator.createStaffAttendantList(pSemesterId, pExamType, pExamDate, output);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }

}
