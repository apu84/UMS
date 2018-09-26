package org.ums.academic.resource.optCourse;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
@Component
@Path("academic/optOfferedGroupSubGroupMap")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class OptOfferedGroupSubGroupMapResource extends MutableOptOfferedGroupSubGroupMapResource {
  @GET
  @Path("/getGroupInfo/semesterId/{Semester-id}/program/{program-id}/year/{year}/semester/{semester}")
  public JsonObject getGroupInfo(@Context Request pRequest, @PathParam("Semester-id") Integer pSemesterId,
      @PathParam("program-id") Integer pProgramId, @PathParam("year") Integer pYear,
      @PathParam("semester") Integer pSemester) {
    return mapResourceHelper.getGroupInfo(pSemesterId, pProgramId, pYear, pSemester, mUriInfo);
  }

}
