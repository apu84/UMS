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
  @Path("/getCourseList/studentId/{student-id}/regType/{reg-type}")
  public JsonObject getCourseList(@Context Request pRequest, @PathParam("student-id") String pStudentId,
      @PathParam("reg-type") Integer pRegType) {
    return mHelper.getCourseList(pStudentId, pRegType, pRequest, mUriInfo);
  }

  @GET
  @Path("/getExpelInfoList/semesterId/{Semester-id}/regType/{reg-type}")
  public JsonObject getExpelInfoList(@Context Request pRequest, @PathParam("Semester-id") Integer pSemesterId,
      @PathParam("reg-type") Integer pRegType) {
    return mHelper.getExpelInfoList(pSemesterId, pRegType, pRequest, mUriInfo);
  }
}
