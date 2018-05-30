package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 5/27/2018.
 */
@Component
@Path("/academic/expelledInformation")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ExpelledInformationResource extends MutableExpelledInformationResource {
  @GET
  @Path("/getCourseList/studentId/{student-id}/examType/{exam-type}")
  public JsonObject getApplicationCCIForStudent(@Context Request pRequest, @PathParam("student-id") String pStudentId,
      @PathParam("exam-type") Integer pExamType) {
    return mHelper.getCourseList(pStudentId, pExamType, pRequest, mUriInfo);
  }
}
