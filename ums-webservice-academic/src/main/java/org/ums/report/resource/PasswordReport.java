package org.ums.report.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.report.generator.PasswordReportGenerator;
import org.ums.report.generator.AbstractReportGenerator;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Path("/credentialReport")
@Produces({"application/pdf"})
public class PasswordReport extends Resource {
  @Autowired
  PasswordReportGenerator mPasswordReportGenerator;

  @GET
  @Path("/single" + PATH_PARAM_OBJECT_ID)
  public StreamingOutput get(final @Context Request pRequest, final @PathParam("object-id") String pObjectId) {
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          String query =
              "Select User_Id,Temp_Password,Full_Name,'Student' Role,Short_Name Dept_Name,'1st year, 1st semester' Semester from USERS,Students,Mst_Program,Mst_Dept_Office Where  "
                  + "User_Id='"
                  + pObjectId
                  + "' "
                  + "And USERS.USER_ID=STUDENTS.STUDENT_ID "
                  + "And MST_PROGRAM.PROGRAM_ID=STUDENTS.PROGRAM_ID "
                  + "And MST_PROGRAM.DEPT_ID=MST_DEPT_OFFICE.DEPT_ID ";
          // mPasswordReportGenerator.generateReport(AbstractReportGenerator.OutputType.PDF, output,
          // query);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }

  @GET
  @Path("/bulk" + PATH_PARAM_OBJECT_ID)
  public StreamingOutput getBulk(final @Context Request pRequest, final @PathParam("object-id") String pObjectId) {
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          String query =
              "Select User_Id,Temp_Password,Full_Name,'Student' Role,Short_Name Dept_Name,'1st year, 1st semester' Semester from USERS,Students,Mst_Program,Mst_Dept_Office Where  "
                  + "Mst_Program.Program_Id='"
                  + pObjectId
                  + "' "
                  + "And USERS.USER_ID=STUDENTS.STUDENT_ID "
                  + "And MST_PROGRAM.PROGRAM_ID=STUDENTS.PROGRAM_ID "
                  + "And MST_PROGRAM.DEPT_ID=MST_DEPT_OFFICE.DEPT_ID ";
          // mPasswordReportGenerator.generateReport(AbstractReportGenerator.OutputType.PDF, output,
          // query);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
