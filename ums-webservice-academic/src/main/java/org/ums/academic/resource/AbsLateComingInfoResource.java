package org.ums.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
@Component
@Path("academic/absLateComing")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AbsLateComingInfoResource extends MutableAbsLateComingInfoResource {
  @GET
  @Path("/getAbsLateComeInfoList/semesterId/{Semester-id}/examType/{exam-type}")
  public JsonObject getExpelInfoList(@Context Request pRequest, @PathParam("Semester-id") Integer pSemesterId,
      @PathParam("exam-type") Integer pExamType) {
    return mHelper.getAbsLateComeInfoList(pSemesterId, pExamType, pRequest, mUriInfo);
  }

}
