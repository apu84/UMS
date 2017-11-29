package org.ums.employee.cv;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.employee.report.EmployeeCVGenerator;
import org.ums.enums.ProgramType;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Path("employee/report")
public class EmployeeCVResource extends Resource {

  @Autowired
  EmployeeCVGenerator mEmployeeCVGenerator;

  @GET
  @Path("/cv/employeeId/{employee-id}")
  @Produces("application/pdf")
  public StreamingOutput getEmployeeCV(final @PathParam("employee-id") String pEmployeeId,
      final @Context Request pRequest) {
    return new StreamingOutput() {
      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {
        try {
          mEmployeeCVGenerator.createEmployeeCV(pEmployeeId, pOutputStream);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
