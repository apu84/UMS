package org.ums.common.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.common.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("/academic/optionalcourse")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)

public class OptionalCourseOfferResource  extends  MutableOptionalCourseOfferResource{

  @GET
  @Path("/semester-id/{semester-id}/program/{program-id}/year/{year}/semester/{semester}")
  public JsonObject getOptionalCourses(final @Context Request pRequest,
                                   final @PathParam("semester-id") Integer pSemesterId,
                                   final @PathParam("program-id") Integer pProgramId,
                                   final @PathParam("year") Integer pYear,
                                   final @PathParam("semester") Integer pSemester) throws Exception {
    return mResourceHelper.getOptionalCourses(pSemesterId, pProgramId, pYear,pSemester,pRequest, mUriInfo);
  }


}
