package org.ums.academic.resource.exam.attendant;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.exam.attendant.StudentsExamAttendantInfoHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 6/9/2018.
 */
public class MutableStudentsExamAttendantInfoResource extends Resource {
  @Autowired
  StudentsExamAttendantInfoHelper mHelper;

  @POST
  @Path("/addStudentAttendantRecords")
  @Produces({MediaType.APPLICATION_JSON})
  public Response addStudentAttendantRecords(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }
}
