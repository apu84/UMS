package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.StudentRecordManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by My Pc on 08-Aug-16.
 */
@Component
@Path("/academic/studentrecord")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class StudentRecordResource extends MutableStudentRecordResource {

  @Autowired
  StudentRecordManager mManager;

  @GET
  @Path("/student/{student-id}/semesterId/{semester-id}/year/{year}/semester/{semester}")
  public JsonObject getStudentRecord(final @Context Request pRequest, final @PathParam("student-id") String pStudentId,
      final @PathParam("semester-id") Integer pSemesterId, final @PathParam("year") Integer pYear,
      final @PathParam("semester") Integer pSemester) {
    return mHelper.getStudentRecord(pStudentId, pSemesterId, pYear, pSemester, pRequest, mUriInfo);
  }

}
